package model;

import java.awt.Color;
import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String profileName;
    private String fontName;
    private int fontSize;
    private Color textColor;
    private Color backgroundColor;
    private String logoPath;
    
    public UserProfile() {
        this.profileName = "Profil par défaut";
        this.fontName = "Arial";
        this.fontSize = 12;
        this.textColor = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.logoPath = null;
    }
    
    // Getters et Setters
    public String getProfileName() { 
        return profileName; 
    }
    
    public void setProfileName(String profileName) { 
        this.profileName = profileName; 
    }
    
    public String getFontName() { 
        return fontName; 
    }
    
    public void setFontName(String fontName) { 
        this.fontName = fontName; 
    }
    
    public int getFontSize() { 
        return fontSize; 
    }
    
    public void setFontSize(int fontSize) { 
        this.fontSize = fontSize; 
    }
    
    public Color getTextColor() { 
        return textColor; 
    }
    
    public void setTextColor(Color textColor) { 
        this.textColor = textColor; 
    }
    
    public Color getBackgroundColor() { 
        return backgroundColor; 
    }
    
    public void setBackgroundColor(Color backgroundColor) { 
        this.backgroundColor = backgroundColor; 
    }
    
    public String getLogoPath() { 
        return logoPath; 
    }
    
    public void setLogoPath(String logoPath) { 
        this.logoPath = logoPath; 
    }
    
    @Override
    public String toString() {
        return profileName;
    }
}