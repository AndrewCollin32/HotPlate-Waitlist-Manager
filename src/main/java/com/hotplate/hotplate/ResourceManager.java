package com.hotplate.hotplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Serializable;

public class ResourceManager {

    public static void save(Serializable data, String fileName){
        try {
            HotPlateApp.log.info("[Start] Initializing save");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(data);
            oos.close();
            fileOut.close();
        }
        catch (Exception e){
            HotPlateApp.log.severe("[Fail] Error: Couldn't save: " + e);
            HotPlateApp.launchLogError("[Fail] Error: Couldn't save: " + e);
        }
        HotPlateApp.log.info("[Success] Initializing Save");
    }

    public static Object load(String fileName){
        HotPlateApp.log.info("[Start] Initializing load");
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
            HotPlateApp.log.info("[Success] Initializing Load");
            return ois.readObject();
        }
        catch (Exception e){
            HotPlateApp.log.severe("[Fail] Initializing Load: " + e);
            HotPlateApp.launchLogError("[Fail] Initializing Load: " + e);
        }
        return null;
    }
}
