/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.controller;

import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.dto.TableViewDto;
import com.sandhata.gcat.application.service.FileOperationService;
import com.sandhata.gcat.application.service.OperationService;
import com.sandhata.gcat.application.service.Service;
import com.sandhata.gcat.application.service.impl.FileOperationServiceImpl;
import com.sandhata.gcat.application.service.impl.OperationServiceImpl;
import com.sandhata.gcat.application.service.impl.ServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import static java.lang.reflect.Array.set;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class OperationsController implements Initializable {

    @FXML
    private ListView<String> lExistingList;
    @FXML
    private ListView<String> lDesiredList;
    @FXML
    private Label instance;
    @FXML
    private TextField tfExisting;
    @FXML
    private Button bmoveToDesired;
    @FXML
    private Button bmoveToExisting;
    @FXML
    private Label count;
    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;
    @FXML
    private Button partnerList;
    @FXML
    private ListView<String> lvpartner;

    public static int sn = 0;

    private OperationService operationService = new OperationServiceImpl();

    /**
     * Initializes the controller class.
     */
    private Service service = new ServiceImpl();

    private FileOperationService fileOperationService = new FileOperationServiceImpl();
    private Properties tempApplicationProp = new Properties();
    private Properties tempOperationProp = new Properties();
    private Properties fileOperationProp = new Properties();
    private Properties applicationProp = new Properties();

    private Set<String> listViewSet = new TreeSet<String>();
    private String inst = "";
    private String partner = "";
    private String operation = "";
    private String mode="";

    File file = new File("");
    private ObservableList<String> interfaceOList = FXCollections.observableArrayList();
    private ObservableList<String> methodOList = FXCollections.observableArrayList();
    private ObservableList<String> existingOList = FXCollections.observableArrayList();
    private ObservableList<TableViewDto> tvList;
    private ObservableList<String> partnerOList = FXCollections.observableArrayList();

    private Set<PartnerOperation> partnerOperationSet = new TreeSet<PartnerOperation>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lExistingList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lDesiredList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /**
     * 
     * @param interfaceOList interface is soap/REST
     * @param methodList What are the available request for REST
     * @param tvList List of information that are already present in the grid
     * @param mode browse or generate mode. Based on this the save button will be enabled/disabled in the operation window
     * @param instance Passing the interface selected by user as an argument from previous frame
     */
    public void initializeObservableList(ObservableList<String> interfaceOList, ObservableList<String> methodList, ObservableList<TableViewDto> tvList, String mode, String instance) {
        //Initializing Table View
        this.tvList = tvList;
        this.interfaceOList = interfaceOList;
        this.methodOList = methodList;
        this.mode=mode;
        this.inst=instance;
        reInitializeExistingList();
        count.setText("" + listViewSet.size());
    }

    @FXML
    private void existingLVMouseClicked(MouseEvent event) {
        lvpartner.setVisible(false);
        partner = "";
    }

    @FXML
    private void desiredLVMouseClicked(MouseEvent event) {
    }

    @FXML
    private void existingLVSearchKeyTyped(KeyEvent event) {
        String searchKey = tfExisting.getText();
//        reInitializeExistingList();
        if (searchKey.equalsIgnoreCase("")) {
            String line = "";
            listViewSet = new TreeSet<String>();
            reInitializeExistingList();
            lExistingList.getItems().removeAll(lDesiredList.getItems());
        } else {
            System.out.println("Key Typed :t" + searchKey);
        }

    }

    /**
     * Used to move the contents to the desired items side
     * Once all the items are moved then we can click on Save for the changes to reflect
     * @param event ActionEvent
     */
    @FXML
    private void moveToDesiredClicked(ActionEvent event) {

        if (partner.equalsIgnoreCase("")) {
            lDesiredList.getItems().addAll(lExistingList.getSelectionModel().getSelectedItems());
            lExistingList.getItems().removeAll(lExistingList.getSelectionModel().getSelectedItems());
        } else {
            lDesiredList.getItems().add(lExistingList.getSelectionModel().getSelectedItem() + ";" + partner);
            lExistingList.getItems().removeAll(lExistingList.getSelectionModel().getSelectedItems());
        }

    }

    /**
     * Used to move the contents from Right to Left (ie., back to the original side)
     * Just incase if the user has selected the wrong operation by mistake.
     * @param event ActionEvent
     */
    @FXML
    private void moveToExistingClicked(ActionEvent event) {
        for (String temp : lDesiredList.getSelectionModel().getSelectedItems()) {
            if (temp.contains(";")) {
                lExistingList.getItems().add(temp.substring(0, temp.indexOf(";")));
                lDesiredList.getItems().remove(temp);
            } else {
                lExistingList.getItems().add(temp);
                lDesiredList.getItems().remove(temp);
            }
        }

    }

    /**
     * Used to save the set of operations that are selected by users these 
     * saved operations will be moved to the grid in Application Controller
     * @param event ActionEvent
     */
    @FXML
    private void saveClicked(ActionEvent event) {
        String config = "";
        try {

            operationService.readFromFile("edit", file, inst, lDesiredList, fileOperationService, instance.getText(), partner, interfaceOList, methodOList, tvList);
            JOptionPane.showMessageDialog(null, "Data read Successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);

            operationService.swapAdd(tvList);

            //Closing the window once we have saved the content
            Stage stage = (Stage) bSave.getScene().getWindow();
            stage.close();

        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Caught in Exception inside class :\tOperationController\tMethod :\tsaveClicked\nAnd the exception is :\t" + exc.toString());
        }

    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        Stage stage = (Stage) bCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Used to view the partner for the selected Operation
     * @param event ActionEvent
     */
    @FXML
    private void partnerClicked(ActionEvent event) {
        lvpartner.setVisible(true);
        operation = lExistingList.getSelectionModel().getSelectedItem();
        Set<String> partnerSet = new TreeSet<String>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileOperationService.getInputFilePath(inst, "facadeaccess.cfg")));
            while ((line = br.readLine()) != null) {
                if (line.startsWith("partner_")) {
                    line = line.substring(line.indexOf("_") + 1, line.length());
                    String[] data = line.split(Pattern.quote("|"));
                    if (data[2].equals(operation)) {
                        partnerSet.add(data[0]);
                    }
                }
            }
            br.close();
            partnerOList.removeAll(partnerOList);
            partnerOList.addAll(partnerSet);
            lvpartner.setItems(partnerOList);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 
     * @param event MouseEvent
     */
    @FXML
    private void partnerLisiViewMouseClicked(MouseEvent event) {
        partner = lvpartner.getSelectionModel().getSelectedItem();
        System.out.println("Selected Partner :\t" + partner);
    }

    /**
     * It's more like a reset. It's used for re-initializing the existing list
     */
    public void reInitializeExistingList() {
        String line = "";
        try {
            //Getting the Path of FacadeAccess.cfg from the Property File
            FileInputStream tempApplicationStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\TempApplication.properties"));
            tempApplicationProp.load(tempApplicationStream);

            //If mode is Generate then enable Save Button
            if (mode.equalsIgnoreCase("browse")) {
                bSave.setDisable(true);
                System.out.println("tempApplicationProp.getProperty(\"Mode\").equalsIgnoreCase(\"browse\") :\t" + mode.equalsIgnoreCase("browse"));
            } else {
                if (mode.equalsIgnoreCase("generate")) {
                    System.out.println("tempApplicationProp.getProperty(\"Mode\").equalsIgnoreCase(\"generate\") :\t" + mode.equalsIgnoreCase("generate"));
                    bSave.setDisable(false);
                }
            }

            instance.setText(inst);

            BufferedReader br = new BufferedReader(new FileReader(fileOperationService.getInputFilePath(inst, "targetoperation.cfg")));
            Set<String> removeSet = new TreeSet<String>();
            while ((line = br.readLine()) != null) {
                if (line.startsWith("service_")) {
                    line = line.substring(line.indexOf("_") + 1, line.length());
                    String[] data = line.split(Pattern.quote("|"));
                    for (TableViewDto temp : tvList) {
                        if ((data[0].equals(temp.getName().getText()))) {
                            removeSet.add(data[0]);
                        }
                        listViewSet.add(data[0]);
                    }
                }
            }
            br.close();
            tempApplicationStream.close();
            listViewSet.removeAll(removeSet);
            lExistingList.getItems().addAll(listViewSet);

//            lExistingList.setItems(existingOList);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
