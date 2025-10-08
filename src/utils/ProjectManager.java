package utils;

import model.QRCodeModel;
import model.UserProfile;

import java.io.*;

public class ProjectManager {
    
    public static void saveProject(QRCodeModel model, String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String fileName = model.getProjectName().replaceAll("[^a-zA-Z0-9\\-]", "_") + ".qrproj";
        File file = new File(directory, fileName);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(model);
        }
    }
    
    public static QRCodeModel loadProject(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (QRCodeModel) ois.readObject();
        }
    }
    
    public static void saveProfile(UserProfile profile, String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String fileName = profile.getProfileName().replaceAll("[^a-zA-Z0-9\\-]", "_") + ".qrprof";
        File file = new File(directory, fileName);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(profile);
        }
    }
    
    public static UserProfile loadProfile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (UserProfile) ois.readObject();
        }
    }
}