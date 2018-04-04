package com.geolocation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.lsa.util.LSSPropertiesLoader;

public class FetchLocationMap {
	final static Logger logger = Logger.getLogger(FetchLocationMap.class.getName());
	private static LSSPropertiesLoader lssPropertiesLoader = LSSPropertiesLoader.getInstance();
	public GeoLocationResponse geolocaton(LocationCoordinates locationCoordinates) {
		 
	    try {
	    String url = lssPropertiesLoader.getValue("geolocation.url");
	 
	    String message;
	    JSONObject json = new JSONObject();
	    json.put("radioType", locationCoordinates.getRadioType());
	    json.put("considerIp", false);

	    JSONArray jsonArray1 = new JSONArray();
	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("cellId", locationCoordinates.getCellId());
	    jsonObj.put("locationAreaCode", locationCoordinates.getLac());
	    jsonObj.put("mobileCountryCode", locationCoordinates.getMcc() );
	    jsonObj.put("mobileNetworkCode", locationCoordinates.getMnc());
	    jsonArray1.put(jsonObj);
	    json.put("cellTowers", jsonArray1);
	    
	    message = json.toString();
	    System.out.println(message);
	    
	    HttpParams httpParams = new BasicHttpParams();
	   // ConnRouteParams.setDefaultProxy(httpParams, new HttpHost(PROXY_HOST, PROXY_PORT));
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
	    HttpConnectionParams.setSoTimeout(httpParams, 10000);

	    HttpClient client = new DefaultHttpClient(httpParams);
	    HttpPost request = new HttpPost(url);
	    request.setHeader("Content-type", "application/json");

	    try {
	        StringEntity se = new StringEntity(json.toString());
	        request.setEntity(se);
	        HttpResponse response = client.execute(request);
	        if(response!=null){
	        	Gson gson = new Gson();
	            String jsonResult = EntityUtils.toString(response.getEntity());
	            System.out.println(jsonResult);
	            GeoLocationResponse geolocationResponse = gson.fromJson(jsonResult, GeoLocationResponse.class);
	            Double lat = geolocationResponse.getLocation().getLat();
				Double lng = geolocationResponse.getLocation().getLng();
				System.out.println("Est. Location: " + lat + ", "+ lng );
				fetchDirections(lat, lng);
	            fetchLocationMap(lat, lng);
	            return geolocationResponse;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    } catch (Exception e) {
	    e.printStackTrace();
	    }
		return null;
	 
	  }

	/**
	 * @param geolocationResponse
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void fetchLocationMap(double lat, double lng)
			throws MalformedURLException, IOException, FileNotFoundException {
		String urlString = "https://maps.googleapis.com/maps/api/staticmap?"
				+ "center="+lat + "," +lng
				+"&size=800x600&sensor=true&zoom=15&maptype=roadmap&"
				+ "markers=color:red%7Clabel:S%7C"
				+lat + "," +lng;
		URL url1 = new URL(urlString);
		System.out.println("urlString :" + urlString);
		
		String destinationFile = "image-"+lat+"-"+lng+".jpg";
		
		InputStream is = url1.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
		    os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	/**
	 * @param geolocationResponse
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void fetchDirections(double lat , double lng)
			throws ApiException, InterruptedException, IOException {
		GeoApiContext geoApiContextForDir = new GeoApiContext.Builder().apiKey(lssPropertiesLoader.getValue("directions.apikey")).build();
		LatLng originLatLng = new LatLng(lat, lng);
		LatLng destinationLatLng = new LatLng(lat, lng);
		DirectionsResult result = DirectionsApi.newRequest(geoApiContextForDir)
		        .origin(originLatLng)
		        .destination(destinationLatLng)
		        .await();
		for(DirectionsRoute route : result.routes) {
         // System.out.println(route.bounds.southwest);
		}
	}

	/**
	 * @param geolocationResponse
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public GeocodingResult getAddress(GeoLocationResponse geolocationResponse)
			throws ApiException, InterruptedException, IOException {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(lssPropertiesLoader.getValue("geocoding.apikey")).build();
		LatLng latlng = new LatLng(geolocationResponse.getLocation().getLat(), geolocationResponse.getLocation().getLng());
		GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latlng).await();
		if (results != null && results.length > 0) {
		    for(GeocodingResult geocodingResult : results){
		    	System.out.println(geocodingResult.formattedAddress);
		    	System.out.println(geocodingResult.placeId);
		    	fetchVicinity(geocodingResult.placeId);
		    	return geocodingResult;
		    }
		}
		return null;
	}

	/**
	 * @param geocodingResult
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String fetchVicinity(String placeId)
			throws ApiException, InterruptedException, IOException {
		GeoApiContext contextForPlacesApi = new GeoApiContext.Builder().apiKey(lssPropertiesLoader.getValue("places.apikey")).build();
		PlaceDetails placesDetails = PlacesApi.placeDetails(contextForPlacesApi, placeId).await();
		System.out.println(placesDetails.name + "," + placesDetails.vicinity);
		return placesDetails.name + "," + placesDetails.vicinity;
	}
}
