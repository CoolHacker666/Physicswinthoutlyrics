package ru.mixa201.physics.classes;

import java.io.Serializable;

/**
 * Created by Admin on 12.03.2016.
 */
public class Test implements Serializable {
    public static final long serialVersionUID=1L;
    public String name;
    private boolean created;
    public boolean timed;
    public int minutes;
    public byte size;
    public byte answersCount;
    public String[] quests;
    public String[] answers;
    public byte[] tans;
    public String[] tags;
    public boolean[] isTexted;
    public String[] textedAns;
    public Test(String name){
        this.name=name;
    }
    public void createClassical(String[] quests, String[] answers, byte[] tans, String[] tags){
        this.quests=quests;
        this.answers=answers;
        this.tans=tans;
        this.tags=tags;
        this.size=10;
        this.answersCount=4;
        this.created=true;
        this.isTexted=new boolean[this.size];
        this.textedAns=new String[this.size];
    }
    public void createTimedClassical(int minutes, String[] quests, String[] answers, byte[] tans, String[] tags){
        this.timed=true;
        this.minutes=minutes;
        this.quests=quests;
        this.answers=answers;
        this.tans=tans;
        this.tags=tags;
        this.size=10;
        this.answersCount=4;
        this.created=true;
        this.isTexted=new boolean[this.size];
        this.textedAns=new String[this.size];
    }
    public void createCustom(byte size, byte answersCount, int minutes, String[] quests, String[] answers, byte[] tans, String[] tags, boolean[] isTexted, String[] textedAns){
        this.size=size;
        this.answersCount=answersCount;
        this.timed=true;
        this.minutes=minutes;
        this.quests=quests;
        this.answers=answers;
        this.tans=tans;
        this.tags=tags;
        this.isTexted=isTexted;
        this.textedAns=textedAns;
        this.created=true;
    }
}
