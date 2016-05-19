package ru.mixa201.physics.classes;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Admin on 12.03.2016.
 */
public class Manifest implements Serializable {
    public static final long serialVersionUID=2L;
    public Vector<String> fileNames;
    public Manifest(){
        fileNames=new Vector<String>();
    }
    public void registerFile(String path){
        fileNames.add(path);
    }
    public void unregisterFile(String name){
        fileNames.remove(name);
    }
    public String[] getNames(){
        String[] s=new String[fileNames.size()];
        for(int i=0;i<s.length;i++){
            s[i]=fileNames.get(i);
        }
        return s;
    }
}
