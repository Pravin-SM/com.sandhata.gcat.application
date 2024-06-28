/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.controller;

import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.exception.MyExceptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import com.sandhata.gcat.application.config.Log4Config;
import org.eclipse.jgit.util.StringUtils;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField tfConfigFilePath;
    @FXML
    private Button bConfigFileChoose;
    @FXML
    private TextField tfGitRepoPath;
    @FXML
    private TextField tfOutputFilePath;
    @FXML
    private Button bOutputFileChoose;
    @FXML
    private TextField tfGitBashPath;
    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;

    String gitUserName = null;
    String gitPassword = null;
    String licenseFilePath = null;

    private Properties prop = new Properties();
//    FileHandler fh;
    private File file = new File("");
    Logger logger = (new Log4Config()).getLogger(file.getAbsolutePath() + "\\gcatLogs.log", "SettingsController");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Basic configurator to log debug messages to the console
//        BasicConfigurator.configure();
        boolean exception = false;
        String content = "";
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            tfConfigFilePath.setText(prop.getProperty("config_file_base_path"));
            tfGitRepoPath.setText(prop.getProperty("git_repo_path"));
            tfOutputFilePath.setText(prop.getProperty("output_file_directory"));
            tfGitBashPath.setText(prop.getProperty("git_bash_path"));
            if (!StringUtils.isEmptyOrNull(prop.getProperty("git_user_name"))) {
                gitUserName = prop.getProperty("git_user_name");
            }
            if (!StringUtils.isEmptyOrNull(prop.getProperty("git_user_password"))) {
                gitPassword = prop.getProperty("git_user_password");
            }
            if (!StringUtils.isEmptyOrNull(prop.getProperty("license_file_path"))) {
                licenseFilePath = prop.getProperty("license_file_path");
            }
            logger.info("Config File Base Path :\t" + tfConfigFilePath.getText());
            inputStream.close();
        } catch (Exception exc) {
            logger.error(exc.toString());
        }
    }

    @FXML
    private void configFileChooseClicked(ActionEvent event) {
        try {
            DirectoryChooser dc = new DirectoryChooser();

            File selectedDirectory = dc.showDialog(null);
            if (selectedDirectory == null) {
                System.out.println("No directory selected");
            } else {
                tfConfigFilePath.setText(selectedDirectory.getAbsolutePath());
            }
        } catch (Exception exc) {
            ExceptionModel eModel = new ExceptionModel();
            eModel.setClassName("SettingsController");
            eModel.setMethodName("configFileChooseClicked");
            eModel.setExceptionDescription(exc.toString());
            eModel.setDate(new Date());
        }
    }

    @FXML
    private void outputFileChooseClicked(ActionEvent event) {
        try {
            DirectoryChooser dc = new DirectoryChooser();
            File selectedDirectory = dc.showDialog(null);
            if (selectedDirectory == null) {
                System.out.println("No directory selected");
            } else {
                tfOutputFilePath.setText(selectedDirectory.getAbsolutePath());
            }
        } catch (Exception exc) {
            ExceptionModel eModel = new ExceptionModel();
            eModel.setClassName("SettingsController");
            eModel.setMethodName("configFileChooseClicked");
            eModel.setExceptionDescription(exc.toString());
            eModel.setDate(new Date());
        }
    }

    @FXML
    private void saveClicked(ActionEvent event) {
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.setProperty("config_file_base_path", tfConfigFilePath.getText());
            prop.setProperty("git_repo_path", tfGitRepoPath.getText());
            prop.setProperty("output_file_directory", tfOutputFilePath.getText());
            prop.setProperty("git_bash_path", tfGitBashPath.getText());
            prop.setProperty("git_user_name", gitUserName);
            prop.setProperty("git_user_password", gitPassword);
            prop.setProperty("license_file_path", licenseFilePath);
            prop.save(outputStream, "save");
            outputStream.close();

            //Closing the windows after saving the contents
            Stage stage = (Stage) bSave.getScene().getWindow();
            stage.close();
        } catch (Exception exc) {
            System.out.println("Caught in Exception inside Class :\tSettingsController Method :\tsaveClicked and the exception is :\t" + exc);
        }
    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        Stage stage = (Stage) bCancel.getScene().getWindow();
        stage.close();
    }

}
