package com.hotplate.hotplate;

import javafx.scene.shape.Path;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Serializable;

public class ResourceManager {

    public static void save(Serializable data, String fileName){
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(data);
            oos.close();
            fileOut.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object load(String fileName){
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
            return ois.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
