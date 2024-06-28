/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.service.impl;

import com.sandhata.gcat.application.config.Log4Config;
import com.sandhata.gcat.application.config.model.License;
import com.sandhata.gcat.application.config.model.PartnerDetails;
import com.sandhata.gcat.application.config.model.PartnerInstanceInfo;
import com.sandhata.gcat.application.config.model.PartnerOperation;
import com.sandhata.gcat.application.config.model.Project;
import com.sandhata.gcat.application.constants.GCATConstants;
import com.sandhata.gcat.application.dto.TableViewDto;
import com.sandhata.gcat.application.exception.ExceptionModel;
import com.sandhata.gcat.application.exception.GCATExceptions;
import com.sandhata.gcat.application.exception.MyExceptions;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;
import com.sandhata.gcat.application.service.FileOperationService;
import com.sandhata.gcat.application.service.Service;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class FileOperationServiceImpl implements FileOperationService {

    private Properties prop = new Properties();
    private Properties configProp = new Properties();
    private Properties fileOperationProp = new Properties();
    File file = new File("");
    private org.apache.log4j.Logger logger = (new Log4Config()).getLogger(file.getAbsolutePath() + "\\gcatLogs.log", "FileOperationServiceImpl");

    @Override
    public void newPartNewOp(Project project, PartnerInstanceInfo partnerInstanceInfo, PartnerDetails partnerDetails, List<PartnerOperation> partnerOperationList, String fileName) {

        String text = "";
        String template = "";
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream configTempFile = new FileInputStream(new File(file.getAbsoluteFile() + "\\gatewayconfig.properties"));
            configProp.load(configTempFile);

            File file = new File(prop.getProperty("config_file_base_path") + "\\" + partnerInstanceInfo.getScope() + "\\" + fileName);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
            text += "\n\n" + project.generateComments() + "\n";

            for (PartnerOperation temp : partnerOperationList) {
                System.out.println(temp.toString());

                if (fileName.equalsIgnoreCase("FacadeAccess.cfg")) {
                    String newText = facadeAccess(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                if (fileName.equalsIgnoreCase("FacadeOperation.cfg")) {
                    String newText = facadeOperation(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                if (fileName.equalsIgnoreCase("Monitor.cfg")) {
                    String newText = monitor(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                if (fileName.equalsIgnoreCase("PartnerData.cfg")) {
                    String newText = partnerData(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                if (fileName.equalsIgnoreCase("Routing.cfg")) {
                    String newText = routing(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                if (fileName.equalsIgnoreCase("TargetOperation.cfg")) {
                    String newText = targetOperation(temp, partnerDetails, fileName);
                    if (!text.contains(newText)) {
                        text += newText;
                    }
                }
                System.out.println("Outside\tTransform :\t" + temp.getTransform() + "\tMask :\t" + temp.getMask() + "\tExpression :\t" + (temp.getTransform() || temp.getMask()));
                if (temp.getTransform() || temp.getMask()) {
//                    System.out.println("Inside\tTransform :\t" + temp.getTransform() + "\tMask :\t" + temp.getMask() + "\tExpression :\t" + (temp.getTransform() || temp.getMask()));
                    if (fileName.equalsIgnoreCase("Mapping.cfg")) {
                        if ((temp.getTransform() || temp.getMask())) {
                            System.out.println("Invoking mapping method");
                            text += mapping(temp, partnerDetails, fileName);
                        } else {
                            System.out.println("There is no content to write in Mapping.cfg");
                        }
                    }
                }
            }
            System.out.println(template);
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(prop.getProperty("output_file_directory") + "\\" + fileName)));
            bw.write(text);
            bw.close();
            inputStream.close();
            configTempFile.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public String getConfigName(PartnerOperation partnerOperation, String fileName) {
        String op = "";

        System.out.println("Config Name :\t" + partnerOperation.getInterfac());

        if (fileName.equalsIgnoreCase("FacadeAccess.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Facade_Access_JSON";
            } else {
                op = "Facade_Access_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("FacadeOperation.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Facade_Operation_JSON";
            } else {
                op = "Facade_Operation_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("Monitor.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Monitor_JSON";
            } else {
                op = "Monitor_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("Routing.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Routing_JSON";
            } else {
                op = "Routing_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("TargetOperation.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Target_Operation_JSON";
            } else {
                op = "Target_Operation_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("Mapping.cfg")) {
            if (partnerOperation.getInterfac().equalsIgnoreCase("rest")) {
                op = "Mapping_JSON";
            } else {
                op = "Mapping_SOAP";
            }
        }
        if (fileName.equalsIgnoreCase("PartnerData.cfg")) {
            op = "Partner_Data";
        }
        return (op);
    }

    @Override
    public List<String> getFiles(String operation) {
        List< String> fileNamesList = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream);
            fileNamesList = Arrays.asList((fileOperationProp.get(operation)).toString().split(","));
            inputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        fileNamesList.forEach(temp -> {
            System.out.println(temp);
        });
        return (fileNamesList);
    }

    //Used to write data into Facade Access.cfg File
    private String facadeAccess(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {
        String template = "";
        System.out.println("Interface within Facade Access :\t" + temp.getInterfac());
        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //partner_{0}|po_{1}_{2}|{3}|R-{4}-{5}-{6}-{7}|{8}|false|{9}|{10}||||
            //partner_{0}|po_{1}_{2}|{3}|R-{4}-{5}-{6}-{7}|{8}|false||||||

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", partnerDetails.getName());
            template = template.replace("{1}", partnerDetails.getName());
            template = template.replace("{2}", temp.getOperationName());
            template = template.replace("{3}", temp.getOperationName());
            template = template.replace("{4}", partnerDetails.getName());
            template = template.replace("{5}", temp.getAbbr());
            template = template.replace("{6}", temp.getGap());
            template = template.replace("{7}", temp.getReq());
            template = template.replace("{8}", "" + temp.getFacade());
            if (!temp.getTransform()) {
                template = template.replace("{9}", "");
            } else {
                //Logic for Transform Name
                template = template.replace("{9}", "mp_" + partnerDetails.getName() + "RequestTransform");
            }
            if (!temp.getMask()) {
                template = template.replace("{10}", "");
            } else {
                //Logic for Mask Name
                template = template.replace("{10}", "mp_" + partnerDetails.getName() + temp.getOperationName() + "ResponseMasking");
            }
        } else {
            //partner_{0}|po_{1}_{2}_JSON|{3}_JSON|R-{4}-{5}J-{6}-{7}|{8}|false|{9}|||||
            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", partnerDetails.getName());
            template = template.replace("{1}", partnerDetails.getName());
            template = template.replace("{2}", temp.getOperationName());
            template = template.replace("{3}", temp.getOperationName());
            template = template.replace("{4}", partnerDetails.getName());
            template = template.replace("{5}", temp.getAbbr());
            template = template.replace("{6}", temp.getGap());
            template = template.replace("{7}", temp.getReq());
            template = template.replace("{8}", "" + (temp.getFacade()));
            if (temp.getMethod().equalsIgnoreCase("post")) {
                template = template.replace("{9}", "mp_Action");
            } else {
                if (temp.getMethod().equalsIgnoreCase("get")) {
                    template = template.replace("{9}", "mp_ACTION_GET");
                } else {
                    template = template.replace("{9}", "mp_ACTION_PUT");
                }
            }
        }
        return (template + "\n");
    }

    //Used to write data into Facade Operation.cfg File
    private String facadeOperation(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {
        String template = "";
        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //operation_{0}|"{1}"|||{2}||GenericFaultXSLT.xsl|||||500

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getSoapActionUri());
            template = template.replace("{2}", temp.getOperationName());
        } else {
            //operation_{0}_JSON||{1}|{2}_JSON|{3}_JSON||Fault_JSON||||{4}|500
            //operation_Pravin_Operations_Name_JSON||Pravin_Operations_Soap_Action_OR_URI|Pravin_Operations_Name_JSON|Pravin_Operations_Name_JSON||Fault_JSON||||PUT|500
            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getSoapActionUri());
            template = template.replace("{2}", temp.getOperationName());
            template = template.replace("{3}", temp.getOperationName());
            template = template.replace("{4}", temp.getMethod());
        }
        return (template + "\n");
    }

    //Used to write data into Monitor.cfg File
    private String monitor(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {

        String template = "";
        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //Rate|throttle_R-{0}-{1}-{2}-{3}|{4}|{5}|||R-{6}-{7}-{8}-{9}|null||null|RequestCount|

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", partnerDetails.getName());
            template = template.replace("{1}", temp.getAbbr());
            template = template.replace("{2}", temp.getGap());
            template = template.replace("{3}", temp.getReq());
            template = template.replace("{4}", temp.getGap());
            template = template.replace("{5}", temp.getReq());
            template = template.replace("{6}", partnerDetails.getName());
            template = template.replace("{7}", temp.getAbbr());
            template = template.replace("{8}", temp.getGap());
            template = template.replace("{9}", temp.getReq());
        } else {
            //Rate|throttle_R-{0}-{1}J-{2}-{3}|{4}|{5}|||R-{6}-{7}J-{8}-{9}|null||null|RequestCount|

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", partnerDetails.getName());
            template = template.replace("{1}", temp.getAbbr());
            template = template.replace("{2}", temp.getGap());
            template = template.replace("{3}", temp.getReq());
            template = template.replace("{4}", temp.getGap());
            template = template.replace("{5}", temp.getReq());
            template = template.replace("{6}", partnerDetails.getName());
            template = template.replace("{7}", temp.getAbbr());
            template = template.replace("{8}", temp.getGap());
            template = template.replace("{9}", temp.getReq());
        }
        return (template + "\n");
    }

    //Used to write data into Partner Data.cfg File
    private String partnerData(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {
        String template = "";

        //partner_{0}|{1}|{2}|{3}||{4}|{5}|{6}||false||
        template = configProp.getProperty(getConfigName(temp, fileName));
        template = template.replace("{0}", partnerDetails.getName());
        template = template.replace("{1}", partnerDetails.getName());
        template = template.replace("{2}", partnerDetails.getEmailID());
        template = template.replace("{3}", partnerDetails.getPhone());
        template = template.replace("{4}", partnerDetails.getGroup());
        template = template.replace("{5}", partnerDetails.getSerialNumber());
        template = template.replace("{6}", partnerDetails.getCaIssuer());

        return (template + "\n");
    }

    //Used to write data into Routing.cfg File
    private String routing(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {

        String template = "";
        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //{0}||{1}||default|

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getOperationName());

        } else {
            //{0}_JSON||{1}_JSON||default|

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getOperationName());

        }
        return (template + "\n");

    }

    //Used to write data into Mapping.cfg File
    private String mapping(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {

        String template = "";
        StringBuilder sb = new StringBuilder();

        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //{0}|xslt|{1}.xsl|full||||

            String op = "";
            template = configProp.getProperty(getConfigName(temp, fileName));
            if (temp.getTransform()) {
                op = template.replace("{0}", "mp_" + temp.getOperationName() + "_RequestTransform");
                op = op.replace("{1}", temp.getOperationName() + "RequestTransform") + "\n";
                sb.append(op);
            }
            op = "";
            if (temp.getMask()) {
                op = template.replace("{0}", "mp_" + temp.getOperationName() + "_ResponseMasking");
                op = op.replace("{1}", temp.getOperationName() + "ResponseMasking") + "\n";
                sb.append(op);
            }

        } else {
            //{0}|xslt|{1}.xsl|full||||

            template = configProp.getProperty(getConfigName(temp, fileName));

            String op = "";
            //INBOUND - mp_HTTPJSONToJMS
            //OUTBOUND - sb_inbound.xsl
            if ((temp.getMethod().equals("") || (temp.getMethod() == null)) && (temp.getTransform())) {
                op = template.replace("{0}", "mp_Request" + temp.getOperationName() + "JSON");
                op = op.replace("{1}", "sb_inbound") + "\n";
                sb.append(op);
            }
            op = "";
            if ((temp.getMethod().equals("") || (temp.getMethod() == null)) && (temp.getMask())) {
                op = template.replace("{0}", "mp_Response" + temp.getOperationName() + "JSON");
                op = op.replace("{1}", "sb_outbound") + "\n";
                sb.append(op);
            }

            //if not null
            //INBOUND - mp_JMSJSONToHTTP
            //OUTBOUND - sb_outbound.xsl
            //Check what is Action XSLT
            op = "";
            if ((!temp.getMethod().equals("")) || (!(temp.getMethod() == null))) {
                String actionXSLT = "";
                if (temp.getMethod().equalsIgnoreCase("post")) {
                    actionXSLT = "mp_ACTION";
                }
                if (temp.getMethod().equalsIgnoreCase("get")) {
                    actionXSLT = "mp_ACTION_GET";
                }
                if (temp.getMethod().equalsIgnoreCase("PUT")) {
                    actionXSLT = "mp_ACTION_PUT";
                }
                if (temp.getMethod().equalsIgnoreCase("DELETE")) {
                    actionXSLT = "mp_ACTION_DELETE";
                }
                if (temp.getTransform()) {
                    op = template.replace("{0}", actionXSLT);
                    op = op.replace("{1}", "sb_inbound") + "\n";
                    sb.append(op);
                }
                op = "";
                if (temp.getMask()) {
                    op = template.replace("{0}", actionXSLT);
                    op = op.replace("{1}", "sb_outbound") + "\n";
                    sb.append(op);
                }
            }

        }
        return (sb.toString());

    }

    //Used to write data into Target Operation.cfg File
    private String targetOperation(PartnerOperation temp, PartnerDetails partnerDetails, String fileName) {

        String template = "";

        if (temp.getInterfac().equalsIgnoreCase("soap")) {
            //service_{0}|{1}JMS|||{2}||||||||||"{3}"|||VOD.UK.PR.TILIF.Request.{4}.{5}|topic||Text Message|false

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getInterfac());
            template = template.replace("{2}", temp.getTarget());
            template = template.replace("{3}", temp.getSoapActionUri());
            template = template.replace("{4}", temp.getInterfac());
            template = template.replace("{5}", temp.getPostFix());

        } else {
            //service_{0}_JSON|ESB|||{1}||mp_HTTPJSONToJMS|mp_JMSJSONToHTTP|||||||0,VOD.UK.PR.TILIF.Request.{2}.{3},topic|,sync||

            template = configProp.getProperty(getConfigName(temp, fileName));
            template = template.replace("{0}", temp.getOperationName());
            template = template.replace("{1}", temp.getTarget());
            template = template.replace("{2}", "JSON");
            template = template.replace("{3}", temp.getPostFix());

        }
        return (template + "\n");

    }

    //Get all Partner Details
    @Override
    public PartnerDetails getPartnerDetails(String instance, String partner) {
        PartnerDetails partnerDetails = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream inputStream2 = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream2);
            File file = new File(prop.getProperty("config_file_base_path") + "\\" + instance + "\\" + fileOperationProp.getProperty("Partner_Data"));
//            System.out.println("Config File Path :\t" + prop.getProperty("config_file_base_path") + "\nPartner Data File Naame :\t" + file.getAbsolutePath());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith("partner_" + partner)) {
//                  System.out.println(line+"\nSub String :\t"+line.substring(line.indexOf("_")+1 , line.length()));
                    line = line.substring(line.indexOf("_") + 1, line.length());
                    String[] textArray = line.split(Pattern.quote("|"));
                    //String group, String name, String phone, String emailID, String caIssuer, String serialNumber

                    partnerDetails = new PartnerDetails(textArray[5], textArray[0], textArray[3], textArray[2], textArray[7], textArray[6]);
                    break;
                }
            }

            br.close();
            inputStream.close();
            inputStream2.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetPartnerDetails\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (partnerDetails);
        }
    }

    //Existing Partner New Operation Scenario
    @Override
    public Set<PartnerOperation> exPartNewOp(String instance, String partner, ObservableList<TableViewDto> tvList, ObservableList<String> interfaceOList, ObservableList<String> methodOList) {
        Set<PartnerOperation> facadeAccessSet = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream inputStream2 = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream2);
            File file1 = new File(prop.getProperty("config_file_base_path") + "\\" + instance + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_1"));
            File file2 = new File(prop.getProperty("config_file_base_path") + "\\" + instance + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_2"));
            File file3 = new File(prop.getProperty("config_file_base_path") + "\\" + instance + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_3"));
            List<String> writeFileNames = Arrays.asList(prop.getProperty("config_file_base_path") + "\\" + instance + "\\" + fileOperationProp.getProperty("ExistingPartner_NewOperation_4").split(","));
            facadeAccessSet = getFacadeAccessData(file1, file2, file3, partner);
            inputStream.close();
            inputStream2.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tfileOpGetOperation\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (facadeAccessSet);
        }
    }

    //Helper Method to get the Facade Access Data from various files
    @Override
    public Set<PartnerOperation> getFacadeAccessData(File file1, File file2, File file3, String partner) {
        Set<PartnerOperation> facadeAccessSet = new HashSet<PartnerOperation>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("partner_" + partner)) {
                    line = line.substring(line.indexOf("_") + 1, line.length());
                    String[] data = line.split(Pattern.quote("|"));
                    PartnerOperation partnerOperation = null;
                    if (data[2].contains("JSON")) {
//                        System.out.println("JSON Data Length :\t" + data.length);
                        if (data.length > 6) {
                            partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "REST", getJSONMethod(file3, data[2]), fOperationGetPostActionURI(file3, data[2]), getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), getTargetCount(file2, data[2]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]));
                        } else {
                            partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "REST", getJSONMethod(file3, data[2]), fOperationGetPostActionURI(file3, data[2]), getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), getTargetCount(file2, data[2]), false, Boolean.parseBoolean(data[5]));
                        }
                    } else {
                        //partner_HURM|po_HURM_GetServiceBusinessDetails.1|GetServiceBusinessDetails.1|R-HURM-GSBD1-10-10|35000|false||||||
                        //partner_{0}|po_{1}_{2}|{3}|R-{4}-{5}-{6}-{7}|{8}|false|{9}|{10}||||
                        //String operationName, String abbr, String interfac, String method, String soapActionUri, String postFix, String req, String gap, String facade, String target, boolean transform, boolean mask
//                        System.out.println("SOAP Data Length :\t" + data.length);
                        if (data.length > 6) {
                            partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "SOAP", "", fOperationGetPostActionURI(file3, data[2]), getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), getTargetCount(file2, data[2]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]));
                        } else {
                            partnerOperation = new PartnerOperation(data[2], data[3].split("-")[2], "SOAP", "", fOperationGetPostActionURI(file3, data[2]), getPostFix(file2, data[2]), data[3].split("-")[3], data[3].split("-")[4], "" + (Integer.parseInt(data[4]) / 1000), getTargetCount(file2, data[2]), Boolean.parseBoolean(data[5]), false);
                        }

                    }
                    facadeAccessSet.add(partnerOperation);
                }
            }
            br.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetFacadeAccessData\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (facadeAccessSet);
        }
    }

    //Get the postfix operation for a given operation name
    @Override
    public String fOperationGetPostActionURI(File tempFile, String opName) {
        String line = "";
        String soapUri = "";
        String postFixString = "";
        try {
            File file = new File("");
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream inputStream2 = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream2);

            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            line = "";
//            System.out.println("Inside facade Operation URI");
            while ((line = br.readLine()) != null) {
                if (line.startsWith("operation_" + opName)) {
                    String[] data = line.split(Pattern.quote("|"));
//                    System.out.println("uri :\t" + data.length);
                    if (line.contains("JSON")) {
//                        postFixString = fileOperationProp.getProperty("PostFix_JSON");
//                        System.out.println("Get Post Fix JSON data[14] :\t" + data[2]);
                        System.out.println("JSON URI DATA[2] :\t" + data[2]);
                        soapUri = data[2];

                    } else {
//                        postFixString = fileOperationProp.getProperty("PostFix_SOAP");
//                        System.out.println("Get Post Fix SOAP data[14] :\t" + data[14]);
                        System.out.println("SOAP URI DATA[1] :\t" + data[1]);
                        soapUri = data[1].replaceAll("\"", "");

                    }
                }
            }
            br.close();
            inputStream.close();
            inputStream2.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetTargetOperation\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (soapUri);
        }
    }

    @Override
    public String getPostFix(File tempFile, String opName) {
        String postFix = "";
        String line = "";
        try {
            File file = new File("");
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            FileInputStream inputStream2 = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(inputStream2);
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            line = "";
//            System.out.println("Inside Post Fix");
//            System.out.println("Operation Name :\t" + opName);
            while ((line = br.readLine()) != null) {
                line = line.substring(line.indexOf("_") + 1, line.length());
                if (line.startsWith(opName)) {
                    String[] data = line.split(Pattern.quote("|"));
//                    System.out.println("data_1 :\t" + data[1] + "\nPost Fix String :\t" + fileOperationProp.getProperty("PostFix_SOAP"));
                    if (data[1].contains("SOAPJMS")) {
                        postFix = data[17].substring(fileOperationProp.getProperty("PostFix_SOAP").length(), data[17].length());
//                        System.out.println("Post Fix data :\t" + data[2] + "\nPostFix String :\t" + postFix);
                        //postFix = data[15];
                    } else {
                        postFix = data[14].substring(fileOperationProp.getProperty("PostFix_JSON").length(), data[14].length());
//                        System.out.println("Post Fix data :\t" + data[14] + "\nPostFix String :\t" + postFix);
                    }
                }
            }
            br.close();
            inputStream.close();
            inputStream2.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetTargetOperation\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (postFix);
        }
    }

    @Override
    public String getJSONMethod(File tempFile, String opName) {
        String line;
        String op = "GET";
        try {
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
//            System.out.println("getJSONMethod Op_Name :\t" + opName + "\nGet JSON Method File Name :" + tempFile.getAbsolutePath());
            while ((line = br.readLine()) != null) {
                line = line.substring(line.indexOf("_") + 1, line.length());

                if (line.startsWith(opName)) {
                    String[] data = line.split(Pattern.quote("|"));
                    op = data[10];
//                    System.out.println("Data[10] :\t" + data[10] + "\nOutput :\t" + op);
                }
            }
            br.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetJSONMethod\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (op);
        }
    }

    @Override
    public String getTargetCount(File tempFile, String opName) {
        String targetCount = "";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            line = "";
            while ((line = br.readLine()) != null) {
                line = line.substring(line.indexOf("_") + 1, line.length());
                if (line.startsWith(opName)) {
                    String[] data = line.split(Pattern.quote("|"));
                    targetCount = "" + (Integer.parseInt(data[4]) / 1000);
                }
            }
            br.close();
        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Caught in Exception inside Class :\tFileOperationController\tMethod :\tgetTargetOperation\nAnd the exception is :\t" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            return (targetCount);
        }
    }

    //Get the input file path for the file name that is passed as argument
    @Override
    public File getInputFilePath(String instanceName, String fileName) {
        String opFile = "";
        try {
            Properties applicationProp = new Properties();

            Properties fileOperationProp = new Properties();
            File file = new File("");
            FileInputStream applicationStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            applicationProp.load(applicationStream);
            FileInputStream fileOperationStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\FileOperation.properties"));
            fileOperationProp.load(fileOperationStream);
            opFile += applicationProp.getProperty("config_file_base_path") + "\\" + instanceName + "\\";
            if (fileName.equalsIgnoreCase("facadeAccess.cfg")) {
                opFile += "FacadeAccess.cfg";
            }
            if (fileName.equalsIgnoreCase("facadeoperation.cfg")) {
                opFile += "FacadeOperation.cfg";
            }
            if (fileName.equalsIgnoreCase("partnerdata.cfg")) {
                opFile += "PartnerData.cfg";
            }
            if (fileName.equalsIgnoreCase("targetOperation.cfg")) {
                opFile += "TargetOperation.cfg";
            }
            if (fileName.equalsIgnoreCase("monitor.cfg")) {
                opFile += "Monitor.cfg";
            }
            if (fileName.equalsIgnoreCase("mapping.cfg")) {
                opFile += "Mapping.cfg";
            }
            if (fileName.equalsIgnoreCase("partnerGroup.cfg")) {
                opFile += "PartnerGroup.cfg";
            }
            if (fileName.equalsIgnoreCase("routing.cfg")) {
                opFile += "Routing.cfg";
            }
            applicationStream.close();
            fileOperationStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            return (new File(opFile));
        }
    }

    //Reads the content of the output directory where the generated 
    //config file are and then updates the changes in the source file (checkout folder)
    @Override
    public void mergeCfgData(String instance, List<File> fileNames) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(file.getAbsoluteFile() + "\\Application.properties"));
            prop.load(inputStream);
            String line = "";
            String content = "";
            for (File temp : fileNames) {
                BufferedReader fileReader = new BufferedReader(new FileReader(new File(prop.getProperty("output_file_directory") + "\\" + temp.getName())));
                content = "";
                while ((line = fileReader.readLine()) != null) {
                    content += line + "\n";
                }
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(prop.getProperty("git_bash_path") + "\\" + instance + "\\" + temp.getName())));
                fileWriter.write(content);
                fileWriter.close();
                fileReader.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
