/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.dto;

import com.sandhata.gcat.application.config.model.PartnerOperation;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author admin
 */
public class TableViewDto {

    int sno;
    TextField name;
    TextField abbr;
    ComboBox<String> interfac;
    ComboBox<String> method;
    TextField soapActionUri;
    TextField postFix;
    TextField req;
    TextField gap;
    TextField facade;
    TextField target;
    CheckBox transform;
    CheckBox mask;
    Button add;
    Button edit;
    Button remove;
    Button save;

    public TableViewDto() {
    }

    public TableViewDto(int sno, TextField name, TextField abbr, ComboBox<String> interfac, ComboBox<String> method, TextField soapActionUri, TextField postFix, TextField req, TextField gap, TextField facade, TextField target, CheckBox transform, CheckBox mask, Button button) {
        this.sno = sno;
        this.name = name;
        this.abbr = abbr;
        this.interfac = interfac;
        this.method = method;
        this.soapActionUri = soapActionUri;
        this.postFix = postFix;
        this.req = req;
        this.gap = gap;
        this.facade = facade;
        this.target = target;
        this.transform = transform;
        this.mask = mask;
        this.add = button;
        this.interfac.setMaxWidth(100);
        this.interfac.setMinWidth(100);
        this.method.setMaxWidth(100);
        this.method.setMinWidth(100);
    }
    
    public TableViewDto(PartnerOperation pOp) {
        this.name=new TextField(pOp.getOperationName());
        this.abbr = new TextField(pOp.getAbbr());
        this.interfac = new ComboBox<String>();
        interfac.setValue(pOp.getInterfac());
        this.method = new ComboBox<String>();
        method.setValue(pOp.getMethod());
        this.soapActionUri = new TextField(pOp.getSoapActionUri());
        this.postFix = new TextField(pOp.getPostFix());
        this.req = new TextField(pOp.getReq());
        this.gap = new TextField(pOp.getGap());
        this.facade = new TextField(pOp.getFacade());
        this.target = new TextField(pOp.getTarget());
        this.transform = new CheckBox();
        transform.setSelected(pOp.getTransform());
        this.mask = new CheckBox();
        mask.setSelected(pOp.getTransform());
        this.interfac.setMaxWidth(100);
        this.interfac.setMinWidth(100);
        this.method.setMaxWidth(100);
        this.method.setMinWidth(100);
    }

    public void setPartnerOperation(PartnerOperation partnerOperation) {
//        this.sno = sno;
        this.name.setText(partnerOperation.getOperationName());
        this.abbr.setText(partnerOperation.getAbbr());
        this.interfac.setValue(partnerOperation.getInterfac());
        this.method.setValue(partnerOperation.getMethod());
        this.soapActionUri.setText(partnerOperation.getSoapActionUri());
        this.postFix.setText(partnerOperation.getPostFix());
        this.req.setText(partnerOperation.getReq());
        this.gap.setText(partnerOperation.getGap());
        this.facade.setText("" + (Integer.parseInt(partnerOperation.getFacade()) / 1000));
        this.target.setText("" + (Integer.parseInt(partnerOperation.getTarget()) / 1000));
        this.transform.setSelected(partnerOperation.getTransform());
        this.mask.setSelected(partnerOperation.getMask());
//        this.add = button;
        this.interfac.setMaxWidth(100);
        this.interfac.setMinWidth(100);
        this.method.setMaxWidth(100);
        this.method.setMinWidth(100);
    }

    public int getSno() {
        return sno;
    }

    public TextField getName() {
        return name;
    }

    public TextField getAbbr() {
        return abbr;
    }

    public ComboBox<String> getInterfac() {
        return interfac;
    }

    public ComboBox<String> getMethod() {
        return method;
    }

    public TextField getSoapActionUri() {
        return soapActionUri;
    }

    public TextField getPostFix() {
        return postFix;
    }

    public TextField getReq() {
        return req;
    }

    public TextField getGap() {
        return gap;
    }

    public TextField getFacade() {
        return facade;
    }

    public TextField getTarget() {
        return target;
    }

    public CheckBox getTransform() {
        return transform;
    }

    public CheckBox getMask() {
        return mask;
    }

    public Button getAdd() {
        return add;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public void setAbbr(TextField abbr) {
        this.abbr = abbr;
    }

    public void setInterfac(ComboBox<String> interfac) {
        this.interfac = interfac;
    }

    public void setMethod(ComboBox<String> method) {
        this.method = method;
    }

    public void setSoapActionUri(TextField soapActionUri) {
        this.soapActionUri = soapActionUri;
    }

    public void setPostFix(TextField postFix) {
        this.postFix = postFix;
    }

    public void setReq(TextField req) {
        this.req = req;
    }

    public void setGap(TextField gap) {
        this.gap = gap;
    }

    public void setFacade(TextField facade) {
        this.facade = facade;
    }

    public void setTarget(TextField target) {
        this.target = target;
    }

    public void setTransform(CheckBox transform) {
        this.transform = transform;
    }

    public void setMask(CheckBox mask) {
        this.mask = mask;
    }

    public void setAdd(Button add) {
        this.add = add;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getRemove() {
        return remove;
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "TableViewDto{" + "sno=" + sno + ", name=" + name + ", abbr=" + abbr + ", interfac=" + interfac + ", method=" + method + ", soapActionUri=" + soapActionUri + ", postFix=" + postFix + ", req=" + req + ", gap=" + gap + ", facade=" + facade + ", target=" + target + ", transform=" + transform + ", mask=" + mask + ", add=" + add + ", edit=" + edit + ", remove=" + remove + ", save=" + save + '}';
    }
}
