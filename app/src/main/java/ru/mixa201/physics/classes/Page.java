package ru.mixa201.physics.classes;

import java.io.Serializable;

/**
 * Created by Admin on 19.03.2016.
 */
public class Page implements Serializable {
    public static final long serialVersionUID=3L;
    public String name;
    public String text;
    public String imageName;
    public Page(String n){
        this.name=n;
    }
    public void setContext(String text){
        this.text=text;
    }
}
