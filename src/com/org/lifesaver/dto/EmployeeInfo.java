package com.org.lifesaver.dto;

/**
 * Created by Gopi Yarasani on 01-04-2018.
 */
public class EmployeeInfo {
    private String name;
    private String role;
    private String currentRoleDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCurrentRoleDuration() {
        return currentRoleDuration;
    }

    public void setCurrentRoleDuration(String currentRoleDuration) {
        this.currentRoleDuration = currentRoleDuration;
    }
}
