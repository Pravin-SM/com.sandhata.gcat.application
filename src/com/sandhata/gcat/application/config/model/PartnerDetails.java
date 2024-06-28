/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.config.model;

/**
 *
 * @author admin
 */
public class PartnerDetails {
    
    private String group;
    private String name;
    private String phone;
    private String emailID;
    private String caIssuer;
    private String serialNumber;

    public PartnerDetails() {
    }

    public PartnerDetails(String group, String name, String phone, String emailID, String caIssuer, String serialNumber) {
        this.group = group;
        this.name = name;
        this.phone = phone;
        this.emailID = emailID;
        this.caIssuer = caIssuer;
        this.serialNumber = serialNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getCaIssuer() {
        return caIssuer;
    }

    public void setCaIssuer(String caIssuer) {
        this.caIssuer = caIssuer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "PartnerDetails{" + "group=" + group + ", name=" + name + ", phone=" + phone + ", emailID=" + emailID + ", caIssuer=" + caIssuer + ", serialNumber=" + serialNumber + '}';
    }
    
}
