package ru.mixa201.physics;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

import ru.mixa201.physics.Questionary.Watcher;

public class QuestStepper extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary lib;
    private GestureOverlayView gestures;
    private int stage;
    private int formula;
    private int fc;
    private String input="";
    private Vector<String> formulas;
    private SQLiteDatabase db;
    private String quest;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_stepper);
        formulas=new Vector<String>();
        tv=(TextView)findViewById(R.id.uFtv);
        Intent intent=getIntent();
        String database=intent.getStringExtra("DATABASE");
        quest=intent.getStringExtra("QUEST");
        db=openOrCreateDatabase(database, MODE_PRIVATE, null);
        if(stage==0) {
            stage=1;
            Cursor rs = db.rawQuery("SELECT * FROM [Formulas] WHERE quest="+quest,null);
            formula=0;
            fc=rs.getCount();
            rs.move(0);
            while (rs.moveToNext()){
                formulas.add(rs.getString(1));
            }
            setTitle("Введите формулу "+formula+"/"+fc);
        }
        lib= GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!lib.load()){
            finish();
        }
        gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
        //Cursor rs1=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
        //Cursor rs2=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
        //Watcher.equal("S=Vt","S=Vt",rs1,rs2);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> a=lib.recognize(gesture);
        if (a.size() > 0) {
            Prediction prediction = a.get(0);
            if (prediction.score > 1.0) {
                if(prediction.name.equals("end")){
                    if(stage==1){
                        Log.i("Gesture", "Parsing formula...");
                        //Toast t=Toast.makeText(getApplicationContext(),"Checking...",Toast.LENGTH_SHORT);
                        //t.show();
                        for(int i=0;i<formulas.size();i++){
                            Cursor rs1=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
                            Cursor rs2=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
                            Log.i("Gesture","Equaling: "+input+" and "+formulas.get(i));
                            if(Watcher.equal(formulas.get(i), input,rs1,rs2)){
                                Log.i("Gesture","OK");
                                formula++;
                                //input="";
                                //setTitle("Введите формулу " + formula + "/" + fc);
                                Intent ii=new Intent();
                                ii.setClass(getApplicationContext(),QuestChecker.class);
                                Cursor rs=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
                                ii.putExtra("CALC", Watcher.toNumForm(input, rs));
                                rs=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest,null);
                                ii.putExtra("TRUE", Watcher.toNumForm(formulas.firstElement(), rs));
                                rs=db.rawQuery("SELECT * FROM [Values] WHERE quest="+quest+" AND type='answer'",null);
                                rs.moveToFirst();
                                ii.putExtra("RESULT",rs.getDouble(2));
                                startActivity(ii);
                                finish();
                            }
                        }
                        if(formula==fc){

                        }
                    }
                }
                else if(prediction.name.equals("clear")){
                    input="";
                }
                else{
                    input+=prediction.name;
                }
                tv.setText(input);
            }
        }
    }
}
