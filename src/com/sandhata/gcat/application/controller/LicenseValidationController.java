/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.controller;

import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.exception.GCATExceptions;
import com.sandhata.gcat.application.service.LicenseValidationService;
import com.sandhata.gcat.application.service.impl.LicenseValidationServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.eclipse.jgit.util.StringUtils;

/**
 * FXML Controller class
 *
 * @author s_m_p
 */
public class LicenseValidationController implements Initializable {

    @FXML
    private AnchorPane validateLicenseAnchorPane;
    @FXML
    private TextField tfvalidateLicense;
    @FXML
    private Button bvalidateLicense;
    @FXML
    private Button bUpload;

    private LicenseValidationService licenseValidationService = new LicenseValidationServiceImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void browseLicenseClicked(ActionEvent event) {

        try {
            System.out.println("Class : LicenseValidationController :: Method : validateLicenseClicked");
            FileChooser fc = new FileChooser();
            File selectedFile = fc.showOpenDialog(null);
            if (selectedFile == null) {
                System.out.println("No directory selected");
            } else {
                tfvalidateLicense.setText(selectedFile.getAbsolutePath());
            }
        } catch (Exception exc) {
            ExceptionModel eModel = new ExceptionModel();
            eModel.setClassName("LicenseValidationController");
            eModel.setMethodName("validateLicenseClicked");
            eModel.setExceptionDescription(exc.toString());
            eModel.setDate(new Date());
            System.out.println(eModel);
            throw eModel;
        }

    }

    @FXML
    private void uploadButtonClicked(ActionEvent event) throws FileNotFoundException, IOException {

        Map<String, String> allProperties = licenseValidationService.readAllProperties();

        allProperties.put(GCATConstants.LICENSE_FILE_PATH, tfvalidateLicense.getText());
        licenseValidationService.writeAllProperties(allProperties);

        allProperties.keySet().forEach(key -> {
            System.out.println("Property Name : " + key + " :: Value : " + allProperties.get(key) + "\n");
        });

        JOptionPane.showMessageDialog(null, GCATConstants.LICENSE_UPLOADED_SUCCESSFULLY, GCATConstants.SUCCESS, JOptionPane.INFORMATION_MESSAGE);
    }

}
