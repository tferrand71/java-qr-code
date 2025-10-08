package model;

import java.io.Serializable;

public class QRCodeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String text;
    private String filePath;
    private int width;
    private int height;
    private UserProfile currentProfile;
    private String projectName;
    
    public QRCodeModel() {
        this.width = 300;
        this.height = 300;
        this.currentProfile = new UserProfile();
        this.projectName = "Nouveau Projet";
    }
    
    // Getters et Setters
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public UserProfile getCurrentProfile() {
        return currentProfile;
    }
    
    public void setCurrentProfile(UserProfile profile) {
        this.currentProfile = profile;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}