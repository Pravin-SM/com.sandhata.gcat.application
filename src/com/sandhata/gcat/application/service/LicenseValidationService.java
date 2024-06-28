/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javafx.stage.Stage;

/**
 *
 * @author s_m_p
 */
public interface LicenseValidationService {

    public boolean validateLicense() throws IOException;

    public Map<String, String> readAllProperties();

    public void writeAllProperties(Map<String, String> allPropertiesMap);

    public void uploadLicenseScreen(Stage stage) throws IOException;

}
