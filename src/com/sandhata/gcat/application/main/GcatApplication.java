package com.sandhata.gcat.application.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sandhata.gcat.application.service.LicenseValidationService;
import com.sandhata.gcat.application.service.impl.LicenseValidationServiceImpl;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author admin
 */
public class GcatApplication extends Application {
    
    private LicenseValidationService licenseValidationService=new LicenseValidationServiceImpl();

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {

        File file = new File("");
       
        Map<String, String> allproperties=licenseValidationService.readAllProperties();
        licenseValidationService.writeAllProperties(allproperties);
        initializeScreen(stage);
    }

    public void initializeScreen(Stage stage) {
        File logo = new File("");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/ApplicationFXML.fxml"));

            //Code to dynamically change the screen size
//            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//            int width = gd.getDisplayMode().getWidth();
//            int height = gd.getDisplayMode().getHeight();
//            Scene scene = new Scene(root,width,height);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/Main.css");
            stage.setScene(scene);

//          stage.setResizable(false);
            stage.getIcons().add(new Image("file:/" + logo.getAbsolutePath() + "\\GatewayConfig.ico"));
            stage.setTitle("G C A T");

            stage.show();
            //Make Sure the stage is terminating properly
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(GcatApplication.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
