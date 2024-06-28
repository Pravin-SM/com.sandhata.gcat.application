/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.exception;

import java.util.Date;

/**
 *
 * @author admin
 */
public class ExceptionModel extends RuntimeException{
    
    String className;
    String methodName;
    String exceptionDescription;
    Date date;

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public ExceptionModel(){
        
    }
    
    public ExceptionModel(String className, String methodName, GCATExceptions gcatException){
        this.className=className;
        this.exceptionDescription=gcatException.getExceptionDescription();
        this.methodName=methodName;
        date=new Date();
    }

    @Override
    public String toString() {
        return "ExceptionModel{" + "className=" + className + ", methodName=" + methodName + ", exceptionDescription=" + exceptionDescription + ", date=" + date + '}';
    }
    
}
