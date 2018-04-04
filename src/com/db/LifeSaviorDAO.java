package com.db;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.lsa.util.LSSPropertiesLoader;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.pojo.UserProfileDetails;

public class LifeSaviorDAO {
	final static Logger logger = Logger.getLogger(LifeSaviorDAO.class.getName());
	private static LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();
	public MongoClient openConnection() {
		// Creating a Mongo client
		return new MongoClient(lssPropertiesLoader.getValue("mongo.db.hostname"), Integer.parseInt(lssPropertiesLoader.getValue("mongo.db.port")));
	}

	public MongoDatabase connectToDatabase(MongoClient mongo) {
		// Creating Credentials
		MongoCredential.createCredential("sampleUser", "lifesavior",
				"password".toCharArray());
		// Accessing the database
		return mongo.getDatabase(lssPropertiesLoader.getValue("mongo.db.name"));
	}

	public UserProfileDetails selectData(String mobileNumber)
			throws IllegalArgumentException, IllegalAccessException {
		MongoClient mongo = openConnection();
		MongoDatabase database = connectToDatabase(mongo);
		MongoCollection<Document> collection = database
				.getCollection(lssPropertiesLoader.getValue("mongo.db.collec.name"));
		FindIterable<Document> iterDoc = collection.find(Filters.eq(
				"mobileNum", mobileNumber));
		// FindIterable<Document> iterDoc = collection.find();
		Iterator<Document> it = iterDoc.iterator();
		UserProfileDetails userProfileDetails = new UserProfileDetails();
		boolean hasUser = false;
		while (it.hasNext()) {
			Document itr = it.next();
			Field[] fields = userProfileDetails.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (itr.containsKey(field.getName())) {
					hasUser = true;
					field.setAccessible(true);
					field.set(userProfileDetails, itr.get(field.getName()));
				}
			}
		}
		if (hasUser) {
			Field[] fields = userProfileDetails.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				System.out.println("field:" + field.getName() + ", value:"
						+ field.get(userProfileDetails));
			}
		} else {
			userProfileDetails = null;
		}
		mongo.close();
		return userProfileDetails;
	}

	public void insertData(UserProfileDetails userProfileDetails)
			throws IllegalArgumentException, IllegalAccessException {
		MongoClient mongo = openConnection();
		MongoDatabase database = connectToDatabase(mongo);
		MongoCollection<Document> collection = database
				.getCollection(lssPropertiesLoader.getValue("mongo.db.collec.name"));
		Field[] fields = userProfileDetails.getClass().getDeclaredFields();
		Map<String, Object> insertMap = new HashMap<String, Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			insertMap.put(field.getName(),
					(String) field.get(userProfileDetails));
		}
		Document document = new Document(insertMap);
		collection.insertOne(document);
		mongo.close();
	}
}
