package ru.mixa201.physics;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import ru.mixa201.physics.classes.*;
import ru.mixa201.physics.classes.Package;

public class SettingsActivity extends AppCompatActivity {
    private Button install;
    private Button ifs;
    private EditText path;
    private static String APP_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent=getIntent();
        APP_PATH=intent.getStringExtra("APP_PATH");
        path = (EditText) findViewById(R.id.filePath);
        install = (Button) findViewById(R.id.btnInstall);
        ifs=(Button) findViewById(R.id.btnIFS);
        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    installPackage(APP_PATH+path.getText().toString()+".pipf");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("APP_PATH",APP_PATH);
                i.setClass(getApplicationContext(),ServerView.class);
                startActivity(i);
            }
        });
    }

    public static void installPackage(String path) throws IOException, ClassNotFoundException {
        File f=new File(path);
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(f));
        Package pack= (Package) in.readObject();
        in.close();
        pack.install(APP_PATH);
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(APP_PATH+"questionary.db",null);
        //try {
            for (int i = 0; i < pack.toQM.size(); i++) {
                db.execSQL("INSERT INTO [Manifest](name) VALUES ('" + pack.toQM.get(i) + "')");
                Cursor c = db.rawQuery("SELECT * FROM [Manifest] WHERE name='" + pack.toQM.get(i) + "'", null);
                c.moveToFirst();
                int mid = c.getInt(0);
                for (int k = 0; k < pack.toQD.size(); k++) {
                    db.rawQuery("INSERT INTO[Defaults](view, package) VALUES ('" + pack.toQD.get(i) + "', " + mid + ")", null);
                }
                for (int j = 0; j < pack.toQQ.size(); j++) {
                    Quest q = pack.toQQ.get(j);
                    db.execSQL("INSERT INTO [Quests](quest, package, name) VALUES ('" + q.getQuest() + "', " + mid + ", '" + q.getName() + "')");
                    Cursor c1 = db.rawQuery("SELECT * FROM [Quests] WHERE name='" + q.getName() + "'", null);
                    c1.moveToFirst();
                    Log.i("c1 length",""+c1.getCount());
                    int qid = c1.getInt(0);
                    db.execSQL("INSERT INTO [Formulas](view, quest) VALUES ('" + q.getFormula() + "', " + qid + ")");
                    for (int k = 0; k < q.getValues().size(); k++) {
                        Value v = q.getValues().get(k);
                        db.execSQL("INSERT INTO [Values](type, value, descript, quest) VALUES ('" + v.getType() + "', " + v.getValue() + ", '" + v.getDescript() + "', " + qid+")");
                    }
                    db.execSQL("INSERT INTO [Targets](target, quest) VALUES ('" + q.getTarget() + "', " + qid + ")");
                }
            }
            db.close();
        /*} catch (Exception e){
            Log.wtf("ERROR",e.getMessage());
            db.close();
        }*/
    }

    public static void installPackage(Package pack, String APP_PATH) throws IOException, ClassNotFoundException {
        pack.install(APP_PATH);
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(APP_PATH+"questionary.db",null);
        for (int i = 0; i < pack.toQM.size(); i++) {
            db.execSQL("INSERT INTO [Manifest](name) VALUES ('" + pack.toQM.get(i) + "')");
            Cursor c = db.rawQuery("SELECT * FROM [Manifest] WHERE name='" + pack.toQM.get(i) + "'", null);
            c.moveToFirst();
            int mid = c.getInt(0);
            for (int k = 0; k < pack.toQD.size(); k++) {
                db.rawQuery("INSERT INTO[Defaults](view, package) VALUES ('" + pack.toQD.get(i) + "', " + mid + ")", null);
            }
            for (int j = 0; j < pack.toQQ.size(); j++) {
                Quest q = pack.toQQ.get(j);
                db.execSQL("INSERT INTO [Quests](quest, package, name) VALUES ('" + q.getQuest() + "', " + mid + ", '" + q.getName() + "')");
                Cursor c1 = db.rawQuery("SELECT * FROM [Quests] WHERE name='" + q.getName() + "'", null);
                c1.moveToFirst();
                Log.i("c1 length",""+c1.getCount());
                int qid = c1.getInt(0);
                db.execSQL("INSERT INTO [Formulas](view, quest) VALUES ('" + q.getFormula() + "', " + qid + ")");
                for (int k = 0; k < q.getValues().size(); k++) {
                    Value v = q.getValues().get(k);
                    db.execSQL("INSERT INTO [Values](type, value, descript, quest) VALUES ('" + v.getType() + "', " + v.getValue() + ", '" + v.getDescript() + "', " + qid+")");
                }
                db.execSQL("INSERT INTO [Targets](target, quest) VALUES ('" + q.getTarget() + "', " + qid + ")");
            }
        }
        db.close();
    }
}
