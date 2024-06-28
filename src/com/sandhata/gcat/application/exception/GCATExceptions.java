/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.exception;

import java.util.Date;

/**
 *
 * @author s_m_p
 */
public enum GCATExceptions {

    INPUT_FILE_MISSING("File not found in the given path"),
    MISSING_LICENSE_FILE_PATH("Missing License file path in configuration"),
    MISSING_LICENSE_FILE("Missing license file in specified path"),
    INVALID_LICENSE_FILE("The uploaded license is invalid. Please try again with a valid licence file"),
    MISSING_MANDATORY_FILE_PATHS("One or more of the madatory file path is missing..."),
    MISSING_GIT_CREDENTIALS("Invalid git credentials/Missing git credentials...");

    private String exceptionDescription;

    private GCATExceptions(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    private GCATExceptions() {
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }
}
