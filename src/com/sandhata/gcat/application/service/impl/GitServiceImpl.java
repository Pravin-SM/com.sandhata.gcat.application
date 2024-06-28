/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service.impl;

import com.sandhata.gcat.application.config.Log4Config;
import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.exception.GCATExceptions;
import com.sandhata.gcat.application.service.FileOperationService;
import com.sandhata.gcat.application.service.GitService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import static org.eclipse.jgit.api.Git.init;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.StringUtils;

/**
 *
 * @author admin
 */
public class GitServiceImpl implements GitService {

    private FileOperationService fileOperationService = new FileOperationServiceImpl();
    private File file = new File("");
    private Logger gitLogger = (new Log4Config()).getLogger(file.getAbsolutePath() + "\\gitLogs.log", "GitServiceImpl");
    private Properties prop = new Properties();
    private Properties inputProp = new Properties();

    //Used to perform Check-In operation
    @Override
    public void checkIn() {
        try {
            Properties prop = new Properties();
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);

            String userName=prop.getProperty("git_user_name");
            String password=prop.getProperty("git_user_password");
            String repoUrl = prop.getProperty("git_repo_path");
            String cloneDirectoryPath = prop.getProperty("git_bash_path"); // Ex.in windows c:\\gitProjects\SpringBootMongoDbCRUD\

            init();

            Git git = Git.init().setDirectory(new File(cloneDirectoryPath)).call();
            Repository repo = git.getRepository();
            repo.getConfig().setString("remote", "origin", "url", repoUrl);
            repo.getConfig().save();
            git.add().addFilepattern(".").call();
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("DD-mm-yyyy'T'HH:MM:ss");
            git.commit().setMessage("testing").setAuthor(userName, sdf.format(date)).call();
            git.push().setRemote("origin").setPushAll().setForce(true).setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, password)).call();
            repo.close();

            inputStream.close();
            System.out.println("Successfully pushed the code to git");
            JOptionPane.showMessageDialog(null, "Successfully pushed the files to git repo...", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            System.out.println("Exception occurred while checkin git repo");
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, exc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Used to perform Check-out operation
    @Override
    public void checkOut() {
        try {
            Properties prop = new Properties();
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);

            System.out.println("Output Directory Path :\t" + prop.getProperty("git_bash_path"));

            File deleteFilePath = new File(prop.getProperty("git_bash_path"));
            if (deleteFilePath.exists()) {
                deleteDirectoryRecursionJava6(deleteFilePath);
                gitLogger.info("Deleting the existing git files before checkout");
            }

            String repoUrl = prop.getProperty("git_repo_path");
            String cloneDirectoryPath = prop.getProperty("git_bash_path"); // Ex.in windows c:\\gitProjects\SpringBootMongoDbCRUD\

            System.out.println("Cloning " + repoUrl + " into " + repoUrl);
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(Paths.get(cloneDirectoryPath).toFile())
                    .call()
                    .close();
            inputStream.close();
            gitLogger.info("sucessfully checked out the files from git path : " + repoUrl + " into loacl path : " + cloneDirectoryPath);
            System.out.println("Completed Cloning");
            JOptionPane.showMessageDialog(null, "Successfully cloned the files from git repo...", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (GitAPIException exc) {
            System.out.println("Exception occurred while cloning repo");
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, exc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exc) {
            System.out.println("Exception occurred while cloning repo");
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, exc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Delet's all the existing content in the specified git path before performing checkout operation
    private void deleteDirectoryRecursionJava6(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursionJava6(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

    @Override
    public void updateGitCredentials(String userName, String password) {
        FileOutputStream outputStream = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            inputProp.load(inputStream);
            outputStream = new FileOutputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty("config_file_base_path"))) {
                prop.setProperty("config_file_base_path", inputProp.getProperty("config_file_base_path"));
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty("git_repo_path"))) {
                prop.setProperty("git_repo_path", inputProp.getProperty("git_repo_path"));
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty("output_file_directory"))) {
                prop.setProperty("output_file_directory", inputProp.getProperty("output_file_directory"));
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty("git_bash_path"))) {
                prop.setProperty("git_bash_path", inputProp.getProperty("git_bash_path"));
            }
            if (!StringUtils.isEmptyOrNull(inputProp.getProperty("license_file_path"))) {
                prop.setProperty("license_file_path", inputProp.getProperty("license_file_path"));
            }
            prop.setProperty("git_user_name", userName);
            prop.setProperty("git_user_password", password);
            prop.save(outputStream, "save");
            outputStream.close();
            inputStream.close();
            JOptionPane.showMessageDialog(null, "Successfully saved/updated git credentials...", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            ExceptionModel exceptions = new ExceptionModel(GCATConstants.GIT_SERVICE_IMPL, GCATConstants.UPDATE_GIT_CREDENTIALS, GCATExceptions.INPUT_FILE_MISSING);
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(GitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, exceptions.getExceptionDescription(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ExceptionModel exceptions = new ExceptionModel(GCATConstants.GIT_SERVICE_IMPL, GCATConstants.UPDATE_GIT_CREDENTIALS, GCATExceptions.INPUT_FILE_MISSING);
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(GitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, exceptions.getExceptionDescription(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
