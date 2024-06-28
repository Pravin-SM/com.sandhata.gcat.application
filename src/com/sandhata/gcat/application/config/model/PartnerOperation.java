/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.config.model;

import com.sandhata.gcat.application.dto.TableViewDto;

/**
 *
 * @author admin
 */
public class PartnerOperation {

    private String operationName;
    private String abbr;
    private String interfac;
    private String method;
    private String soapActionUri;
    private String postFix;
    private String req;
    private String gap;
    private String facade;
    private String target;
    private boolean transform;
    private boolean mask;

    public PartnerOperation() {
    }

    public PartnerOperation(String operationName, String abbr, String interfac, String method, String soapActionUri, String postFix, String req, String gap, String facade, String target, boolean transform, boolean mask) {
        this.operationName = operationName;
        this.abbr = abbr;
        this.interfac = interfac;
        this.method = method;
        this.soapActionUri = soapActionUri;
        this.postFix = postFix;
        if ((req == null)||(req.equals(" "))||(req.equals(""))) {
            this.req = "" + 10;
        } else {
            this.req = req;
        }
        if ((gap == null)||(gap.equals(" "))||(gap.equals(""))) {
            this.gap = "" + 10;
        } else {
            this.gap = gap;
        }
        if ((facade == null)||(facade.equals(" "))||(facade.equals(""))) {
            this.facade = "" + 35000;
        } else {
            this.facade = "" + Integer.parseInt(facade) * 1000;
        }
        if ((target == null)||(target.equals(" "))||(target.equals(""))) {
            this.target = "" + 32000;
        } else {
            this.target = "" + Integer.parseInt(target) * 1000;
        }
        this.transform = transform;
        this.mask = mask;
    }

    public PartnerOperation(TableViewDto tvDto) {
        this.operationName = tvDto.getName().getText();
        this.abbr = tvDto.getAbbr().getText();
        this.interfac = tvDto.getInterfac().getValue();
        this.method = tvDto.getMethod().getValue();
        this.soapActionUri = tvDto.getSoapActionUri().getText();
        this.postFix = tvDto.getPostFix().getText();
        
        if ((tvDto.getReq().getText() == null)||(tvDto.getReq().getText().equals(" "))||(tvDto.getReq().getText().equals(""))) {
            this.req = "" + 10;
        } else {
            this.req = tvDto.getReq().getText();
        }
        if ((tvDto.getGap().getText() == null)||(tvDto.getGap().getText().equals(" "))||(tvDto.getGap().getText().equals(""))) {
            this.gap = "" + 10;
        } else {
            this.gap = tvDto.getGap().getText();
        }
        if ((tvDto.getFacade().getText() == null)||(tvDto.getFacade().getText().equals(" "))||(tvDto.getFacade().getText().equals(""))) {
            this.facade = "" + 35000;
        } else {
            this.facade = "" + Integer.parseInt(tvDto.getFacade().getText()) * 1000;
        }
        if ((tvDto.getTarget().getText() == null)||(tvDto.getTarget().getText().equals(" "))||(tvDto.getTarget().getText().equals(""))) {
            this.target = "" + 32000;
        } else {
            this.target = "" + Integer.parseInt(tvDto.getTarget().getText()) * 1000;
        }
        
        this.transform = tvDto.getTransform().selectedProperty().getValue();
        this.mask = tvDto.getMask().selectedProperty().getValue();
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getInterfac() {
        return interfac;
    }

    public void setInterfac(String interfac) {
        this.interfac = interfac;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSoapActionUri() {
        return soapActionUri;
    }

    public void setSoapActionUri(String soapActionUri) {
        this.soapActionUri = soapActionUri;
    }

    public String getPostFix() {
        return postFix;
    }

    public void setPostFix(String postFix) {
        this.postFix = postFix;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getFacade() {
        return facade;
    }

    public void setFacade(String facade) {
        this.facade = facade;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean getTransform() {
        return transform;
    }

    public void setTransform(boolean transform) {
        this.transform = transform;
    }

    public boolean getMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "PartnerOperation{" + "operationName=" + operationName + ", abbr=" + abbr + ", interfac=" + interfac + ", method=" + method + ", soapActionUri=" + soapActionUri + ", postFix=" + postFix + ", req=" + req + ", gap=" + gap + ", facade=" + facade + ", target=" + target + ", transform=" + transform + ", mask=" + mask + '}';
    }

}
