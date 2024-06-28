/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service.impl;

import com.sandhata.gcat.application.config.model.PartnerOperation;
import static com.sandhata.gcat.application.controller.OperationsController.sn;
import com.sandhata.gcat.application.dto.TableViewDto;
import com.sandhata.gcat.application.service.FileOperationService;
import com.sandhata.gcat.application.service.OperationService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author admin
 */
public class OperationServiceImpl implements OperationService {

    private TableViewDto tvODto;
    private TableViewDto tvUDto;

    @Override
    public void readFromFile(String mode, File file, String inst, ListView<String> lDesiredList, FileOperationService fileOperationService, String instance, String partner, ObservableList<String> interfaceOList, ObservableList<String> methodOList, ObservableList<TableViewDto> tvList) {
        try {

//          System.out.println("File Path :\t" + fileOperationService.getInputFilePath(inst, "facadeaccess.cfg"));
            String line = "";
            String operationName = "";
            String partnerName = "";
            Properties prop = new Properties();
            Properties fileOperationProp = new Properties();
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream inputStream2 = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream2);

            File file1 = new File(prop.getProperty("config_file_base_path") + "\\" + inst + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_1"));
            File file2 = new File(prop.getProperty("config_file_base_path") + "\\" + inst + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_2"));
            File file3 = new File(prop.getProperty("config_file_base_path") + "\\" + inst + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_3"));

//            System.out.println("Desilable List Size :\t" + lDesiredList.getItems().size());
            for (String tempOperation : lDesiredList.getItems()) {
                System.out.println("Temp Operation :\t" + tempOperation);
                if (tempOperation.contains(";")) {
                    String[] opName = tempOperation.split(Pattern.quote(";"));
                    operationName = opName[0];
                    partnerName = opName[1];
                } else {
                    operationName = tempOperation;
                    partnerName = "";
                }
                BufferedReader br = new BufferedReader(new FileReader(fileOperationService.getInputFilePath(instance, "facadeaccess.cfg")));
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("partner_" + partner)) {
                        line = line.substring(line.indexOf("_") + 1, line.length());
//                        System.out.println("Line :\t" + line);
                        String[] data = line.split(Pattern.quote("|"));
                        if (data[2].equals(tempOperation)) {
                            PartnerOperation partnerOperation = null;
                            if (data[2].contains("JSON")) {
//                        System.out.println("JSON Data Length :\t" + data.length);
                                if (data.length > 6) {
                                    partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "REST", fileOperationService.getJSONMethod(file3, data[2]), fileOperationService.fOperationGetPostActionURI(file3, data[2]), fileOperationService.getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), fileOperationService.getTargetCount(file2, data[2]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]));
                                } else {
                                    partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "REST", fileOperationService.getJSONMethod(file3, data[2]), fileOperationService.fOperationGetPostActionURI(file3, data[2]), fileOperationService.getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), fileOperationService.getTargetCount(file2, data[2]), false, Boolean.parseBoolean(data[5]));
                                }
                            } else {
                                //partner_HURM|po_HURM_GetServiceBusinessDetails.1|GetServiceBusinessDetails.1|R-HURM-GSBD1-10-10|35000|false||||||
                                //partner_{0}|po_{1}_{2}|{3}|R-{4}-{5}-{6}-{7}|{8}|false|{9}|{10}||||
                                //String operationName, String abbr, String interfac, String method, String soapActionUri, String postFix, String req, String gap, String facade, String target, boolean transform, boolean mask
//                                System.out.println("Data :\t" + (Integer.parseInt(data[4]) / 1000));
                                if (data.length > 6) {
                                    partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "SOAP", "", fileOperationService.fOperationGetPostActionURI(file3, data[2]), fileOperationService.getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), fileOperationService.getTargetCount(file2, data[2]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]));
                                } else {
//                                    System.out.println("Data[2]:\t" + data[2]);
                                    partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "SOAP", "", fileOperationService.fOperationGetPostActionURI(file3, data[2]), fileOperationService.getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), fileOperationService.getTargetCount(file2, data[2]), false, Boolean.parseBoolean(data[5]));
                                }

                            }
//                            System.out.println(partnerOperation.toString());
//                            partnerOperationSet.add(partnerOperation);
                            TableViewDto tempDto = createNewRow(interfaceOList, methodOList, mode, tvList);
                            tempDto.setPartnerOperation(partnerOperation);
                            tvList.add(tempDto);
                            break;
                        }
                    }
                }
                br.close();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public TableViewDto createNewRow(ObservableList<String> interfaceOList, ObservableList<String> methodOList, String mode, ObservableList<TableViewDto> tvList) {
        int sno = getSno(tvList);
        TextField name = new TextField();
        name.setPromptText("Name (with version#)");
        TextField abbr = new TextField();
        abbr.setPromptText("Abbr (with version#)");
        ComboBox<String> interfac = new ComboBox<String>();
        interfac.setPrefWidth(100);
        ComboBox<String> method = new ComboBox<String>();
        method.setPrefWidth(100);
        interfac.setItems(interfaceOList);

        //Listerner for interface combo box to check if the selection is SOAP/REST
        interfac.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (interfac.getValue() != null) {
                    if (interfac.getValue().equalsIgnoreCase("soap")) {
                        method.setItems(null);
                    } else {
                        method.setItems(methodOList);
                    }
                }
            }
        });

        TextField soapActionUri = new TextField();
        soapActionUri.setPromptText("SOAPAction (SOAP)/URI (JSON)");
        TextField postFix = new TextField();
        postFix.setPromptText("DestinationPostfix");
        TextField req = new TextField();
        req.setPromptText("10");
        TextField gap = new TextField();
        gap.setPromptText("10");
        TextField facade = new TextField();
        facade.setPromptText("35");
        TextField target = new TextField();
        target.setPromptText("32");
        CheckBox transform = new CheckBox();
        CheckBox mask = new CheckBox();
        Button button = new Button();
        button.setPrefWidth(70);
        if (mode.equalsIgnoreCase("add")) {
            button.setText("Add");

            //Adding Listener's for ADD button
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    for (TableViewDto temp : tvList) {
                        if (temp.getAdd().equals(e.getSource())) {
                            //Inserting new row with remove button
                            TableViewDto tvDto = copyTableViewContent(createNewRow(interfaceOList, methodOList, "remove", tvList), temp);
                            tvList.remove(temp);
                            tvList.add(tvDto);
                            tvList.add(createNewRow(interfaceOList, methodOList, "add", tvList));
                            break;
                        }
                    }
                    rearrange(tvList);
                    System.out.println("You have selected " + button.getText());
                }
            });
        } else {
            if (mode.equalsIgnoreCase("edit")) {
                button.setText("Edit");
                name.setEditable(false);
                abbr.setEditable(false);
                interfac.setDisable(true);
                method.setDisable(true);
                soapActionUri.setEditable(false);
                postFix.setEditable(false);
                req.setEditable(false);
                gap.setEditable(false);
                facade.setEditable(false);
                target.setEditable(false);
                transform.setDisable(true);
                mask.setDisable(true);

                //updating the color of existing row to green
                name.setStyle("-fx-background-color:#77DD77;");
                abbr.setStyle("-fx-background-color:#77DD77;");
                interfac.setStyle("-fx-background-color:#77DD77;");
                method.setStyle("-fx-background-color:#77DD77;");
                soapActionUri.setStyle("-fx-background-color:#77DD77;");
                postFix.setStyle("-fx-background-color:#77DD77;");
                req.setStyle("-fx-background-color:#77DD77;");
                gap.setStyle("-fx-background-color:#77DD77;");
                facade.setStyle("-fx-background-color:#77DD77;");
                target.setStyle("-fx-background-color:#77DD77;");
                transform.setStyle("-fx-background-color:#77DD77;");
                mask.setStyle("-fx-background-color:#77DD77;");
                button.setStyle("-fx-background-color:#77DD77;");

                //Adding Listener's for Edit button
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (button.getText().equalsIgnoreCase("edit")) {
                            //name.setEditable(true);
                            //abbr.setEditable(true);
                            //interfac.setDisable(false);
                            //method.setDisable(false);
                            //soapActionUri.setEditable(true);
                            //postFix.setEditable(true);
                            req.setEditable(true);
                            gap.setEditable(true);
                            facade.setEditable(true);
                            target.setEditable(true);

                            //Validating if it is Soap or JSON and based on that we are disabling transforming and Mask
                            //For JSON we are disabling in first drop
                            if (interfac.getValue().equalsIgnoreCase("soap")) {
                                transform.setDisable(false);
                                mask.setDisable(false);
                            } else {
                                transform.setDisable(true);
                                mask.setDisable(true);
                            }

                            button.setText("Save");

                            //Updating the values for tvODto
                            TableViewDto tempODto = new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button);
                            PartnerOperation tOPOperation = new PartnerOperation(tempODto);
                            System.out.println("Partner Operation Old Content :\t" + tOPOperation.toString());
                            tvODto = new TableViewDto(tOPOperation);

                        } else {
                            //name.setEditable(false);
                            //abbr.setEditable(false);
                            //interfac.setDisable(true);
                            //method.setDisable(true);
                            //soapActionUri.setEditable(false);
                            //postFix.setEditable(false);
                            req.setEditable(false);
                            gap.setEditable(false);
                            facade.setEditable(false);
                            target.setEditable(false);
                            transform.setDisable(true);
                            mask.setDisable(true);
                            button.setText("Edit");

                            //Updating the values for tvUDto
                            TableViewDto tempUDto = new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button);
                            PartnerOperation tUPOperation = new PartnerOperation(tempUDto);
                            System.out.println("Partner Operation Updated Content :\t" + tUPOperation.toString());
                            tvUDto = new TableViewDto(tUPOperation);

                            if (valueModified(tvODto, tvUDto)) {
                                //updating the color of Modified row to orange
                                name.setStyle("-fx-background-color:#FFE082;");
                                abbr.setStyle("-fx-background-color:#FFE082;");
                                interfac.setStyle("-fx-background-color:#FFE082;");
                                method.setStyle("-fx-background-color:#FFE082;");
                                soapActionUri.setStyle("-fx-background-color:#FFE082;");
                                postFix.setStyle("-fx-background-color:#FFE082;");
                                req.setStyle("-fx-background-color:#FFE082;");
                                gap.setStyle("-fx-background-color:#FFE082;");
                                facade.setStyle("-fx-background-color:#FFE082;");
                                target.setStyle("-fx-background-color:#FFE082;");
                                transform.setStyle("-fx-background-color:#FFE082;");
                                mask.setStyle("-fx-background-color:#FFE082;");
                                button.setStyle("-fx-background-color:#FFE082;");
                            }

                            modifyColorUpladedFields(tvODto, tvUDto, tvList);

                        }
                        System.out.println("You have selected " + button.getText());
                    }
                });
            } else {
                if (mode.equalsIgnoreCase("remove")) {
                    button.setText("Remove");
                    //Adding Listener's for Remove button
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            for (TableViewDto temp : tvList) {
                                if (temp.getAdd().equals(e.getSource())) {
                                    tvList.remove(temp);
                                    break;
                                }
                            }
                            rearrange(tvList);
                            System.out.println("You have selected " + button.getText());
                        }
                    });
                } else {
                    button.setText("Save");
                    //Adding Listener's for Save button
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {

                            //updating the color of Modified row to orange
                            name.setStyle("-fx-background-color:#FFE082;");
                            abbr.setStyle("-fx-background-color:#FFE082;");
                            interfac.setStyle("-fx-background-color:#FFE082;");
                            method.setStyle("-fx-background-color:#FFE082;");
                            soapActionUri.setStyle("-fx-background-color:#FFE082;");
                            postFix.setStyle("-fx-background-color:#FFE082;");
                            req.setStyle("-fx-background-color:#FFE082;");
                            gap.setStyle("-fx-background-color:#FFE082;");
                            facade.setStyle("-fx-background-color:#FFE082;");
                            target.setStyle("-fx-background-color:#FFE082;");
                            transform.setStyle("-fx-background-color:#FFE082;");
                            mask.setStyle("-fx-background-color:#FFE082;");
                            button.setStyle("-fx-background-color:#FFE082;");

                            System.out.println("You have selected " + button.getText());
                        }
                    });
                }
            }
        }
        return (new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button));
    }

    public int getSno(ObservableList<TableViewDto> tvDto) {
        int sno = 0;
        for (TableViewDto temp : tvDto) {
            if (sno < temp.getSno()) {
                sno = temp.getSno();
            }
        }

        sn = ++sno;
        return (sn);
    }

    public void rearrange(ObservableList<TableViewDto> tvList) {
        SortedMap<Integer, TableViewDto> sMap = new TreeMap<Integer, TableViewDto>();
        tvList.forEach(temp -> {
            sMap.put(temp.getSno(), temp);
        });
        tvList.removeAll(tvList);
        for (Integer kInt : sMap.keySet()) {
            tvList.add(sMap.get(kInt));
        }
    }

    public TableViewDto copyTableViewContent(TableViewDto tvDto, TableViewDto temp) {
        tvDto.setSno(temp.getSno());
        tvDto.setName(temp.getName());
        tvDto.setAbbr(temp.getAbbr());
        tvDto.setInterfac(temp.getInterfac());
        tvDto.setMethod(temp.getMethod());
        tvDto.setSoapActionUri(temp.getSoapActionUri());
        tvDto.setPostFix(temp.getPostFix());
        tvDto.setReq(temp.getReq());
        tvDto.setGap(temp.getGap());
        tvDto.setFacade(temp.getFacade());
        tvDto.setTarget(temp.getTarget());
        tvDto.setTransform(temp.getTransform());
        tvDto.setMask(temp.getMask());
        return (tvDto);
    }

    @Override
    public void swapAdd(ObservableList<TableViewDto> tvList) {
        TableViewDto tvDto = new TableViewDto();
        int s = 0;
        int sno;
        for (TableViewDto temp : tvList) {
            if (temp.getAdd().getText().equalsIgnoreCase("add") && (s == 0)) {
                tvDto = temp;
                tvList.remove(temp);
                s = 1;
            }
//            System.out.println("SN within swap add before Swap:\t" + temp.getSno());
            if (s == 1) {
                for (TableViewDto temp1 : tvList) {
                    if (temp.getSno() < temp1.getSno()) {
                        sno = tvDto.getSno();
                        System.out.println("Temp Obj SN :\t" + tvDto.getSno());
                        tvDto.setSno(temp1.getSno());
                        temp1.setSno(sno);
                    }
                }
                break;
            }
//            System.out.println("SN within swap add After Swap:\t" + temp.getSno());
        }
        tvList.add(tvDto);
    }

    //Used to highlight the updated fields by comparing the original and the updated objects
    private void modifyColorUpladedFields(TableViewDto tvODto, TableViewDto tvUDto, ObservableList<TableViewDto> tvList) {

        if (!tvODto.getName().getText().equals(tvUDto.getName().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getName().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getAbbr().getText().equals(tvUDto.getAbbr().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getAbbr().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getInterfac().getValue().equals(tvUDto.getInterfac().getValue())) {
            changeColor(tvList, tvUDto.getName().getText()).getInterfac().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getMethod().getValue().equals(tvUDto.getMethod().getValue())) {
            changeColor(tvList, tvUDto.getName().getText()).getMethod().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getSoapActionUri().getText().equals(tvUDto.getSoapActionUri().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getSoapActionUri().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getPostFix().getText().equals(tvUDto.getPostFix().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getPostFix().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getReq().getText().equals(tvUDto.getReq().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getReq().setStyle("-fx-background-color:yellow;");
        }
        if (!tvODto.getGap().getText().equals(tvUDto.getGap().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getGap().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getFacade().getText().equals(tvUDto.getFacade().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getFacade().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getTarget().getText().equals(tvUDto.getTarget().getText())) {
            changeColor(tvList, tvUDto.getName().getText()).getTarget().setStyle("-fx-background-color:yellow;");;
        }
        if (tvODto.getTransform().isSelected() != tvUDto.getTransform().isSelected()) {
            changeColor(tvList, tvUDto.getName().getText()).getTransform().setStyle("-fx-background-color:yellow;");;
        }
        if (tvODto.getMask().isSelected() != tvUDto.getMask().isSelected()) {
            changeColor(tvList, tvUDto.getName().getText()).getMask().setStyle("-fx-background-color:yellow;");;
        }
    }

    //Returns the Table View Object by validating it against the operation name
    private TableViewDto changeColor(ObservableList<TableViewDto> tvList, String name) {
        TableViewDto tvDto = null;
        for (TableViewDto temp : tvList) {
            if (temp.getName().getText().equals(name)) {
                tvDto = temp;
                break;
            }
        }
        return (tvDto);
    }
    
    private boolean valueModified(TableViewDto tvODto,TableViewDto tvUDto){
        boolean op=false;
        if(!tvODto.getName().getText().equals(tvUDto.getName().getText())){
           op=true; 
        }
        if(!tvODto.getAbbr().getText().equals(tvUDto.getAbbr().getText())){
           op=true; 
        }
        if(!tvODto.getInterfac().getValue().equals(tvUDto.getInterfac().getValue())){
           op=true; 
        }
        if(!tvODto.getMethod().getValue().equals(tvUDto.getMethod().getValue())){
           op=true; 
        }
        if(!tvODto.getSoapActionUri().getText().equals(tvUDto.getSoapActionUri().getText())){
           op=true; 
        }
        if(!tvODto.getPostFix().getText().equals(tvUDto.getPostFix().getText())){
           op=true; 
        }
        if(!tvODto.getReq().getText().equals(tvUDto.getReq().getText())){
           op=true; 
        }
        if(!tvODto.getGap().getText().equals(tvUDto.getGap().getText())){
           op=true; 
        }
        if(!tvODto.getFacade().getText().equals(tvUDto.getFacade().getText())){
           op=true; 
        }
        if(!tvODto.getTarget().getText().equals(tvUDto.getTarget().getText())){
           op=true; 
        }
        if(tvODto.getTransform().isSelected()!=tvUDto.getTransform().isSelected()){
            op=true;
        }
        if(tvODto.getMask().isSelected()!=tvUDto.getMask().isSelected()){
            op=true;
        }
        return(op);
    }
}
