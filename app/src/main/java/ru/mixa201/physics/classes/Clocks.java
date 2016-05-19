package ru.mixa201.physics.classes;

/**
 * Created by Admin on 13.03.2016.
 */
public class Clocks {
    int minutes;
    int seconds;
    public Clocks(int minutes, int seconds){
        this.minutes=minutes;
        this.seconds=seconds;
    }
    public boolean minus(){
        seconds-=1;
        if(seconds<0){
            if(minutes>0) {
                minutes -= 1;
                seconds = 59;
            }
            else{
                return false;
            }
        }
        return true;
    }
    public String toString(){
        String m,s;
        m=""+minutes;
        if(seconds>9){
            s=""+seconds;
        }
        else s="0"+seconds;
        return m+":"+s;
    }
}
