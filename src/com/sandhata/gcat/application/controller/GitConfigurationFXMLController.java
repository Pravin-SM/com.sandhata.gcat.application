/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.controller;

import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.service.GitService;
import com.sandhata.gcat.application.service.impl.GitServiceImpl;
import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.exception.GCATExceptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.eclipse.jgit.util.StringUtils;

/**
 * FXML Controller class
 *
 * @author s_m_p
 */
public class GitConfigurationFXMLController implements Initializable {

    @FXML
    private Button bSaveGitCredentials;
    @FXML
    private TextField tfGitUserName;
    @FXML
    private TextField tfgitPassword;

    private GitService gitService;

    private File file = new File("");

    private Properties inputProp = new Properties();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            inputProp.load(inputStream);
            tfGitUserName.setText(inputProp.getProperty("git_user_name"));
            tfgitPassword.setText(inputProp.getProperty("git_user_password"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GitConfigurationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GitConfigurationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        gitService = new GitServiceImpl();
    }

    @FXML
    private void saveGitCredentialsClicked(ActionEvent event) {
        boolean notEmpty = true;
        String userName = null;
        String password = null;
        if (!StringUtils.isEmptyOrNull(tfGitUserName.getText())) {
            userName = tfGitUserName.getText();
        } else {
            notEmpty = false;
        }
        if (!StringUtils.isEmptyOrNull(tfgitPassword.getText())) {
            password = tfgitPassword.getText();
        } else {
            notEmpty = false;
        }
        if (!notEmpty) {
            throw new ExceptionModel(GCATConstants.GIT_CONFIGURATION_FXML_CONTROLLER, GCATConstants.SAVE_GIT_CREDENTIALS_CLICKED, GCATExceptions.MISSING_GIT_CREDENTIALS);
        } else {
            gitService.updateGitCredentials(userName, password);
        }

        //Closing the windows after saving the contents
        Stage stage = (Stage) bSaveGitCredentials.getScene().getWindow();
        stage.close();
    }

}
