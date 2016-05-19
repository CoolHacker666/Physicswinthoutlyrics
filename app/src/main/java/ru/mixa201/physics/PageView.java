package ru.mixa201.physics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import ru.mixa201.physics.classes.Page;

public class PageView extends AppCompatActivity {
    private String path;
    private String APP_PATH;
    private Page page;
    private TextView t1;
    private ImageView imv;
    private Bitmap b;
    private String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);
        Intent intent=getIntent();
        path=intent.getStringExtra("PATH");
        APP_PATH=intent.getStringExtra("APP_PATH");
        File f=new File(APP_PATH+"server.cfg");
        try {
            server=new Scanner(f).nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imv=(ImageView)findViewById(R.id.idPv);
        try {
            ObjectInputStream r = new ObjectInputStream(new FileInputStream(new File(path)));
            page=(Page)r.readObject();

        } catch (IOException e){
            finish();
        } catch (ClassNotFoundException e) {
            finish();
        }
        t1=(TextView)findViewById(R.id.tv1);
        t1.setText(page.text);
        setTitle(page.name);
        AsyncTask<Void, Void, Void> a=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URLConnection u=new URL("http://"+server+"/media/"+page.imageName).openConnection();
                    b = BitmapFactory.decodeStream(u.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        a.execute();

        while(b==null){

        }

        imv.setImageBitmap(b);
        imv.setMinimumWidth(150);
        imv.setMinimumHeight(150);
    }
}
