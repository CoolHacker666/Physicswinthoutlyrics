package ru.mixa201.physics;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import ru.mixa201.physics.classes.Manifest;

/**
 * Created by Admin on 12.03.2016.
 */
public class ContentView extends ListActivity {
    private String action;
    private String pack;
    private String WORK_PATH;
    private String[] filePaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii=getIntent();
        action=ii.getStringExtra("ACTION");
        pack=ii.getStringExtra("PACKAGE");
        if(!action.equals("quests")) {
            WORK_PATH = Launcher.APP_PATH + action + "/" + pack + "/";
            try {
                Manifest m;
                ObjectInputStream r = new ObjectInputStream(new FileInputStream(new File(WORK_PATH + "main.pmf")));
                m = (Manifest) r.readObject();
                r.close();
                filePaths = m.getNames();
            } catch (Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "Manifest reading error!", Toast.LENGTH_LONG);
                t.show();
                Log.wtf("Manifest error", e.toString());
                finish();
            }
            ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filePaths);
            setListAdapter(a);
        }
        else{
            WORK_PATH=ii.getStringExtra("DATABASE");
            SQLiteDatabase db=openOrCreateDatabase(WORK_PATH, MODE_PRIVATE, null);
            Cursor rs1=db.rawQuery("SELECT * FROM [Manifest] WHERE name='"+pack+"'",null);
            rs1.moveToFirst();
            pack=""+rs1.getInt(0);
            Cursor rs=db.rawQuery("SELECT * FROM [Quests] WHERE package="+pack,null);
            Log.i("QUERY RESULT",""+rs.getCount()+" package #"+pack);
            rs.move(0);
            Vector<String> v=new Vector<String>();
            while (rs.moveToNext()){
                v.add(rs.getString(3));
            }
            String[] s=new String[v.size()];
            for(int i=0;i<v.size();i++){
                s[i]=v.get(i);
            }
            filePaths=s;
            ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);
            setListAdapter(a);
            db.close();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i=new Intent();
        if (action.equals("tests")) {
            i.putExtra("PATH", WORK_PATH + filePaths[position] + ".ptf");
            i.setClass(getApplicationContext(), TestView.class);
        }
        else if (action.equals("pages")){
            i.putExtra("PATH", WORK_PATH + filePaths[position] + ".ppf");
            i.putExtra("APP_PATH", Launcher.APP_PATH);
            i.setClass(getApplicationContext(), PageView.class);
        }
        else if(action.equals("quests")){
            i.putExtra("QUEST",""+filePaths[position]);
            i.putExtra("DATABASE",WORK_PATH);
            i.putExtra("PACKAGE",pack);
            i.setClass(getApplicationContext(),QuestView.class);
        }
        else{
            return;
        }
        startActivity(i);
        finish();
    }
}
