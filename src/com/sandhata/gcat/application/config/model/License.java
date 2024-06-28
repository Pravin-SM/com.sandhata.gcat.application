/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.config.model;

/**
 *
 * @author s_m_p
 */
public class License {
    
    private String product;
    
    private String date;
    
    private String keyIdentifier;
    
    private String macAddress;
    
    private String licenseKey;

    public void setProduct(String product) {
        this.product = product;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public String getProduct() {
        return product;
    }

    public String getDate() {
        return date;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getLicenseKey() {
        return licenseKey;
    }
    
}
