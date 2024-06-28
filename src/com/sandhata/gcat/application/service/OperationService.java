/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service;

import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.dto.TableViewDto;
import java.io.File;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author admin
 */
public interface OperationService {
    
//    public void initialize(boolean configedChanged,Set<PartnerOperation> savedPartnerOperationSet);
    public void readFromFile(String mode, File file, String inst, ListView<String> lDesiredList, FileOperationService fileOperationService, String instance, String partner, ObservableList<String> interfaceOList, ObservableList<String> methodOList, ObservableList<TableViewDto> tvList);
    public TableViewDto createNewRow(ObservableList<String> interfaceOList, ObservableList<String> methodOList, String mode, ObservableList<TableViewDto> tvList);
    public void swapAdd(ObservableList<TableViewDto> tvList);
    
}
