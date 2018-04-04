package com.org.lifesaver.dto;

/**
 * Created by Gopi Yarasani on 01-04-2018.
 */
public class IncidentInfo {

    private String location;
    private String date;
    private String time;
    private String description;
    private String cause;
    private String additionalInfo;
    private String oshaReporting;
    private String witnessContactInfo;
    private String reportedDate;
    private String reportedTime;
    private InjuryInfo injuryInfo;
    private EmployeeInfo reportedEmpInfo;
    private VerifiationInfo verifiationInfo;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getOshaReporting() {
        return oshaReporting;
    }

    public void setOshaReporting(String oshaReporting) {
        this.oshaReporting = oshaReporting;
    }

    public String getWitnessContactInfo() {
        return witnessContactInfo;
    }

    public void setWitnessContactInfo(String witnessContactInfo) {
        this.witnessContactInfo = witnessContactInfo;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(String reportedTime) {
        this.reportedTime = reportedTime;
    }

    public InjuryInfo getInjuryInfo() {
        return injuryInfo;
    }

    public void setInjuryInfo(InjuryInfo injuryInfo) {
        this.injuryInfo = injuryInfo;
    }

    public EmployeeInfo getReportedEmpInfo() {
        return reportedEmpInfo;
    }

    public void setReportedEmpInfo(EmployeeInfo reportedEmpInfo) {
        this.reportedEmpInfo = reportedEmpInfo;
    }

    public VerifiationInfo getVerifiationInfo() {
        return verifiationInfo;
    }

    public void setVerifiationInfo(VerifiationInfo verifiationInfo) {
        this.verifiationInfo = verifiationInfo;
    }
}
