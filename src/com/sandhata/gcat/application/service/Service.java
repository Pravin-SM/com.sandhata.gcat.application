/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service;

import com.sandhata.gcat.application.config.model.PartnerDetails;
import com.sandhata.gcat.application.config.model.PartnerInstanceInfo;
import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.config.model.Project;
import com.sandhata.gcat.application.dto.TableViewDto;
import java.io.File;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author admin
 */
public interface Service {
    
    public List<String> getAllInstance();
    public List<String> getAllInterfaces();
    public List<String> getAllMethods();
    public List<String> getAllPartnerDetails();
    public Set<String> getAllPartnerGroup(String instanceName);
    public void rearrange(ObservableList<TableViewDto> tvList);
    public void swapAdd(ObservableList<TableViewDto> tvList);
    public List<PartnerOperation> getNewPartner(ObservableList<TableViewDto> tvList, String lable);
    public TableViewDto createNewRow(ObservableList<String> interfaceOList, ObservableList<String> methodOList, String mode, ObservableList<TableViewDto> tvList);
    public void createNewEditRow(Set<PartnerOperation> partnerDetailsSet, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList, List<PartnerOperation> partnerOperationList);
    public ObservableList<TableViewDto> resetAll(ObservableList<String> interfaceOList, ObservableList<String> methodOList, ObservableList<TableViewDto> tvList);
    public String validateProject(Project project);
    public boolean isConfigedChanged();
    public void setConfigedChanged(boolean configedChanged);
    public List<PartnerOperation> getSavedPartnerOperationSet();
    public void setSavedPartnerOperationSet(List<PartnerOperation> savedPartnerOperationSet);
    public void removeAllExceptAdd(ObservableList<TableViewDto> tvList);
    
    
    //FileOperationService
    public void fileOperation(Project project, PartnerInstanceInfo partnerInstanceInfo, PartnerDetails partnerDetails, List<PartnerOperation> partnerOperationList, String fileNames);
    public List<String> getFiles(String operation);
    public PartnerDetails getPartnerDetails(String instance, String partner);
    public Set<PartnerOperation> exPartNewOp(String instance, String partner, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList);
    public void mergeCfgData(String instance, List<File> fileNames);
    
    //GIT Service
    public void checkOut();
    public void checkin();
    
}
