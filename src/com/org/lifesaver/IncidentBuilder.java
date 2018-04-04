package com.org.lifesaver;

import java.util.Map;

import com.itextpdf.forms.fields.PdfFormField;
import com.org.lifesaver.dto.EmployeeInfo;
import com.org.lifesaver.dto.IncidentInfo;
import com.org.lifesaver.dto.InjuryInfo;
import com.org.lifesaver.dto.VerifiationInfo;
import com.org.lifesaver.pdf.acro.constants.IncidentFormAcroFields;

/**
 * Created by Gopi Yarasani on 01-04-2018.
 */
public class IncidentBuilder {

    public static IncidentInfo buildIncidentInfo() {
        IncidentInfo incidentInfo = new IncidentInfo();
        incidentInfo.setLocation("Rayadurgam");
        incidentInfo.setDate("1-Apr-2018");
        incidentInfo.setTime("12:00pm");
        incidentInfo.setDescription("6 people injured and 4 motor cycles and 2 cars damaged");
        incidentInfo.setCause("Lorry break failure");
        incidentInfo.setAdditionalInfo("None");
        incidentInfo.setOshaReporting("No");
        incidentInfo.setWitnessContactInfo("Maro Aparichithudu, FootPath, Near Rayadurgam PS. Phone:1234567890");
        incidentInfo.setReportedDate("1-Apr-2018");
        incidentInfo.setReportedTime("12:15pm");


        InjuryInfo injuryInfo = new InjuryInfo();
        injuryInfo.setName("Aparichithudu");
        injuryInfo.setHead(true);
        injuryInfo.setHandLeft(true);
        injuryInfo.setEarsLeft(true);


        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setName("108");
        employeeInfo.setRole("Junior Doctor");
        employeeInfo.setCurrentRoleDuration("1year");


        VerifiationInfo verifiationInfo = new VerifiationInfo();
        verifiationInfo.setSupervisorName("Venkatachalam");
        verifiationInfo.setReportedTo("UNKNOWN");
        verifiationInfo.setReportedDate("02-Apr-2018");
        verifiationInfo.setBureau("Health Ministry");
        verifiationInfo.setWorkUnit("108");
        verifiationInfo.setAdditionalInfo("None");

        incidentInfo.setInjuryInfo(injuryInfo);
        incidentInfo.setReportedEmpInfo(employeeInfo);
        incidentInfo.setVerifiationInfo(verifiationInfo);


        return incidentInfo;
    }

    public static IncidentInfo buildFromPdf(Map<String, PdfFormField> formFields) {

        IncidentInfo incidentInfo = new IncidentInfo();

        incidentInfo.setLocation(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_LOCATION));
        incidentInfo.setDate(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_DATE));
        incidentInfo.setTime(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_TIME));
        incidentInfo.setDescription(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_DESCRIPTION));
        incidentInfo.setCause(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_CAUSE));
        incidentInfo.setAdditionalInfo(formFieldValue(formFields, IncidentFormAcroFields.ADDITIONAL_INFO));
        incidentInfo.setOshaReporting(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_OSHA_REPORTING));
        incidentInfo.setWitnessContactInfo(formFieldValue(formFields, IncidentFormAcroFields.INCIDENT_WITNESS_CONTACT_INFO));
        incidentInfo.setReportedDate(formFieldValue(formFields, IncidentFormAcroFields.REPORTED_DATE));

        InjuryInfo injuryInfo = new InjuryInfo();
        injuryInfo.setHead(optionFormFieldValue(formFields, IncidentFormAcroFields.HEAD));
        injuryInfo.setHandLeft(optionFormFieldValue(formFields, IncidentFormAcroFields.HAND_LEFT));
        injuryInfo.setEarsLeft(optionFormFieldValue(formFields, IncidentFormAcroFields.EARS_RIGHT));


        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setName(formFieldValue(formFields, IncidentFormAcroFields.EMPLOYEE_NAME));
        employeeInfo.setRole(formFieldValue(formFields, IncidentFormAcroFields.EMPLOYEE_ROLE));
        employeeInfo.setCurrentRoleDuration(formFieldValue(formFields, IncidentFormAcroFields.EMPLOYEE_DURATION));


        VerifiationInfo verifiationInfo = new VerifiationInfo();
        verifiationInfo.setSupervisorName(formFieldValue(formFields, IncidentFormAcroFields.SUPERVISOR_NAME));
        verifiationInfo.setReportedTo(formFieldValue(formFields, IncidentFormAcroFields.REPORTED_TO));
        verifiationInfo.setReportedDate(formFieldValue(formFields, IncidentFormAcroFields.VERIFICATION_REPORTED_DATE));
        verifiationInfo.setBureau(formFieldValue(formFields, IncidentFormAcroFields.BUREAU));
        verifiationInfo.setWorkUnit(formFieldValue(formFields, IncidentFormAcroFields.WORK_UNIT));
        verifiationInfo.setAdditionalInfo(formFieldValue(formFields, IncidentFormAcroFields.ADDITIONAL_INFO));

        incidentInfo.setInjuryInfo(injuryInfo);
        incidentInfo.setReportedEmpInfo(employeeInfo);
        incidentInfo.setVerifiationInfo(verifiationInfo);


        return incidentInfo;

    }

    public static void populateAcroFields(Map<String, PdfFormField> formFields, IncidentInfo incidentInfo) {
        PdfFormField field;
        field = formFields.get(IncidentFormAcroFields.EMPLOYEE_NAME);
        field.setValue(incidentInfo.getReportedEmpInfo().getName());
        field = formFields.get(IncidentFormAcroFields.EMPLOYEE_ROLE);
        field.setValue(incidentInfo.getReportedEmpInfo().getRole());
        field = formFields.get(IncidentFormAcroFields.EMPLOYEE_DURATION);
        field.setValue(incidentInfo.getReportedEmpInfo().getCurrentRoleDuration());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_LOCATION);
        field.setValue(incidentInfo.getLocation());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_DATE);
        field.setValue(incidentInfo.getDate());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_TIME);
        field.setValue(incidentInfo.getTime());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_DESCRIPTION);
        field.setValue(incidentInfo.getDescription());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_CAUSE);
        field.setValue(incidentInfo.getCause());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_ADD_INFO);
        field.setValue(incidentInfo.getAdditionalInfo());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_OSHA_REPORTING);
        field.setValue(incidentInfo.getOshaReporting());
        field = formFields.get(IncidentFormAcroFields.INCIDENT_WITNESS_CONTACT_INFO);
        field.setValue(incidentInfo.getWitnessContactInfo());
        field = formFields.get(IncidentFormAcroFields.REPORTED_DATE);
        field.setValue(incidentInfo.getReportedDate());

        //Verification
        field = formFields.get(IncidentFormAcroFields.SUPERVISOR_NAME);
        field.setValue(incidentInfo.getVerifiationInfo().getSupervisorName());
        field = formFields.get(IncidentFormAcroFields.VERIFICATION_REPORTED_DATE);
        field.setValue(incidentInfo.getVerifiationInfo().getReportedDate());
        field = formFields.get(IncidentFormAcroFields.REPORTED_TO);
        field.setValue(incidentInfo.getVerifiationInfo().getReportedTo());
        field = formFields.get(IncidentFormAcroFields.BUREAU);
        field.setValue(incidentInfo.getVerifiationInfo().getBureau());
        field = formFields.get(IncidentFormAcroFields.WORK_UNIT);
        field.setValue(incidentInfo.getVerifiationInfo().getWorkUnit());
        field = formFields.get(IncidentFormAcroFields.ADDITIONAL_INFO);
        field.setValue(incidentInfo.getVerifiationInfo().getAdditionalInfo());

        //Injury
        field = formFields.get(IncidentFormAcroFields.HEAD);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isHead()));
        field = formFields.get(IncidentFormAcroFields.FACE);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isFace()));
        field = formFields.get(IncidentFormAcroFields.NECK);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isNeck()));
        field = formFields.get(IncidentFormAcroFields.UPPERBACK);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isUpperBack()));
        field = formFields.get(IncidentFormAcroFields.LOWERBACK);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLowerBack()));
        field = formFields.get(IncidentFormAcroFields.CHEST);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isChest()));
        field = formFields.get(IncidentFormAcroFields.ABDOMEN);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isAbdomen()));
        field = formFields.get(IncidentFormAcroFields.PELVIS_GROIN);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isPelvisGroin()));
        field = formFields.get(IncidentFormAcroFields.LIPS);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLips()));
        field = formFields.get(IncidentFormAcroFields.TEETH);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isTeeth()));
        field = formFields.get(IncidentFormAcroFields.TONGUE);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isTongue()));
        field = formFields.get(IncidentFormAcroFields.NOSE);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isNose()));
        field = formFields.get(IncidentFormAcroFields.FINGERS);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isFingers()));
        field = formFields.get(IncidentFormAcroFields.TOES);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isToes()));
        field = formFields.get(IncidentFormAcroFields.OTHER1);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isOther1()));
        field = formFields.get(IncidentFormAcroFields.OTHER2);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isOther2()));

        field = formFields.get(IncidentFormAcroFields.SHOULDER_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isShoulderLeft()));
        field = formFields.get(IncidentFormAcroFields.SHOULDER_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isShoulderRight()));
        field = formFields.get(IncidentFormAcroFields.ARMPIT_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isArmPitLeft()));
        field = formFields.get(IncidentFormAcroFields.ARMPIT_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isArmPitRight()));
        field = formFields.get(IncidentFormAcroFields.UPPERARM_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isUpperArmLeft()));
        field = formFields.get(IncidentFormAcroFields.UPPERARM_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isUpperArmRight()));
        field = formFields.get(IncidentFormAcroFields.LOWERARM_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLowerArmLeft()));
        field = formFields.get(IncidentFormAcroFields.LOWERARM_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLowerLegRight()));
        field = formFields.get(IncidentFormAcroFields.ELBOW_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isElbowLeft()));
        field = formFields.get(IncidentFormAcroFields.ELBOW_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isElbowRight()));
        field = formFields.get(IncidentFormAcroFields.WRIST_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isWristLeft()));
        field = formFields.get(IncidentFormAcroFields.WRIST_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isWristRight()));
        field = formFields.get(IncidentFormAcroFields.HAND_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isHandLeft()));
        field = formFields.get(IncidentFormAcroFields.HAND_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isHandRight()));
        field = formFields.get(IncidentFormAcroFields.BUTTOCKS_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isButtocksLeft()));
        field = formFields.get(IncidentFormAcroFields.BUTTOCKS_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isButtocksRight()));
        field = formFields.get(IncidentFormAcroFields.HIP_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isHipLeft()));
        field = formFields.get(IncidentFormAcroFields.HIP_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isHipRight()));
        field = formFields.get(IncidentFormAcroFields.THIGH_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isThighLeft()));
        field = formFields.get(IncidentFormAcroFields.THIGH_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isThighRight()));
        field = formFields.get(IncidentFormAcroFields.LOWERLEG_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLowerLegLeft()));
        field = formFields.get(IncidentFormAcroFields.LOWERLEG_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isLowerLegRight()));
        field = formFields.get(IncidentFormAcroFields.KNEE_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isKneeLeft()));
        field = formFields.get(IncidentFormAcroFields.KNEE_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isKneeRight()));
        field = formFields.get(IncidentFormAcroFields.ANKLE_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isAnkleLeft()));
        field = formFields.get(IncidentFormAcroFields.ANKLE_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isAnkleRight()));
        field = formFields.get(IncidentFormAcroFields.EYES_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isEyesLeft()));
        field = formFields.get(IncidentFormAcroFields.EYES_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isEyesRight()));
        field = formFields.get(IncidentFormAcroFields.EARS_LEFT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isEarsLeft()));
        field = formFields.get(IncidentFormAcroFields.EARS_RIGHT);
        field.setValue(optionFieldValue(incidentInfo.getInjuryInfo().isEarsRight()));

    }

    private static String optionFieldValue(boolean flag) {
        return flag?"On":"Off";
    }

    private static String formFieldValue(Map<String, PdfFormField> formFields, String acroFieldName) {
        return formFields.get(acroFieldName).getValue().toString();
    }

    private static boolean optionFormFieldValue(Map<String, PdfFormField> formFields, String acroFieldName) {
        return "On".equalsIgnoreCase(formFields.get(acroFieldName).getValue().toString())?true:false;
    }
}
