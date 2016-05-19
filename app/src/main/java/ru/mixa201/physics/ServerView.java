package ru.mixa201.physics;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ru.mixa201.physics.classes.*;
import ru.mixa201.physics.classes.Package;

public class ServerView extends ListActivity {

    public static Client client;
    public String APP_PATH;
    public static pack[] packs;
    public static Package pack;
    public static int position1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        APP_PATH=intent.getStringExtra("APP_PATH");
        File f=new File(APP_PATH+"server.cfg");
        try {
            f.createNewFile();
            Scanner in=new Scanner(f);
            client=new Client(in.nextLine(), APP_PATH);
            in.close();
        } catch (IOException e) {
            Log.e("Readin server.cfg error",e.toString());
            e.printStackTrace();
            finish();
        }
        packs=null;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    packs=client.getAvailablePackages();
                } catch (IOException e) {
                    Log.e("Building package list error",e.toString());
                    e.printStackTrace();
                    finish();
                }
            }
        });
        t.start();
        while(t.isAlive()){

        }
        String[] packages=new String[packs.length];
        for(int i=0;i<packages.length;i++){
            packages[i]=packs[i].name+" v"+packs[i].version;
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,packages);
        setListAdapter(a);
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        position1=position;
        pack=null;

        try {
            ServerView.pack=ServerView.client.getPackage(ServerView.packs[ServerView.position1].name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            SettingsActivity.installPackage(pack, APP_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            Toast tt=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
            tt.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast tt=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
            tt.show();
        }
    }
}

