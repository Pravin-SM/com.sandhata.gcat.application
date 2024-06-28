/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service.impl;

import com.sandhata.gcat.application.config.Log4Config;
import com.sandhata.gcat.application.config.model.PartnerDetails;
import com.sandhata.gcat.application.config.model.PartnerInstanceInfo;
import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.config.model.Project;
import com.sandhata.gcat.application.controller.ApplicationController;
import static com.sandhata.gcat.application.controller.ApplicationController.sn;
import com.sandhata.gcat.application.dto.DataComparator;
import com.sandhata.gcat.application.dto.TableViewDto;
import com.sandhata.gcat.application.service.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.commons.collections4.SortedBidiMap;
import org.apache.log4j.Logger;
import com.sandhata.gcat.application.service.FileOperationService;
import com.sandhata.gcat.application.service.GitService;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.scene.DepthTest;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;

/**
 *
 * @author admin
 */
public class ServiceImpl implements Service {

    private FileOperationService fileOpService = new FileOperationServiceImpl();
    private GitService gitService = new GitServiceImpl();

    private boolean configedChanged = false;
    private int sn = 0;
    private Properties prop = new Properties();
    File file = new File("");
    private Logger logger = (new Log4Config()).getLogger(file.getAbsolutePath() + "\\gcatLogs.log", "ApplicationController");

    private List<PartnerOperation> savedPartnerOperationSet = new ArrayList<PartnerOperation>();

    private TableViewDto tvODto;
    private TableViewDto tvUDto;

    @Override
    public List<String> getAllInterfaces() {
        List<String> interfaceList = new ArrayList<String>();
        interfaceList.add("SOAP");
        interfaceList.add("REST");
        return (interfaceList);
    }

    @Override
    public List<String> getAllMethods() {
        List<String> methodList = new ArrayList<String>();
        methodList.add("POST");
        methodList.add("GET");
        methodList.add("PUT");
        methodList.add("DELETE");
        return (methodList);
    }

    @Override
    public List<String> getAllInstance() {
        List<String> instanceList = null;
        try {

            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            File instancesFolders = new File(prop.getProperty("config_file_base_path"));

            String[] directories = instancesFolders.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return (new File(current, name).isDirectory());
                }
            });

            instanceList = Arrays.asList(directories);
            inputStream.close();
        } catch (Exception exc) {
            logger.error(exc.toString());
        } finally {
            return (instanceList);
        }
    }

    @Override
    public List<String> getAllPartnerDetails() {
        List<String> partnerDetailsList = new ArrayList<String>();
        partnerDetailsList.add("SANDHATA-INTERNAL");
        partnerDetailsList.add("SANDHATA-EXTERNAL");
        return (partnerDetailsList);
    }

    @Override
    public Set<String> getAllPartnerGroup(String instanceName) {
        Set<String> partnerGroupList = new TreeSet<String>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            File partnerFile = new File(prop.getProperty("config_file_base_path") + "\\" + instanceName + "\\FacadeAccess.cfg");
            BufferedReader br = new BufferedReader(new FileReader(partnerFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("partner_")) {
                    partnerGroupList.add(line.substring(line.indexOf("partner_") + (line.substring(0, line.indexOf("_") + 1)).length(), line.indexOf("|")));
                }
            }
            br.close();
            inputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            return (partnerGroupList);
        }
    }

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

        //Listerner for interface combo box to check if the selection is SOAP/REST
        interfac.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (interfac.getValue() != null) {
                    if (interfac.getValue().equalsIgnoreCase("soap")) {
                        method.setItems(null);
                        transform.setDisable(false);
                        mask.setDisable(false);
                    } else {
                        method.setItems(methodOList);
                        transform.setDisable(true);
                        mask.setDisable(true);
                    }
                }
            }
        });

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

                            //updating the new color of row to red/pink
                            tvDto.getName().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getAbbr().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getInterfac().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getMethod().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getSoapActionUri().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getPostFix().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getReq().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getGap().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getFacade().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getTarget().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getTransform().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getMask().setStyle("-fx-background-color:#FFC0CB;");
                            tvDto.getAdd().setStyle("-fx-background-color:#FFC0CB;");
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

                //Adding Listener's for Edit button
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

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
                            System.out.println("You have selected " + button.getText());
                        }
                    });
                }
            }
        }
        return (new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button));
    }

    @Override
    public void createNewEditRow(Set<PartnerOperation> partnerDetailsSet, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList, List<PartnerOperation> partnerOperationList) {
        for (PartnerOperation temp : partnerDetailsSet) {
            int sno = getSno(tvList);

            TextField name = new TextField();
            name.setPromptText("Name (with version#)");
            name.setText(temp.getOperationName());
            name.setEditable(false);
            TextField abbr = new TextField();
            abbr.setPromptText("Abbr (with version#)");
            abbr.setText(temp.getAbbr());
            abbr.setEditable(false);

            ComboBox<String> interfac = new ComboBox<String>();
            interfac.setPrefWidth(100);

            ComboBox<String> method = new ComboBox<String>();
            method.setPrefWidth(100);
            interfac.setItems(interfaceOList);
            interfac.setValue(temp.getInterfac());

            interfac.setDisable(true);
            method.setDisable(true);

            TextField soapActionUri = new TextField();
            soapActionUri.setPromptText("SOAPAction (SOAP)/URI (JSON)");
            soapActionUri.setText(temp.getSoapActionUri());
            soapActionUri.setEditable(false);

            TextField postFix = new TextField();
            postFix.setPromptText("DestinationPostfix");
            postFix.setText(temp.getPostFix());
            postFix.setEditable(false);

            TextField req = new TextField();
            req.setPromptText("10");
            req.setText(temp.getReq());
            req.setEditable(false);

            TextField gap = new TextField();
            gap.setPromptText("10");
            gap.setText(temp.getGap());
            gap.setEditable(false);

            TextField facade = new TextField();
            facade.setPromptText("35");
            facade.setText("" + (Integer.parseInt(temp.getFacade()) / 1000));
            facade.setEditable(false);

            TextField target = new TextField();
            target.setPromptText("32");
            target.setText("" + (Integer.parseInt(temp.getTarget()) / 1000));
            target.setEditable(false);

            CheckBox transform = new CheckBox();
            transform.selectedProperty().setValue(temp.getTransform());
            transform.setDisable(true);

            CheckBox mask = new CheckBox();
            transform.selectedProperty().setValue(temp.getMask());
            mask.setDisable(true);

            Button button = new Button();
            button.setPrefWidth(70);

            button.setText("Edit");

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

            //Listerner for interface combo box to check if the selection is SOAP/REST
            interfac.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if (interfac.getValue() != null) {
                        System.out.println("Interface :\t" + interfac.getValue());
                        if (interfac.getValue().equalsIgnoreCase("soap")) {
                            method.setItems(null);
                            transform.setDisable(false);
                            mask.setDisable(false);
                        } else {
                            method.setItems(methodOList);
                            transform.setDisable(true);
                            mask.setDisable(true);
                        }
                    }
                }
            });
            method.setValue(temp.getMethod());

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

                        //updating the color of existing row to green
//                        name.setStyle("-fx-background-color:#77DD77;");
//                        abbr.setStyle("-fx-background-color:#77DD77;");
//                        interfac.setStyle("-fx-background-color:#77DD77;");
//                        method.setStyle("-fx-background-color:#77DD77;");
//                        soapActionUri.setStyle("-fx-background-color:#77DD77;");
//                        postFix.setStyle("-fx-background-color:#77DD77;");
//                        req.setStyle("-fx-background-color:#77DD77;");
//                        gap.setStyle("-fx-background-color:#77DD77;");
//                        facade.setStyle("-fx-background-color:#77DD77;");
//                        target.setStyle("-fx-background-color:#77DD77;");
//                        transform.setStyle("-fx-background-color:#77DD77;");
//                        mask.setStyle("-fx-background-color:#77DD77;");
//                        button.setStyle("-fx-background-color:#77DD77;");
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
                        configedChanged = true;
                        button.setText("Edit");

                        //Updating the values for tvUDto
                        TableViewDto tempUDto = new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button);
                        PartnerOperation tUPOperation = new PartnerOperation(tempUDto);
                        System.out.println("Partner Operation Updated Content :\t" + tUPOperation.toString());
                        tvUDto = new TableViewDto(tUPOperation);

                        //Validating if any changes is been made
                        if (valueModified(tvODto,tvUDto)) {
                            //updating the color of modified row to orange
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

                        TableViewDto tvDto = new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button);

                        System.out.println("Sno :\t" + sno + "\nName :\t" + name.getText() + "\nAbbr :\t" + abbr.getText() + "\nInterface :\t" + interfac.getValue() + "\nMethod :\t" + method.getValue() + "\nSoap Action URI :\t" + soapActionUri.getText() + "\nPostFix :\t" + postFix.getText() + "\nReq :\t" + req.getText() + "\nGap :\t" + gap.getText() + "\nFacade :\t" + facade.getText() + "\nTarget :\t" + target.getText() + "\nTransform :\t" + transform.isSelected() + "\nMask :\t" + mask.isSelected());
                        savedPartnerOperationSet.add(new PartnerOperation(name.getText(), abbr.getText(), interfac.getValue(), method.getValue(), soapActionUri.getText(), postFix.getText(), req.getText(), gap.getText(), facade.getText(), target.getText(), transform.isSelected(), mask.isSelected()));
                    }
                }
            });
            TableViewDto newTableViewDto = new TableViewDto(sno, name, abbr, interfac, method, soapActionUri, postFix, req, gap, facade, target, transform, mask, button);
            partnerOperationList.add(new PartnerOperation(name.getText(), abbr.getText(), interfac.getValue(), method.getValue(), soapActionUri.getText(), postFix.getText(), req.getText(), gap.getText(), facade.getText(), target.getText(), transform.isSelected(), mask.isSelected()));
            tvList.add(newTableViewDto);
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
        //While Adding new Operation validating if the transfor and mask is checked for rest operation
        if (tvDto.getInterfac().getValue().equalsIgnoreCase("rest")) {
            temp.getTransform().selectedProperty().setValue(false);
            temp.getMask().selectedProperty().setValue(false);
        }
        tvDto.setTransform(temp.getTransform());
        tvDto.setMask(temp.getMask());
        return (tvDto);
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

    @Override
    public ObservableList<TableViewDto> resetAll(ObservableList<String> interfaceOList, ObservableList<String> methodOList, ObservableList<TableViewDto> tvList) {
        sn = 0;
        tvList.removeAll(tvList);
        tvList.add(createNewRow(interfaceOList, methodOList, "add", tvList));
        return (tvList);
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
//        tvList.forEach(temp -> {
//            System.out.println(temp.toString());
//        });
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

    @Override
    public void fileOperation(Project project, PartnerInstanceInfo partnerInstanceInfo, PartnerDetails partnerDetails, List<PartnerOperation> partnerOperationList, String fileName) {
        fileOpService.newPartNewOp(project, partnerInstanceInfo, partnerDetails, partnerOperationList, fileName);
    }

    @Override
    public List<PartnerOperation> getNewPartner(ObservableList<TableViewDto> tvList, String lable) {
        List<PartnerOperation> partnerOperationList = new ArrayList<PartnerOperation>();
        for (TableViewDto temp : tvList) {
            if (temp.getAdd().getText().equalsIgnoreCase(lable)) {
                PartnerOperation t = new PartnerOperation(temp);
                partnerOperationList.add(t);
            }
        }
        return (partnerOperationList);
    }

    @Override
    public List<String> getFiles(String operation) {
        return (fileOpService.getFiles(operation));
    }

    @Override
    public PartnerDetails getPartnerDetails(String instance, String partner) {
        return (fileOpService.getPartnerDetails(instance, partner));
    }

    @Override
    public Set<PartnerOperation> exPartNewOp(String instance, String partner, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList) {
        Set<PartnerOperation> partnerOperationSet = fileOpService.exPartNewOp(instance, partner, tvList, interfaceOList, methodOList);
        partnerOperationSet.forEach(temp -> {
            System.out.println(temp.toString());
        });
        return (partnerOperationSet);
    }

    //Validate the information uder Project, checking if all the fields are populated or not
    @Override
    public String validateProject(Project project) {
        String missingFields = "";
        if (project.getName().equals(null) || project.getName() == null || project.getName().equals("") || project.getName().equals(" ")) {
            missingFields += "Name\n";
        }
        if (project.getReleaseBranch().equals(null) || project.getReleaseBranch() == null || project.getReleaseBranch().equals("") || project.getReleaseBranch().equals(" ")) {
            missingFields += "ReleaseBranch\n";
        }
        if (project.getDescription().equals(null) || project.getDescription() == null || project.getDescription().equals("") || project.getDescription().equals(" ")) {
            missingFields += "Description\n";
        }
        if (project.getJira().equals(null) || project.getJira() == null || project.getJira().equals("") || project.getJira().equals(" ")) {
            missingFields += "JIRA#\n";
        }
        if (project.getAuthorEmailId().equals(null) || project.getAuthorEmailId() == null || project.getAuthorEmailId().equals("") || project.getAuthorEmailId().equals(" ")) {
            missingFields += "Author Email ID\n";
        }
        return (missingFields);
    }

    //Flag to validate if any configuration is been changed or not
    @Override
    public boolean isConfigedChanged() {
        return configedChanged;
    }

    @Override
    public void setConfigedChanged(boolean configedChanged) {
        this.configedChanged = configedChanged;
    }

    @Override
    public List<PartnerOperation> getSavedPartnerOperationSet() {
        return savedPartnerOperationSet;
    }

    @Override
    public void setSavedPartnerOperationSet(List<PartnerOperation> savedPartnerOperationSet) {
        this.savedPartnerOperationSet = savedPartnerOperationSet;
    }

    //Removes all the records except record with Add button
    @Override
    public void removeAllExceptAdd(ObservableList<TableViewDto> tvList) {
        ObservableList<TableViewDto> tempList = FXCollections.observableArrayList();
        tvList.forEach(temp -> {
            if (!temp.getAdd().getText().equalsIgnoreCase("add")) {
                tempList.add(temp);
            } else {
                temp.setSno(1);
            }
        });
        tvList.removeAll(tempList);
    }

    @Override
    public void checkOut() {
        gitService.checkOut();
    }

    @Override
    public void checkin() {
        gitService.checkIn();
    }

    //Used to highlight the updated fields by comparing the original and the updated objects
    private void modifyColorUpladedFields(TableViewDto tvODto, TableViewDto tvUDto, ObservableList<TableViewDto> tvList) {

        if (!tvODto.getName().getText().equals(tvUDto.getName().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getName().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getAbbr().getText().equals(tvUDto.getAbbr().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getAbbr().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getInterfac().getValue().equals(tvUDto.getInterfac().getValue())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getInterfac().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getMethod().getValue().equals(tvUDto.getMethod().getValue())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getMethod().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getSoapActionUri().getText().equals(tvUDto.getSoapActionUri().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getSoapActionUri().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getPostFix().getText().equals(tvUDto.getPostFix().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getPostFix().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getReq().getText().equals(tvUDto.getReq().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getReq().setStyle("-fx-background-color:yellow;");
        }
        if (!tvODto.getGap().getText().equals(tvUDto.getGap().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getGap().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getFacade().getText().equals(tvUDto.getFacade().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getFacade().setStyle("-fx-background-color:yellow;");;
        }
        if (!tvODto.getTarget().getText().equals(tvUDto.getTarget().getText())) {
            changeColorObject(tvList, tvUDto.getName().getText()).getTarget().setStyle("-fx-background-color:yellow;");;
        }
        if (tvODto.getTransform().isSelected() != tvUDto.getTransform().isSelected()) {
            changeColorObject(tvList, tvUDto.getName().getText()).getTransform().setStyle("-fx-background-color:yellow;");;
        }
        if (tvODto.getMask().isSelected() != tvUDto.getMask().isSelected()) {
            changeColorObject(tvList, tvUDto.getName().getText()).getMask().setStyle("-fx-background-color:yellow;");;
        }
    }

    //Returns the Table View Object by validating it against the operation name
    private TableViewDto changeColorObject(ObservableList<TableViewDto> tvList, String name) {
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
    
    //comparing and merging the data that are modified
    @Override
    public void mergeCfgData(String instance, List<File> fileNames){
        fileOpService.mergeCfgData(instance, fileNames);
    }
}
