/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service.impl;

import com.sandhata.gcat.application.config.Log4Config;
import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.exception.GCATExceptions;
import com.sandhata.gcat.application.exception.MyExceptions;
import com.sandhata.gcat.application.main.GcatApplication;
import com.sandhata.gcat.application.service.LicenseValidationService;
import com.sandhata.license.ManageLicense;
import com.sandhata.license.validate.ValidateLicenseCodeMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import org.eclipse.jgit.util.StringUtils;

/**
 *
 * @author s_m_p
 */
public class LicenseValidationServiceImpl implements LicenseValidationService {

    boolean valLicense = false;

    File lofFile = new File("");

    private org.apache.log4j.Logger logger = (new Log4Config()).getLogger(lofFile.getAbsolutePath() + GCATConstants.DOUBLE_SLASH + GCATConstants.GCAT_LOG_FILE_NAME, GCATConstants.LICENSE_VALIDATION_SERVICE_IMPL);

    @Override
    public boolean validateLicense() throws IOException {

        //1. check for license file path - present or not
        Map<String, String> allproperties = readAllProperties();

        try {
            if (allproperties.containsKey(GCATConstants.LICENSE_FILE_PATH)) {

                //2. If present then validate if the license is valid or not valid
                ManageLicense manageLicense = new ManageLicense();
                int op = manageLicense.validateLicense(GCATConstants.GCAT, allproperties.get(GCATConstants.LICENSE_FILE_PATH));
//                    manageLicense.validateLicense(allproperties.get(GCATConstants.LICENSE_FILE_PATH));

                System.out.println("OUtput of License Manager Jar :: \t" + op);

                String msg = ValidateLicenseCodeMapper.getValidationMessage(op);

                if (op == ValidateLicenseCodeMapper.VALID.getValidationCode()) {
//            if (op.contains(GCATConstants.VALID_LICENSE)) {

                    //Throw a success message
                    JOptionPane.showMessageDialog(null, GCATConstants.VALID_LICENSE, GCATConstants.SUCCESS, JOptionPane.INFORMATION_MESSAGE);

                    valLicense = GCATConstants.TRUE;

                } else {

                    //Display license upload license screen
                    valLicense = GCATConstants.FALSE;
                    JOptionPane.showMessageDialog(null, GCATExceptions.INVALID_LICENSE_FILE.getExceptionDescription(), GCATConstants.ERROR, JOptionPane.ERROR_MESSAGE);

                }
            } else {
                //Display license upload license screen
                valLicense = GCATConstants.FALSE;
                JOptionPane.showMessageDialog(null, GCATExceptions.MISSING_LICENSE_FILE_PATH.getExceptionDescription(), GCATConstants.ERROR, JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception exc) {
            logger.error(exc);
        }
        return valLicense;
    }

    @Override
    public Map<String, String> readAllProperties() {
        File file = new File("");
        Map<String, String> allProperties = new HashMap<>();
        try {
            FileInputStream inputStream = new FileInputStream(file.getAbsolutePath() + "\\Application.properties");
            Properties inputProp = new Properties();
            inputProp.load(inputStream);
            int count = 0;
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.OUTPUT_FILE_DIRECTORY))) {
                allProperties.put(GCATConstants.OUTPUT_FILE_DIRECTORY, inputProp.getProperty(GCATConstants.OUTPUT_FILE_DIRECTORY));
                count++;
            } else {
                allProperties.put(GCATConstants.OUTPUT_FILE_DIRECTORY, file.getAbsolutePath());
                count++;
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.GIT_REPO_PATH))) {
                allProperties.put(GCATConstants.GIT_REPO_PATH, inputProp.getProperty(GCATConstants.GIT_REPO_PATH));
                count++;
            } else {
                allProperties.put(GCATConstants.GIT_REPO_PATH, file.getAbsolutePath());
                count++;
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.CONFIG_FILE_BASE_PATH))) {
                allProperties.put(GCATConstants.CONFIG_FILE_BASE_PATH, inputProp.getProperty(GCATConstants.CONFIG_FILE_BASE_PATH));
                count++;
            } else {
                allProperties.put(GCATConstants.CONFIG_FILE_BASE_PATH, file.getAbsolutePath());
                count++;
            }

            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.GIT_BASH_PATH))) {
                allProperties.put(GCATConstants.GIT_BASH_PATH, inputProp.getProperty(GCATConstants.GIT_BASH_PATH));
                count++;
            } else {
                allProperties.put(GCATConstants.GIT_BASH_PATH, file.getAbsolutePath());
                count++;
            }

            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.LICENSE_FILE_PATH))) {
                allProperties.put(GCATConstants.LICENSE_FILE_PATH, inputProp.getProperty(GCATConstants.LICENSE_FILE_PATH));
                count++;
            }

            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.GIT_USER_NAME))) {
                allProperties.put(GCATConstants.GIT_USER_NAME, inputProp.getProperty(GCATConstants.GIT_USER_NAME));
                count++;
            }

            if (!StringUtils.isEmptyOrNull(inputProp.getProperty(GCATConstants.GIT_USER_PASSWORD))) {
                allProperties.put(GCATConstants.GIT_USER_PASSWORD, inputProp.getProperty(GCATConstants.GIT_USER_PASSWORD));
                count++;
            }

            allProperties.put(GCATConstants.PROPERTIES_COUNT, "" + count);

            inputStream.close();

            writeAllProperties(allProperties);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allProperties;
    }

    @Override
    public void writeAllProperties(Map<String, String> allProperties) {
        File file = new File("");
        try {
            FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath() + "\\Application.properties");
            Properties prop = new Properties();

            for (String key : allProperties.keySet()) {
                switch (key) {
                    case GCATConstants.OUTPUT_FILE_DIRECTORY:
                        prop.setProperty("output_file_directory", allProperties.get(GCATConstants.OUTPUT_FILE_DIRECTORY));
                        break;

                    case GCATConstants.GIT_REPO_PATH:
                        prop.setProperty("git_repo_path", allProperties.get(GCATConstants.GIT_REPO_PATH));
                        break;

                    case GCATConstants.GIT_BASH_PATH:
                        prop.setProperty("git_bash_path", allProperties.get(GCATConstants.GIT_BASH_PATH));
                        break;

                    case GCATConstants.CONFIG_FILE_BASE_PATH:
                        prop.setProperty("config_file_base_path", allProperties.get(GCATConstants.CONFIG_FILE_BASE_PATH));
                        break;

                    case GCATConstants.LICENSE_FILE_PATH:
                        prop.setProperty("license_file_path", allProperties.get(GCATConstants.LICENSE_FILE_PATH));
                        break;
                    case GCATConstants.GIT_USER_NAME:
                        prop.setProperty("git_user_name", allProperties.get(GCATConstants.GIT_USER_NAME));
                        break;
                    case GCATConstants.GIT_USER_PASSWORD:
                        prop.setProperty("git_user_password", allProperties.get(GCATConstants.GIT_USER_PASSWORD));
                        break;
                }
            }
            prop.store(outputStream, "Initializing Property File");
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LicenseValidationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void uploadLicenseScreen(Stage stage) throws IOException {

        File file = new File("");
        boolean valLicense = false;
        try {
            System.out.println("LicenseValidation File Path :: \t" + getClass().getResource("/fxml/LicenseValidationFXML.fxml"));

            FXMLLoader uploadLicenseStageLoader = new FXMLLoader(getClass().getResource("/fxml/LicenseValidationFXML.fxml"));
            Parent uploadLicenseStageParent = (Parent) uploadLicenseStageLoader.load();

            stage.setResizable(false);
            stage.setTitle("G C A T License Upload");
            stage.setScene(new Scene(uploadLicenseStageParent));
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(GcatApplication.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

}
