/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service;

import com.sandhata.gcat.application.config.model.License;
import com.sandhata.gcat.application.config.model.PartnerDetails;
import com.sandhata.gcat.application.config.model.PartnerInstanceInfo;
import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.config.model.Project;
import com.sandhata.gcat.application.dto.TableViewDto;
import java.io.File;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public interface FileOperationService {
    
    public void newPartNewOp(Project project, PartnerInstanceInfo partnerInstanceInfo, PartnerDetails partnerDetails, List<PartnerOperation> partnerOperationList, String fileName);
    public List<String> getFiles(String operation);
    public PartnerDetails getPartnerDetails(String instance, String partner);
    public Set<PartnerOperation> exPartNewOp(String instance, String partner, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList);
    public File getInputFilePath(String instanceName, String fileName);
//    public void newPartExistingOperation(Project project, PartnerInstanceInfo partnerInstanceInfo, PartnerDetails partnerDetails, List<PartnerOperation> partnerOperationList, String fileName);
    public Set<PartnerOperation> getFacadeAccessData(File file1, File file2, File file3, String partner);
    public String fOperationGetPostActionURI(File tempFile, String opName);
    public String getPostFix(File tempFile, String opName);
    public String getJSONMethod(File tempFile, String opName);
    public String getTargetCount(File tempFile, String opName);
    public void mergeCfgData(String instance, List<File> fileNames);
}
