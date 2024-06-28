/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.dto;

import java.util.Comparator;

/**
 *
 * @author admin
 */
public class DataComparator implements Comparator<TableViewDto> {

//    @Override
//    public int compare(User u1, User u2) {
//        return u1.getAge().compareTo(u2.getAge());
//    }
    @Override
    public int compare(TableViewDto o1, TableViewDto o2) {
        return (new Integer(o1.getSno()).compareTo(new Integer(o2.getSno())));
    }
}
