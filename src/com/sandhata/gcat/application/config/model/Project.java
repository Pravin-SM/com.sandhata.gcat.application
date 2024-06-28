/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sandhata.gcat.application.config.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Project {

    private String name;
    private String releaseBranch;
    private String description;
    private String jira;
    private String authorEmailId;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Project() {
    }

    public Project(String name, String releaseBranch, String description, String jira, String authorEmailId) {
        this.name = name;
        this.releaseBranch = releaseBranch;
        this.description = description;
        this.jira = jira;
        this.authorEmailId = authorEmailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseBranch() {
        return releaseBranch;
    }

    public void setReleaseBranch(String releaseBranch) {
        this.releaseBranch = releaseBranch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJira() {
        return jira;
    }

    public void setJira(String jira) {
        this.jira = jira;
    }

    public String getAuthorEmailId() {
        return authorEmailId;
    }

    public void setAuthorEmailId(String authorEmailId) {
        this.authorEmailId = authorEmailId;
    }

    @Override
    public String toString() {
        return "Project{" + "name=" + name + ", releaseBranch=" + releaseBranch + ", description=" + description + ", jira=" + jira + ", authorEmailId=" + authorEmailId + '}';
    }

    public String generateComments() {
        String op = "#### Auto Generated";
        if (this.jira != null) {
            op += " for JIRA -" + this.jira;
        }
        if (this.name != null) {
            op += ". Project Name - " + this.name;
        }
        if (this.releaseBranch != null) {
            op += ". Release - " + this.releaseBranch;
        }
        if (this.authorEmailId != null) {
            op += ". Created by - " + this.authorEmailId;
        }
        if (this.description != null) {
            op += ". Change Description - " + this.description;
        }
        op += ". On - " + sdf.format(new Date()) + ". ####";
        return (op);
    }
}
