/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.controller;

import com.sandhata.gcat.application.config.model.PartnerDetails;
import com.sandhata.gcat.application.config.model.PartnerInstanceInfo;
import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.config.model.Project;
import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.dto.TableViewDto;
import com.sandhata.gcat.application.service.LicenseValidationService;
import com.sandhata.gcat.application.service.Service;
import com.sandhata.gcat.application.service.impl.LicenseValidationServiceImpl;
import com.sandhata.gcat.application.service.impl.ServiceImpl;
import com.sun.javafx.stage.StageHelper;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author s_m_p
 */
public class ApplicationController implements Initializable {

    @FXML
    private BorderPane borderPane1;
    @FXML
    private MenuBar home_menubar;
    @FXML
    private Menu mbMenu;
    @FXML
    private MenuItem mbNew;
    @FXML
    private MenuItem mbOpen;
    @FXML
    private MenuItem mbSave;
    @FXML
    private MenuItem mbSaveAs;
    @FXML
    private MenuItem mbClose;
    @FXML
    private Menu mbEdit;
    @FXML
    private MenuItem mbSetting;
    @FXML
    private MenuItem mbGitConfiguration;
    @FXML
    private MenuItem mbUploadLicense;
    @FXML
    private Menu mbHelp;
    @FXML
    private MenuItem mbViewDemo;
    @FXML
    private MenuItem mbAbout;
    @FXML
    private BorderPane borderPane2;
    @FXML
    public TableView<TableViewDto> tableView;
    @FXML
    private TableColumn<TableViewDto, Integer> tvSn;
    @FXML
    private TableColumn<TableViewDto, TextField> tvName;
    @FXML
    private TableColumn<TableViewDto, TextField> tvAbbr;
    @FXML
    private TableColumn<TableViewDto, TextField> tvInterface;
    @FXML
    private TableColumn<TableViewDto, TextField> tvMethod;
    @FXML
    private TableColumn<TableViewDto, TextField> tvSoapAction;
    @FXML
    private TableColumn<TableViewDto, TextField> tvPostFix;
    @FXML
    private TableColumn<TableViewDto, TextField> tvReq;
    @FXML
    private TableColumn<TableViewDto, TextField> tvGap;
    @FXML
    private TableColumn<TableViewDto, TextField> tvFacade;
    @FXML
    private TableColumn<TableViewDto, TextField> tvTarget;
    @FXML
    private TableColumn<TableViewDto, CheckBox> tvTransform;
    @FXML
    private TableColumn<TableViewDto, CheckBox> tvMask;
    @FXML
    private TableColumn<TableViewDto, Button> tvAddRemove;
    @FXML
    private FlowPane flowPaneTableView;
    @FXML
    private Button bCheckout;
    @FXML
    private Button bGenerate;
    @FXML
    private Button bViewDiff;
    @FXML
    private Button bCheckIn;
    @FXML
    private Button bResetAll;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private VBox vBoxHeading;
    @FXML
    private HBox hBoxTitle;
    @FXML
    private Label lTitle;
    @FXML
    private ImageView iCompanyLogo;
    @FXML
    private HBox hbSelectionModel;
    @FXML
    private RadioButton rbBrowse;
    @FXML
    private ToggleGroup GCATRB;
    @FXML
    private RadioButton rbGenerate;
    @FXML
    private TextField tfName;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfAuthorEmail;
    @FXML
    private TextField tfReleaseBranch;
    @FXML
    private TextField tfJira;
    @FXML
    private RadioButton rbNew;
    @FXML
    private RadioButton rbExisting;
    @FXML
    private RadioButton rbRemove;
    @FXML
    private ComboBox<String> cbPartner;
    @FXML
    private Button bLoadExisting;
    @FXML
    private CheckBox ckbDecommissioned;
    @FXML
    private Button bLoad;
    @FXML
    private ComboBox<String> cbInstance;
    @FXML
    private ComboBox<String> cbGroup;
    @FXML
    private TextField tf1partnerDetails;
    @FXML
    private TextField tf2partnerDetails;
    @FXML
    private TextField tf3partnerDetails;
    @FXML
    private TextField tf4partnerDetails;
    @FXML
    private TextField tf5partnerDetails;

    FileHandler fh;
    SimpleFormatter simpleFormatter = new SimpleFormatter();
    private File file = new File("");

    Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    /**
     * Initializes the controller class.
     */
    private Stage settingsStage = new Stage();
    private Stage operationStage = new Stage();
    private Stage uploadLicenseStage = new Stage();
    private Stage gitConfigurationStage = new Stage();
    private Service service = new ServiceImpl();

    private String mode = "";
    private String option = "";

    private boolean loadExisting = false;
    private boolean load = false;
    private boolean generated = false;

    public static int sn = 0;
    private int sizeOfExistingTvList = 0;

    private ObservableList<TableViewDto> tvList = FXCollections.observableArrayList();
    private ObservableList<String> interfaceOList = FXCollections.observableArrayList();
    private ObservableList<String> methodOList = FXCollections.observableArrayList();
    private ObservableList<String> instanceOList = FXCollections.observableArrayList();
    private ObservableList<String> partnerDetailsOList = FXCollections.observableArrayList();
    private ObservableList<String> partnerGroupOList = FXCollections.observableArrayList();

    private Set<PartnerOperation> savedPartnerOperationSet = new TreeSet<PartnerOperation>();
    private List<PartnerOperation> partnerOperationLoadedList = new ArrayList<PartnerOperation>();

    private LicenseValidationService licenseValidationService = new LicenseValidationServiceImpl();

    //used to send as reference to Operation scene
    ObservableList<String> desirableOList = FXCollections.observableArrayList();

    private boolean licenseValidation = GCATConstants.FALSE;

    /**
     * Initializes the all the combo boxes with the values extracted from the
     * cfg files.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeApplicationController();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        bCheckout.setDisable(false);
        bCheckIn.setDisable(true);

        //Loading all images
        loadAllImages();
        //getting the contents of interface combobox
        interfaceOList.addAll(service.getAllInterfaces());
        //getting the contents of method combobox
        methodOList.addAll(service.getAllMethods());
        //Initializing the Instance Combobox
        cbInstance.setItems(instanceOList);
        //Initializing Partner Details Combobox
        partnerDetailsOList.addAll(service.getAllPartnerDetails());
        cbGroup.setItems(partnerDetailsOList);
        //Initializing Partner Group Combobox
//        partnerGroupOList.addAll(service.getAllPartnerGroup());
//        cbPartner.setItems(partnerGroupOList);

        //initializing table view
        tvSn.setCellValueFactory(new PropertyValueFactory<TableViewDto, Integer>("sno"));
        tvName.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("name"));
        tvAbbr.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("abbr"));
        tvInterface.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("interfac"));
        tvInterface.setMaxWidth(100);
        tvInterface.setMinWidth(100);
        tvMethod.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("method"));
        tvMethod.setMaxWidth(100);
        tvMethod.setMinWidth(100);
        tvSoapAction.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("soapActionUri"));
        tvPostFix.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("postFix"));
        tvReq.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("req"));
        tvGap.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("gap"));
        tvFacade.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("facade"));
        tvTarget.setCellValueFactory(new PropertyValueFactory<TableViewDto, TextField>("target"));
        tvTransform.setCellValueFactory(new PropertyValueFactory<TableViewDto, CheckBox>("transform"));
        tvMask.setCellValueFactory(new PropertyValueFactory<TableViewDto, CheckBox>("mask"));
        tvAddRemove.setCellValueFactory(new PropertyValueFactory<TableViewDto, Button>("add"));
        TableView.TableViewSelectionModel<TableViewDto> udTimeSheetModelList = tableView.getSelectionModel();
        tvList.add(service.createNewRow(interfaceOList, methodOList, "add", tvList));
        tableView.setItems(tvList);
        tvList.forEach(temp -> {
            temp.getName().setDisable(true);
            temp.getAbbr().setDisable(true);
            temp.getInterfac().setDisable(true);
            temp.getMethod().setDisable(true);
            temp.getSoapActionUri().setDisable(true);
            temp.getPostFix().setDisable(true);
            temp.getReq().setDisable(true);
            temp.getGap().setDisable(true);
            temp.getFacade().setDisable(true);
            temp.getTarget().setDisable(true);
            temp.getTransform().setDisable(true);
            temp.getMask().setDisable(true);
            temp.getAdd().setDisable(true);
        });
    }

    @FXML
    private void menubarNewClicked(ActionEvent event) {
    }

    @FXML
    private void menubarOpenClicked(ActionEvent event) {
    }

    @FXML
    private void menubarSaveClicked(ActionEvent event) {
    }

    @FXML
    private void menubarSaveAsClicked(ActionEvent event) {
    }

    @FXML
    private void menuClicked(ActionEvent event) {
    }

    @FXML
    private void menuValidation(Event event) {
    }

    @FXML
    private void menubarSettingClicked(ActionEvent event) {
        try {
            if (!StageHelper.getStages().contains(settingsStage)) {
                FXMLLoader settingStageLoader = new FXMLLoader(getClass().getResource("/fxml/SettingsFXML.fxml"));
                Parent settingsStageParent = (Parent) settingStageLoader.load();
                settingsStage.setResizable(false);
                settingsStage.setTitle("Settings");
                settingsStage.setScene(new Scene(settingsStageParent));
                settingsStage.show();
            } else {
                logger.warn("The Window is already open\nYou can not open multiple instance of this window...Sorry!!!");
                JOptionPane.showMessageDialog(null, "The Window is already open\nYou can not open multiple instance of this window...Sorry!!!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception exc) {
            logger.error(exc.toString());
            System.out.println("Caught in Exception inside Class :\tApplication Controller\tMethod :\tmenubarSettingClicked\nAnd the exception is :\t" + exc);
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tApplication Controller\tMethod :\tmenubarSettingClicked\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();

        }
    }

    @FXML
    private void menubarEditClicked(ActionEvent event) {
    }

    @FXML
    private void menubarViewDemoClicked(ActionEvent event) {
    }

    /**
     *
     * @param event
     */
    @FXML
    private void menubarAboutClicked(ActionEvent event) {
        try {
            File file = new File("");
            File docFile = new File(file.getAbsolutePath() + "/GCAT User Guide.docx");
            Desktop.getDesktop().open(docFile);
        } catch (IOException ioe) {
            System.out.println("Caught in Exception inside Class :\tApplicationController\tMethod :\tmenubarAboutClicked\n And the exception is \n" + ioe + "\n");
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception exc) {
            System.out.println("Caught in Exception inside Class :\tApplicationController\tMethod :\tmenubarAboutClicked\n And the exception is \n" + exc + "\n");
            logger.error(exc.toString());
            exc.printStackTrace();
        }
    }

    @FXML
    private void menubarGitConfigurationClicked(ActionEvent event) {
        try {
            if (!StageHelper.getStages().contains(gitConfigurationStage)) {
                FXMLLoader gitConfigurationStageLoader = new FXMLLoader(getClass().getResource("/fxml/GitConfigurationFXML.fxml"));
                Parent gitConfigurationStageParent = (Parent) gitConfigurationStageLoader.load();
                gitConfigurationStage.setResizable(false);
                gitConfigurationStage.setTitle("Git Configuration");
                gitConfigurationStage.setScene(new Scene(gitConfigurationStageParent));
                gitConfigurationStage.show();
            } else {
                logger.warn("The Window is already open\nYou can not open multiple instance of this window...Sorry!!!");
                JOptionPane.showMessageDialog(null, "The Window is already open\nYou can not open multiple instance of this window...Sorry!!!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception exc) {
            logger.error(exc.toString());
            System.out.println("Caught in Exception inside Class :\tApplication Controller\tMethod :\tmenubarSettingClicked\nAnd the exception is :\t" + exc);
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tApplication Controller\tMethod :\tmenubarSettingClicked\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();

        }
    }

    @FXML
    private void menubarHelpClicked(ActionEvent event) {
    }

    @FXML
    private void tableViewStart(SortEvent<String> event) {
    }

    @FXML
    private void checkoutClicked(ActionEvent event) {
        service.checkOut();
    }

    /**
     * Used to generate the set of configuration file in the desired output path
     * based on the input given by user. Based on the User Input any generate
     * operation will fall under one of the 6 scenario.
     * <p>
     * 1. New Partner New Operation 2. New Partner Existing Operation 3. New
     * Partner New + Existing Operation 4. Existing Partner New Operation 5.
     * Existing Partner Existing Operation 6. Existing Partner New + Existing
     * Operation If the condition doesn't meet any of the scenario, then it
     * throws an error message.
     *
     * @param event ActionEvent
     */
    @FXML
    private void generateClicked(ActionEvent event) {
        System.out.println("Mode :\t" + mode + "\tOption :\t" + option);

        Project project = new Project(tfName.getText(), tfReleaseBranch.getText(), taDescription.getText(), tfJira.getText(), tfAuthorEmail.getText());
        Properties prop = new Properties();
        if (service.validateProject(project).equals("")) {

            boolean generateSuccess = false;
            try {
                FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
                prop.load(inputStream);

                System.out.println("mode.equalsIgnoreCase(\"generate\") (option.equalsIgnoreCase(\"new\")) :\t" + (mode.equalsIgnoreCase("generate") && (option.equalsIgnoreCase("new"))));

                //Condition for New Partner and New Operation
                if (mode.equalsIgnoreCase("generate") && (option.equalsIgnoreCase("new"))) {

                    //Collecting all the new entries under operation
                    List<PartnerOperation> partnerOperationList = new ArrayList<PartnerOperation>();
                    partnerOperationList.addAll(service.getNewPartner(tvList, "remove"));

                    System.out.println("New Partner New Operation \t!partnerOperationList.isEmpty() && !load && !loadExisting :\t" + (!partnerOperationList.isEmpty() && !load && !loadExisting));
                    if (!partnerOperationList.isEmpty() && !load && !loadExisting) {

                        PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                        PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                        //Retriving all the files for NewPartner_NewOperation scenario
                        List<String> fileNames = service.getFiles("NewPartner_NewOperation");

                        for (String fileName : fileNames) {
                            service.fileOperation(project, partnerInstanceInfo, partnerDetails, partnerOperationList, fileName);
                        }

                        generateSuccess = true;
                        logger.info("Successfully created files for New Partner New Operation scenario...");
                        JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);

                    } else {

                        //New Partner Existing Operation
                        //Collecting all the existing entries under operation
                        List<PartnerOperation> partnerOperationEditList = new ArrayList<PartnerOperation>();

                        if (loadExisting) {
                            partnerOperationEditList.addAll(service.getNewPartner(tvList, "edit"));
                        }

                        if (service.isConfigedChanged()) {
                            partnerOperationEditList.addAll(service.getSavedPartnerOperationSet());
                        }

                        System.out.println("New Partner Existing Operation \t!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && loadExisting :\t" + (!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && loadExisting));
                        if (!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && loadExisting) {

                            partnerOperationEditList.forEach(temp -> {
                                System.out.println(temp.toString());
                            });

                            PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                            PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                            //Retriving all the files for NewPartner_NewOperation scenario
                            List<String> fileNames = service.getFiles("NewPartner_ExistingOperation");

                            for (String fileName : fileNames) {
                                service.fileOperation(project, partnerInstanceInfo, partnerDetails, partnerOperationEditList, fileName);
                            }

                            generateSuccess = true;
                            logger.info("Successfully created files for New Partner Existing Operation scenario...");
                            JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {

                            System.out.println("New Partner (New + Existing Operation) \t!partnerOperationEditList.isEmpty() && (!partnerOperationList.isEmpty()) && loadExisting || service.isConfigedChanged() :\t" + (!partnerOperationEditList.isEmpty() && (!partnerOperationList.isEmpty()) && loadExisting || service.isConfigedChanged()));
                            //New Partner New + Existing Operation
                            if (!partnerOperationEditList.isEmpty() && (!partnerOperationList.isEmpty()) && loadExisting || service.isConfigedChanged()) {

                                List<PartnerOperation> partOp = new ArrayList<PartnerOperation>();

                                partOp.addAll(partnerOperationList);

                                List<PartnerOperation> removeList = new ArrayList<PartnerOperation>();
                                removeList.addAll(partOp);

                                //Remove all the contents that are loaded as part of Load Operation
                                for (PartnerOperation temp1 : partOp) {
                                    for (PartnerOperation temp2 : partnerOperationLoadedList) {
                                        if (temp1.getOperationName().equals(temp2.getOperationName())) {
                                            removeList.remove(temp1);
                                        }
                                    }
                                }

                                partOp.removeAll(partOp);
                                partOp.addAll(removeList);

                                partOp.addAll(partnerOperationEditList);

                                PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                                PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                                //Retriving all the files for NewPartner_NewOperation scenario
                                List<String> fileNames = service.getFiles("NewPartner_New_ExistingOperation");

                                for (String fileName : fileNames) {
                                    service.fileOperation(project, partnerInstanceInfo, partnerDetails, partOp, fileName);
                                }

                                generateSuccess = true;
//                              resetAllFlags();
                                logger.info("Successfully created files for New Partner (New + Existing Operation) scenario...");
//                              JOptionPane.showMessageDialog(null, "Successfully created files for New Partner (New + Existing Operation) scenario...", "Success", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Sorry, But there are are no operations that's been entered...\nPlease enter new operation and try again...", "Warning", JOptionPane.WARNING_MESSAGE);
                                System.out.println("Sorry, But there are are no new operations that's been entered...\nPlease enter new operation and try again...");
                            }
                        }
                    }

                }

                if (mode.equalsIgnoreCase("generate") && (option.equalsIgnoreCase("existing"))) {

                    //Existing Partner New Operation
                    //Collecting all the new entries under operation
                    List<PartnerOperation> partnerOperationList = new ArrayList<PartnerOperation>();
                    partnerOperationList.addAll(service.getNewPartner(tvList, "remove"));

                    System.out.println("Existing Partner New Operation \t!partnerOperationList.isEmpty() && load && !loadExisting :\t" + (!partnerOperationList.isEmpty() && load && !loadExisting));

                    if (!partnerOperationList.isEmpty() && load && !loadExisting && !service.isConfigedChanged()) {

                        PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                        PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                        //Retriving all the files for NewPartner_NewOperation scenario
                        List<String> fileNames = service.getFiles("ExistingPartner_NewOperation_4");

                        for (String fileName : fileNames) {
                            service.fileOperation(project, partnerInstanceInfo, partnerDetails, partnerOperationList, fileName);
                        }

                        generateSuccess = true;
                        logger.info("Successfully created files for Existing Partner New Operation scenario...");
                        JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        //Existing Partner Existing Operation
                        //Collecting all the existing entries under operation
                        List<PartnerOperation> partnerOperationEditList = new ArrayList<PartnerOperation>();

                        if (loadExisting) {
                            partnerOperationEditList.addAll(service.getNewPartner(tvList, "edit"));
                        }

                        List<PartnerOperation> removeList = new ArrayList<PartnerOperation>();

                        for (PartnerOperation temp1 : partnerOperationLoadedList) {
                            for (PartnerOperation temp2 : partnerOperationEditList) {
                                if (temp1.getOperationName().equals(temp2.getOperationName())) {
                                    removeList.add(temp2);
                                }
                            }
                        }
                        partnerOperationEditList.removeAll(removeList);

                        //Retrieving all the Existing operation for the selected partner that were edited and saved recently
                        if (service.isConfigedChanged()) {
                            partnerOperationEditList.addAll(service.getSavedPartnerOperationSet());
                        }

                        System.out.println("Existing Partner Existing Operation \t!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && load :\t" + (!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && load));

                        if (!partnerOperationEditList.isEmpty() && (partnerOperationList.isEmpty()) && load) {

                            PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                            PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                            //Retriving all the files for NewPartner_NewOperation scenario
                            List<String> fileNames = service.getFiles("ExistingPartner_ExistingOperation");

                            for (String fileName : fileNames) {
                                service.fileOperation(project, partnerInstanceInfo, partnerDetails, partnerOperationEditList, fileName);
                            }

                            generateSuccess = true;
                            logger.info("Successfully created files for Existing Partner Existing Operation scenario...");
                            JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);

                        } else {

                            //Existing Partner New + Existing Operation
                            List<PartnerOperation> partOp = new ArrayList<PartnerOperation>();

                            partOp.addAll(partnerOperationList);

                            partOp.addAll(partnerOperationEditList);

                            PartnerInstanceInfo partnerInstanceInfo = new PartnerInstanceInfo(mode, cbInstance.getValue(), option, cbPartner.getValue());
                            PartnerDetails partnerDetails = new PartnerDetails(cbGroup.getValue(), tf1partnerDetails.getText(), tf2partnerDetails.getText(), tf3partnerDetails.getText(), tf4partnerDetails.getText(), tf5partnerDetails.getText());

                            //Retriving all the files for ExistingPartner_New_ExistingOperation scenario
                            List<String> fileNames = service.getFiles("ExistingPartner_New_ExistingOperation");

                            System.out.println("Existing Partner (New + Existing) Operation \t!partnerOperationEditList.isEmpty() && (sizeOfExistingTvList - tvList.size() != 0) && (!partnerOperationList.isEmpty()) && load || service.isConfigedChanged() :\t" + (!partnerOperationEditList.isEmpty() && (sizeOfExistingTvList - tvList.size() != 0) && (!partnerOperationList.isEmpty()) && load || service.isConfigedChanged()));

                            if (!partnerOperationEditList.isEmpty() && (sizeOfExistingTvList - tvList.size() != 0) && (!partnerOperationList.isEmpty()) && load || service.isConfigedChanged()) {

                                for (String fileName : fileNames) {
                                    service.fileOperation(project, partnerInstanceInfo, partnerDetails, partOp, fileName);
                                }

                                generateSuccess = true;
                                logger.info("Successfully created files for Existing Partner (New + Existing Operation) scenario...");
                                JOptionPane.showMessageDialog(null, "Successfully created files...", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Sorry, But there are are no new operations that's been entered...\nPlease enter new operation and try again...", "Warning", JOptionPane.WARNING_MESSAGE);
                                System.out.println("Sorry, But there are are no new operations that's been entered...\nPlease enter new operation and try again...");
                            }
                        }
                    }
                }
                inputStream.close();
            } catch (Exception exc) {
                exc.printStackTrace();
                JOptionPane.showMessageDialog(null, exc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (generateSuccess) {
                    System.out.println("Reseting all the flags under finally block");
                    resetAllFlags();
                    generated = true;
                }
            }
        } else {
            System.out.println("Please enter all the fields under Project and then try again...\nSorry for the inconvienience...");
            JOptionPane.showMessageDialog(null, "Please enter all the fields under Project and then try again...\nSorry for the inconvienience...", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Used to Find the difference between the original configuration file and
     * the generated output files. This is performed using the win-merge
     * function. In-order for this function to work first we might need to
     * install the win-merge in the respective system.
     *
     * @param event ActionEvent
     */
    @FXML
    private void viewDiffClicked(ActionEvent event) {
        try {
            if ((cbInstance.getValue() != null) && (!cbInstance.getValue().equalsIgnoreCase(""))) {//Retrieving the path of config and output folders
                Properties prop = new Properties();
                FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
                prop.load(inputStream);

                //Executing win-merge command to do a comparison
                //WinMergeU C:\Folder\File.txt C:\Folder2
                StringBuilder command = new StringBuilder();
                command.append("WinMergeU \"");
                command.append(prop.getProperty("config_file_base_path") + "\\" + cbInstance.getValue());
                command.append("\" \"" + prop.getProperty("output_file_directory") + "\"");
                Runtime rt = Runtime.getRuntime();

                Process pr = rt.exec(command.toString());

                System.out.println("Process String :\t" + pr.toString());

                inputStream.close();
            } else {
                logger.error("Trying to do a comparison without selecting the Instance");
                JOptionPane.showMessageDialog(null, "Sorry for the inconvienience...\nPlease select an Instance and then select view Diff to compare...", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            logger.error(exc.toString());
            JOptionPane.showMessageDialog(null, exc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Used for performing the checkin operation from Git Repository
     *
     * @param event ActionEvent
     */
    @FXML
    private void checkinClicked(ActionEvent event) {
        try {
            if (generated) {
                Properties prop = new Properties();
                FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
                prop.load(inputStream);

                List<File> fileNames = new ArrayList<File>();
                for (File temp : Arrays.asList(new File(prop.getProperty("output_file_directory")).listFiles())) {
                    if (temp.isFile()) {
                        fileNames.add(temp);
                    }
                }
                service.mergeCfgData(cbInstance.getValue(), fileNames);
                if (!fileNames.isEmpty()) {
                    service.checkin();
                } else {
                    JOptionPane.showMessageDialog(null, "There are no files found in the output directory\nPlease check the path you have specified", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Perform some operation before you checkin...", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Reset all the records that are populated in grid. This function resets
     * all the records and starts from the first again. After resetting it again
     * creates a new record with Add button. Clicking on this button also resets
     * the flags.
     *
     * @param event ActionEvent
     */
    @FXML
    private void resetAllClicked(ActionEvent event) {
        //We might have to do a file operation as well in future
        service.resetAll(interfaceOList, methodOList, tvList);

        //Re-Initializing the contents of interface combobox
        interfaceOList.removeAll(interfaceOList);
        interfaceOList.addAll(service.getAllInterfaces());
        //Re-Initializing the contents of method combobox
        methodOList.removeAll(methodOList);
        methodOList.addAll(service.getAllMethods());
        //Re-Initializing the Instance Combobox
        instanceOList.removeAll(instanceOList);
        instanceOList.addAll(service.getAllInstance());
        cbInstance.setItems(instanceOList);
        //Re-Initializing Partner Group Combobox
        partnerDetailsOList.removeAll(partnerDetailsOList);
        partnerDetailsOList.addAll(service.getAllPartnerDetails());
        cbGroup.setItems(partnerDetailsOList);
        //Re-Initializing Partner Group Combobox
        partnerGroupOList.removeAll(partnerGroupOList);
        //partnerGroupOList.addAll(service.getAllPartnerGroup());
        cbPartner.setItems(partnerGroupOList);

        rbNew.setSelected(false);
        rbExisting.setSelected(false);
        cbGroup.setDisable(true);
        tf1partnerDetails.setDisable(true);
        tf2partnerDetails.setDisable(true);
        tf3partnerDetails.setDisable(true);
        tf4partnerDetails.setDisable(true);
        tf5partnerDetails.setText("");
        tf1partnerDetails.setText("");
        tf2partnerDetails.setText("");
        tf3partnerDetails.setText("");
        tf4partnerDetails.setText("");
        tf5partnerDetails.setText("");

        cbPartner.setDisable(true);
        bLoad.setDisable(true);

        for (TableViewDto temp : tvList) {
            temp.getName().setDisable(true);
            temp.getAbbr().setDisable(true);
            temp.getInterfac().setDisable(true);
            temp.getMethod().setDisable(true);
            temp.getSoapActionUri().setDisable(true);
            temp.getPostFix().setDisable(true);
            temp.getReq().setDisable(true);
            temp.getGap().setDisable(true);
            temp.getFacade().setDisable(true);
            temp.getTarget().setDisable(true);
            temp.getTransform().setDisable(true);
            temp.getMask().setDisable(true);
            temp.getAdd().setDisable(true);
        }

        resetAllFlags();
        logger.info("resetting all the information");
    }

    @FXML
    private void rButtonBrowseSelected(ActionEvent event) {
        mode = "browse";
        rbExisting.setSelected(false);
        rbNew.setSelected(false);
        tfName.setDisable(true);
        tfReleaseBranch.setDisable(true);
        taDescription.setDisable(true);
        tfJira.setDisable(true);
        tfAuthorEmail.setDisable(true);
        tfName.setText("");
        tfReleaseBranch.setText("");
        taDescription.setText("");
        tfJira.setText("");
        tfAuthorEmail.setText("");

        rbNew.setDisable(true);
        bGenerate.setDisable(true);
        bViewDiff.setDisable(true);
        bCheckIn.setDisable(true);
        //Re-Initialize Partner Group Combo Box under Partner Detail
        cbGroup.setDisable(true);
        cbGroup.getItems().removeAll(cbGroup.getItems());
        cbGroup.getItems().addAll(service.getAllPartnerDetails());
        //Re-Initializing the Instance Combobox
        instanceOList.removeAll(instanceOList);
        instanceOList.addAll(service.getAllInstance());
        cbInstance.setItems(instanceOList);
        //Initializing Partner Group Combobox
        partnerGroupOList.removeAll(partnerGroupOList);
        //partnerGroupOList.addAll(service.getAllPartnerGroup());
        cbPartner.setItems(partnerGroupOList);

        tf1partnerDetails.setDisable(true);
        tf2partnerDetails.setDisable(true);
        tf3partnerDetails.setDisable(true);
        tf4partnerDetails.setDisable(true);
        tf5partnerDetails.setDisable(true);
        tf1partnerDetails.setText("");
        tf2partnerDetails.setText("");
        tf3partnerDetails.setText("");
        tf4partnerDetails.setText("");
        tf5partnerDetails.setText("");

        cbPartner.setDisable(true);
        bLoad.setDisable(true);
        service.removeAllExceptAdd(tvList);
        tvList.forEach(temp -> {
            temp.getName().setDisable(true);
            temp.getAbbr().setDisable(true);
            temp.getInterfac().setDisable(true);
            temp.getMethod().setDisable(true);
            temp.getSoapActionUri().setDisable(true);
            temp.getPostFix().setDisable(true);
            temp.getReq().setDisable(true);
            temp.getGap().setDisable(true);
            temp.getFacade().setDisable(true);
            temp.getTarget().setDisable(true);
            temp.getTransform().setDisable(true);
            temp.getMask().setDisable(true);
            temp.getAdd().setDisable(true);
        });
    }

    @FXML
    private void rButtonGenerateSelected(ActionEvent event) {
        mode = "generate";
        tableView.setDisable(false);
        tfName.setDisable(false);
        tfReleaseBranch.setDisable(false);
        taDescription.setDisable(false);
        tfJira.setDisable(false);
        tfAuthorEmail.setDisable(false);
        tfName.setText("");
        tfReleaseBranch.setText("");
        taDescription.setText("");
        tfJira.setText("");
        tfAuthorEmail.setText("");
        tf1partnerDetails.setText("");
        tf2partnerDetails.setText("");
        tf3partnerDetails.setText("");
        tf4partnerDetails.setText("");
        tf5partnerDetails.setText("");

        rbNew.setDisable(false);
        rbExisting.setSelected(false);
        bGenerate.setDisable(false);
        bViewDiff.setDisable(false);
        bCheckIn.setDisable(false);
        ToggleGroup generateMode = new ToggleGroup();
        rbNew.setToggleGroup(generateMode);
        rbExisting.setToggleGroup(generateMode);
        //Initializing Partner Group Combobox
        cbGroup.getItems().removeAll(cbGroup.getItems());
        cbGroup.getItems().addAll(service.getAllPartnerDetails());
        //partnerGroupOList.addAll(service.getAllPartnerGroup());
        cbPartner.setDisable(true);
        bLoad.setDisable(true);

        instanceOList.removeAll(instanceOList);
        instanceOList.addAll(service.getAllInstance());
        cbInstance.setItems(instanceOList);

        service.removeAllExceptAdd(tvList);

        tvList.forEach(temp -> {
            temp.getName().setEditable(true);
            temp.getAbbr().setEditable(true);
            temp.getInterfac().setDisable(true);
            temp.getMethod().setDisable(true);
            temp.getSoapActionUri().setEditable(true);
            temp.getPostFix().setEditable(true);
            temp.getReq().setEditable(true);
            temp.getGap().setEditable(true);
            temp.getFacade().setEditable(true);
            temp.getTarget().setEditable(true);
            temp.getTransform().setDisable(true);
            temp.getMask().setDisable(true);
            temp.getAdd().setDisable(true);
        });
    }

    @FXML
    private void rButtonNewSelected(ActionEvent event) {
        option = "new";
        cbGroup.setDisable(false);
        tf1partnerDetails.setDisable(false);
        tf2partnerDetails.setDisable(false);
        tf3partnerDetails.setDisable(false);
        tf4partnerDetails.setDisable(false);
        tf5partnerDetails.setDisable(false);

        tfName.setText("");
        tfReleaseBranch.setText("");
        taDescription.setText("");
        tfJira.setText("");
        tfAuthorEmail.setText("");
        tf1partnerDetails.setText("");
        tf2partnerDetails.setText("");
        tf3partnerDetails.setText("");
        tf4partnerDetails.setText("");
        tf5partnerDetails.setText("");

        //Initializing Partner Group Combobox
        partnerDetailsOList.removeAll(partnerDetailsOList);
        partnerDetailsOList.addAll(service.getAllPartnerDetails());
        cbGroup.setItems(partnerDetailsOList);

        cbPartner.setDisable(true);
        bLoad.setDisable(true);
        service.removeAllExceptAdd(tvList);
    }

    @FXML
    private void rButtonExistingSelected(ActionEvent event) {
        option = "existing";
        cbGroup.setDisable(true);
        tf1partnerDetails.setDisable(true);
        tf2partnerDetails.setDisable(true);
        tf3partnerDetails.setDisable(true);
        tf4partnerDetails.setDisable(true);
        tf5partnerDetails.setDisable(true);
        tf1partnerDetails.setText("");
        tf2partnerDetails.setText("");
        tf3partnerDetails.setText("");
        tf4partnerDetails.setText("");
        tf5partnerDetails.setText("");
        //Re-Initialize Partner Group Combo Box under Partner Detail
        cbGroup.setDisable(true);
        //Initializing Partner Group Combobox
        cbGroup.getItems().removeAll(cbGroup.getItems());
        cbGroup.getItems().addAll(service.getAllPartnerDetails());
        if (cbInstance.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Please select an instance and then continue\nHint :\tYou can find this under scope", "Warning", JOptionPane.WARNING_MESSAGE);
            rbExisting.setSelected(false);
        } else {
            //Initializing Partner Group Combobox
            partnerGroupOList.removeAll(partnerGroupOList);
            partnerGroupOList.addAll(service.getAllPartnerGroup(cbInstance.getValue()));
            cbPartner.setItems(partnerGroupOList);
            cbPartner.setDisable(false);
            bLoad.setDisable(false);
        }
    }

    @FXML
    private void rButtonRemoveSelected(ActionEvent event) {
    }

    @FXML
    private void cbPartnerSelected(ActionEvent event) {
    }

    /**
     * Used to read all the operation information for the selected Instance and
     * display them. From the range of operation we can select the required
     * operation and them save them. Once saved the operation will be mapped to
     * selected partner
     *
     * @param event ActionEvent
     */
    @FXML
    private void loadExistingClicked(ActionEvent event) {
        sizeOfExistingTvList = tvList.size();
        Parent operationStageParent;
        if (cbInstance.getValue() == null) {
            logger.warn("Please Select an instance");
            JOptionPane.showMessageDialog(null, "Please Select an instance", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Properties tempProp = new Properties();
                FileOutputStream outputStream = new FileOutputStream(new File(file.getAbsoluteFile() + "\\TempApplication.properties"));
                System.out.println("Mode :\t" + mode + "\nOption :\t" + option + "\nInstance :\t" + cbInstance.getValue());
                tempProp.setProperty("Mode", mode);
                tempProp.setProperty("Option", option);
                tempProp.setProperty("Instance", cbInstance.getValue());
                tempProp.save(outputStream, "save");
                outputStream.close();
                System.out.println("Temp File Instance write :\t" + cbInstance.getValue());

                if (!StageHelper.getStages().contains(operationStage)) {
                    FXMLLoader operationStageLoader = new FXMLLoader(getClass().getResource("/fxml/OperationsFXML.fxml"));
                    operationStageParent = (Parent) operationStageLoader.load();
                    OperationsController operationController = operationStageLoader.getController();

                    operationController.initializeObservableList(interfaceOList, methodOList, tvList, mode, cbInstance.getValue());
                    operationStage.setResizable(false);
                    operationStage.setTitle("Operations");
                    operationStage.setScene(new Scene(operationStageParent));
                    operationStage.show();
                    loadExisting = true;

                } else {
                    logger.warn("The Window is already open\nYou can not open multiple instance of this window...Sorry!!!");
                    JOptionPane.showMessageDialog(null, "The Window is already open\nYou can not open multiple instance of this window...Sorry!!!", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception exc) {
                logger.error(exc.toString());
                System.out.println("Caught in Exception inside Class :\tApplication Controller\tMethod :\tloadExistingClicked\nAnd the exception is :\t" + exc);
                JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tApplication Controller\tMethod :\tloadExistingClicked\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
                exc.printStackTrace();
            }
        }
    }

    @FXML
    private void decommissionedCBClicked(ActionEvent event) {
    }

    /**
     * Used to load the existing operation for the selected partner
     *
     * @param event ActionEvent
     */
    @FXML
    private void loadClicked(ActionEvent event) {
        if (cbPartner.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Please Select a Partner and then proceed...", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            Project project = new Project(tfName.getText(), tfReleaseBranch.getText(), taDescription.getText(), tfJira.getText(), tfAuthorEmail.getText());

            //Get all the records for the given instance and partner from the partner data.cfg
            PartnerDetails partnerDetails = service.getPartnerDetails(cbInstance.getValue(), cbPartner.getValue());
            cbGroup.setValue(partnerDetails.getGroup());
            tf1partnerDetails.setText(partnerDetails.getName());
            tf2partnerDetails.setText(partnerDetails.getPhone());
            tf3partnerDetails.setText(partnerDetails.getEmailID());
            tf4partnerDetails.setText(partnerDetails.getCaIssuer());
            tf5partnerDetails.setText(partnerDetails.getSerialNumber());
            Set<PartnerOperation> partnerOperationSet = service.exPartNewOp(cbInstance.getValue(), cbPartner.getValue(), tvList, interfaceOList, methodOList);

            service.removeAllExceptAdd(tvList);
            service.createNewEditRow(partnerOperationSet, tvList, interfaceOList, methodOList, partnerOperationLoadedList);
            service.swapAdd(tvList);
            //The rows that are displayed in the table should be with edit button on them.
            System.out.println("Load Clicked");
            load = true;
            if (rbBrowse.isSelected()) {
                tvList.forEach(temp -> {
                    temp.getName().setEditable(false);
                    temp.getAbbr().setEditable(false);
                    temp.getInterfac().setDisable(true);
                    temp.getMethod().setDisable(true);
                    temp.getSoapActionUri().setEditable(false);
                    temp.getPostFix().setEditable(false);
                    temp.getReq().setEditable(false);
                    temp.getGap().setEditable(false);
                    temp.getFacade().setEditable(false);
                    temp.getTarget().setEditable(false);
                    temp.getTransform().setDisable(true);
                    if (temp.getInterfac().getValue().equalsIgnoreCase("soap")) {
                        temp.getTransform().setDisable(false);
                        temp.getMask().setDisable(false);
                    } else {
                        temp.getTransform().setDisable(true);
                        temp.getMask().setDisable(true);
                        if (temp.getTransform().isSelected()) {
                            temp.getTransform().selectedProperty().setValue(false);
                        }
                        if (temp.getMask().isSelected()) {
                            temp.getMask().selectedProperty().setValue(false);
                        }
                    }
                });

                // highlight the table rows depending upon whether we expect to get paid.
                int i = 0;
                for (Node n : tableView.lookupAll("TableRow")) {
                    if (n instanceof TableRow) {
                        TableRow row = (TableRow) n;
                        if (tableView.getItems().get(i).getAdd().getText().equalsIgnoreCase("edit")) {

                            row.getStyleClass().add("willPayRow");
                        } else {
                            row.getStyleClass().add("wontPayRow");
                        }
                        i++;
                        if (i == tableView.getItems().size()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void instanceCBClicked(ActionEvent event) {
        if (cbInstance.getValue() != null) {
            rbNew.setSelected(false);
            rbExisting.setSelected(false);
            cbPartner.setDisable(true);
            cbGroup.setDisable(true);
            tf1partnerDetails.setDisable(true);
            tf2partnerDetails.setDisable(true);
            tf3partnerDetails.setDisable(true);
            tf4partnerDetails.setDisable(true);
            tf5partnerDetails.setDisable(true);
            tf1partnerDetails.setText("");
            tf2partnerDetails.setText("");
            tf3partnerDetails.setText("");
            tf4partnerDetails.setText("");
            tf5partnerDetails.setText("");
            service.resetAll(interfaceOList, methodOList, tvList);

            generated = false;

            if (rbBrowse.isSelected()) {
                tvList.forEach(temp -> {
                    temp.getName().setEditable(false);
                    temp.getAbbr().setEditable(false);
                    temp.getInterfac().setDisable(true);
                    temp.getMethod().setDisable(true);
                    temp.getSoapActionUri().setEditable(false);
                    temp.getPostFix().setEditable(false);
                    temp.getReq().setEditable(false);
                    temp.getGap().setEditable(false);
                    temp.getFacade().setEditable(false);
                    temp.getTarget().setEditable(false);
                    temp.getTransform().setDisable(true);
                    temp.getMask().setDisable(true);
                    temp.getAdd().setDisable(true);
                });
            }
        }
    }

    @FXML
    private void groupCBClicked(ActionEvent event) {
    }

    /**
     * Resets all the flags only when data's are successfully written into the
     * file.
     */
    public void resetAllFlags() {
        try {
            //resetting all the flags
            sizeOfExistingTvList = tvList.size();
            loadExisting = false;
            load = false;
            service.setConfigedChanged(false);
            service.getSavedPartnerOperationSet().removeAll(service.getSavedPartnerOperationSet());
            if (rbBrowse.isSelected()) {
                mode = "browse";
            }
            if (rbGenerate.isSelected()) {
                mode = "generate";
                ToggleGroup generateMode = new ToggleGroup();
                rbNew.setToggleGroup(generateMode);
                rbExisting.setToggleGroup(generateMode);
            }
            if (rbNew.isSelected()) {
                option = "new";
            }
            if (rbExisting.isSelected()) {
                option = "existing";
            }

        } catch (Exception exc) {
            logger.error(exc.toString());
            System.out.println("Caught in Exception inside Class :\tApplication Controller\tMethod :\tresetAllFlags\nAnd the exception is :\t" + exc);
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tApplication Controller\tMethod :\tresetAllFlags\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();
        }
    }

    /**
     * Loads all the images, Like logo's icons that are used in buttons. This
     * method is invoked at the time of initialization
     */
    public void loadAllImages() {
        File file = new File("");
        System.out.println("File Path :\t" + file.getAbsolutePath());
//      Image companyLogo = new Image("file:/" + file.getAbsolutePath() + "\\VF-Logo.png");
        Image companyLogo = new Image("/images/Sandhata-Logo.png");
        Image loadExistingLogo = new Image("/images/search_icon.png");
        Image resetLogo = new Image("/images/reset_icon.png");
        Image checkOutLogo = new Image("/images/CheckOut_icon.png");
        Image checkInLogo = new Image("/images/CheckIn_icon.png");
        Image generateLogo = new Image("/images/Generate_icon.png");
        Image viewDiffLogo = new Image("/images/ViewDiff_icon.png");
        Image loadLogo = new Image("/images/Load_icon.png");
        bLoadExisting.setGraphic(new ImageView(loadExistingLogo));
        bLoad.setGraphic(new ImageView(loadLogo));
        bCheckout.setGraphic(new ImageView(checkOutLogo));
        bCheckIn.setGraphic(new ImageView(checkInLogo));
        bGenerate.setGraphic(new ImageView(generateLogo));
        bViewDiff.setGraphic(new ImageView(viewDiffLogo));
        bResetAll.setGraphic(new ImageView(resetLogo));

        //(ImageView.new(searchLogo("src/code/media/logo.png")));
        iCompanyLogo.setImage(companyLogo);
    }

    @FXML
    private void menubarUploadLicenseClicked(ActionEvent event) throws IOException {
        licenseValidationService.uploadLicenseScreen(uploadLicenseStage);
//        initializeApplicationController();
        System.out.println("License Validation Value :: \t" + licenseValidation);

    }

    public void initializeApplicationController() throws IOException {
        licenseValidation = licenseValidationService.validateLicense();
        if (licenseValidation) {
            anchorPane1.setVisible(GCATConstants.TRUE);
            borderPane2.setVisible(GCATConstants.TRUE);
        } else {
            anchorPane1.setVisible(GCATConstants.FALSE);
            borderPane2.setVisible(GCATConstants.FALSE);
        }
    }

}
