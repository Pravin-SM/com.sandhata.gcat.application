/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.constants;

/**
 *
 * @author s_m_p
 */
public class GCATConstants {

    //Special characters
    public static final String TAB_SPACE = "  ";
    public static final String COMMA = ",";
    public static final String EQUALS = "=";
    public static final String DOUBLE_SLASH = "\\";

    public static final String FOUR = "4";
    public static final String FIVE = "5";

    public static final String GCAT="GCAT";
    public static final String LICENSE_UPLOADED_SUCCESSFULLY = "License has been uploaded successfully...";
    public static final String VALID_LICENSE = "License is Valid";
    public static final String EXPIRED_LICENSE = "License Has Expired";
    public static final String PROPERTIES_COUNT = "Prop_Count";

    //Properties
    public static final String LICENSE_FILE_PATH = "license_file_path";
    public static final String OUTPUT_FILE_DIRECTORY = "output_file_directory";
    public static final String GIT_REPO_PATH = "git_repo_path";
    public static final String CONFIG_FILE_BASE_PATH = "config_file_base_path";
    public static final String GIT_BASH_PATH = "git_bash_path";
    public static final String GIT_USER_NAME="git_user_name";
    public static final String GIT_USER_PASSWORD="git_user_password";

    //Class
    public static final String GCAT_APPLICATION = "GcatApplication";
    public static final String APPLICATION_CONTROLLER = "ApplicationController";
    public static final String GIT_CONFIGURATION_FXML_CONTROLLER="GitConfigurationFXMLController";
    public static final String FILE_OPERATION_SERVICE_IMPL = "FileOperationServiceImpl";
    public static final String GIT_SERVICE_IMPL="GitServiceImpl";
    public static final String LICENSE_VALIDATION_SERVICE_IMPL = "LicenseValidationServiceImpl";
    

    //Method
    public static final String INITIALIZE = "initialize";
    public static final String VALIDATE_LICENSE = "validateLicense";
    public static final String READ_ALL_PROPERTIES = "readAllProperties";
    public static final String WRITE_ALL_PROPERTIES = "writeAllProperties";
    public static final String UPLOAD_LICENSE_SCREEN = "uploadLicenseScreen";
    public static final String SAVE_GIT_CREDENTIALS_CLICKED="saveGitCredentialsClicked";
    public static final String UPDATE_GIT_CREDENTIALS="updateGitCredentials";

    //Logging
    public static final String ERROR_OUT = "Class :: {} Method :: {} Exception : {}";
    public static final String OUT = "Class :: {} Method :: {}  \n{}";
    public static final String GCAT_LOG_FILE_NAME = "gcatLogs.log";

    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String WARNING = "Warning";
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    //Exceptions
    public static final String INPUT_FILE_MISSING = "File not found in the given path";
    public static final String MISSING_LICENSE_FILE_PATH = "Missing License file path in configuration";
    public static final String MISSING_LICENSE_FILE = "Missing license file in specified path";
    public static final String INVALID_LICENSE_FILE = "The uploaded license is invalid. Please try again with a valid licence file";
    public static final String MISSING_MANDATORY_FILE_PATHS = "One or more of the madatory file path is missing...";

}
