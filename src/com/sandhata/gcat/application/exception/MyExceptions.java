/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.exception;

import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class MyExceptions extends RuntimeException {

    public MyExceptions(String message){
        super(message);
    }
    
}
