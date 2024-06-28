/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service;

/**
 *
 * @author admin
 */
public interface GitService {
    
    public void checkIn();
    public void checkOut();
    public void updateGitCredentials(String userName, String password);
}
