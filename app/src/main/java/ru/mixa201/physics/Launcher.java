package ru.mixa201.physics;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import ru.mixa201.physics.classes.Manifest;
import ru.mixa201.physics.classes.Test;

public class Launcher extends AppCompatActivity {
    private Button bTest;
    private Button bLearn;
    private Button bControl;
    private Button bQuest;
    private ImageView ivE;
    private SoundPool sp;
    private int sound;
    private Vibrator vibrator;
    public static String APP_PATH= Environment.getExternalStorageDirectory().getPath()+"/android/data/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Package", APP_PATH);
        try {
            File ff=new File(APP_PATH+"ru.mixa201.physics/");
            APP_PATH+="ru.mixa201.physics/";
            ff.mkdirs();
            ff=new File(APP_PATH+"tests");
            ff.mkdirs();
            ff=new File(APP_PATH+"pages");
            ff.mkdirs();
            ff=new File(APP_PATH+"media");
            ff.mkdirs();
        } catch (Exception e) {
            Log.w("WRITING",e.toString());
        }
        Log.i("Package", APP_PATH);
        try {
            File f=new File(APP_PATH+"packages.pmf");
            Log.i("Package",APP_PATH+"packages.pmf");
            f.createNewFile();
            /*ObjectOutputStream w=new ObjectOutputStream(new FileOutputStream(f));
            Manifest m=new Manifest();
            m.registerFile("test");
            w.writeObject(m);
            w.close();
            f=new File(APP_PATH+"tests/test/main.pmf");
            f.createNewFile();
            w=new ObjectOutputStream(new FileOutputStream(f));
             m=new Manifest();
            m.registerFile("test_1");
            w.writeObject(m);
            w.close();
            f=new File(APP_PATH+"pages/test/main.pmf");
            w=new ObjectOutputStream(new FileOutputStream(f));
            m=new Manifest();
            m.registerFile("page_1");
            w.writeObject(m);
            w.close();
            f=new File(APP_PATH+"tests/test/test_1.ptf");
            f.createNewFile();
            w=new ObjectOutputStream(new FileOutputStream(f));
            String[] quests={"1","2","3","4","5","6","7","8","9","10"};
            String[] answers=new String[50];
            for(int i=0;i<50;i++){
                answers[i]="A"+i%5;
            }
            byte[] tans = {1,1,1,1,1,1,1,1,1,1};
            boolean[] isTexted=new boolean[10];
            String[] textedAns=new String[10];
            textedAns[0]="A";
            isTexted[0]=true;
            //isTexted[2]=true;
            textedAns[2]="B";
            String[] tags=new String[10];
            Test t=new Test("ТЕСТИРОВАНИЕ");
            t.createCustom((byte)10,(byte)5,(byte)1,quests,answers,tans,tags,isTexted,textedAns);
            w.writeObject(t);
            w.close();*/
        }
        catch(Exception e){

        }
        setContentView(R.layout.activity_launcher);
        bTest=(Button)findViewById(R.id.btT);
        bLearn=(Button)findViewById(R.id.btL);
        bControl=(Button)findViewById(R.id.btC);
        bQuest=(Button)findViewById(R.id.btQ);
        ivE=(ImageView)findViewById(R.id.ivE);
        sp=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound=sp.load(this,R.raw.electricity,1);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        bTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("MANIFEST", APP_PATH + "packages.pmf");
                i.putExtra("ACTION", "tests");
                i.setClass(getApplicationContext(), PackageView.class);
                startActivity(i);
            }
        });
        bLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("MANIFEST", APP_PATH + "packages.pmf");
                i.putExtra("ACTION", "pages");
                i.setClass(getApplicationContext(), PackageView.class);
                startActivity(i);
            }
        });
        bQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("MANIFEST", APP_PATH + "questionary.db");
                i.putExtra("ACTION","quests");
                i.setClass(getApplicationContext(), PackageView.class);
                //i.setClass(getApplicationContext(), QuestView.class);
                startActivity(i);
            }
        });
        bControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("APP_PATH",APP_PATH);
                i.setClass(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
        ivE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(sound, 1,1,0,0,1);
                vibrator.vibrate(1045);
            }
        });
    }
}
