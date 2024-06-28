/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.config;

/**
 *
 * @author admin
 */
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log4Config {

    public Logger getLogger(String filePath, String className) {
        // creates pattern layout
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
        layout.setConversionPattern(conversionPattern);
        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();
        // creates file appender
        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile(filePath);
        fileAppender.setLayout(layout);
        fileAppender.activateOptions();
        // configures the root logger
//        Logger rootLogger = Logger.getRootLogger();
        Logger rootLogger = Logger.getLogger(className);
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);

        return (rootLogger);
    }

}
