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
public class PartnerInstanceInfo {
    
    private String mode;
    private String scope;
    private String option;
    private String partner;

    public PartnerInstanceInfo() {
    }

    public PartnerInstanceInfo(String mode, String scope, String option, String partner) {
        this.mode = mode;
        this.scope = scope;
        this.option = option;
        this.partner = partner;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "PartnerInstanceInfo{" + "mode=" + mode + ", scope=" + scope + ", option=" + option + ", partner=" + partner + '}';
    }
    
}
