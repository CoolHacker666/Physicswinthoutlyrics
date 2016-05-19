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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import ru.mixa201.physics.classes.Manifest;

public class PackageView extends ListActivity {
    private String[] packages;
    private String mPath;
    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPath = intent.getStringExtra("MANIFEST");
        action = intent.getStringExtra("ACTION");
        if(!action.equalsIgnoreCase("quests")) {
            Manifest m = new Manifest();
            try {
                ObjectInputStream r = new ObjectInputStream(new FileInputStream(new File(mPath)));
                m = (Manifest) r.readObject();
                r.close();
            } catch (IOException e) {
                Toast t = Toast.makeText(getApplicationContext(), "Manifest reading error!", Toast.LENGTH_LONG);
                t.show();
                Log.wtf("Manifest error", e.toString());
                finish();
            } catch (ClassNotFoundException e) {
                Toast t = Toast.makeText(getApplicationContext(),/*"Manifest errored!"*/ e.toString(), Toast.LENGTH_LONG);
                t.show();
                Log.wtf("Manifest error", e.toString());
                finish();
            }
            packages = m.getNames();
            ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, packages);
            setTitle("Пакеты");
            setListAdapter(a);
        }
        else{

                SQLiteDatabase db=openOrCreateDatabase(mPath, MODE_PRIVATE, null);
                Cursor rs=db.rawQuery("SELECT * FROM [Manifest]", null);
                rs.move(0);
                Vector<String> v=new Vector<String>();
                while(rs.moveToNext()){
                    v.add(rs.getString(1));
                }
                String[] s=new String[v.size()];
                for(int i=0;i<v.size();i++){
                    s[i]=v.get(i);
                }
            packages=s;
                ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);
                setTitle("Пакеты");
                setListAdapter(a);
                db.close();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i=new Intent();
        if(!action.equals("quests")) {
            i.putExtra("PACKAGE", packages[position]);
        }
        else{
            i.putExtra("PACKAGE", packages[position]);
            i.putExtra("DATABASE", mPath);
        }
        i.putExtra("ACTION",action);
        i.setClass(getApplicationContext(), ContentView.class);
        startActivity(i);
        finish();
    }
}
