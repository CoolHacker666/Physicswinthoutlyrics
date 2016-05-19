package ru.mixa201.physics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import ru.mixa201.physics.classes.Clocks;
import ru.mixa201.physics.classes.Test;

/**
 * Created by Admin on 12.03.2016.
 */
public class TestView extends Activity {
    public String TEST_PATH;
    public Test test;
    Vector<CheckBox> answersC=new Vector<CheckBox>();
    Vector<EditText> answersE=new Vector<EditText>();
    Vector<View> block=new Vector<View>();
    private Button pass;
    private Button finish;
    //private Clocks clocks;
    //private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii=getIntent();
        TEST_PATH=ii.getStringExtra("PATH");
        try {
            File f=new File(TEST_PATH);
            ObjectInputStream r=new ObjectInputStream(new FileInputStream(f));
            test=(Test)r.readObject();
            r.close();
        }
        catch (Exception e){
            Toast t=Toast.makeText(getApplicationContext(),"Test reading error!", Toast.LENGTH_LONG);
            t.show();
            Log.wtf("Test error", e.toString());
            finish();
        }
        setContentView(R.layout.activity_test);
        final LinearLayout Mainlayout=(LinearLayout)findViewById(R.id.base);
        setTitle(test.name);
        //clocks=new Clocks(test.minutes,0);
        //time=new TextView(this);
        //time.setText(clocks.toString());
        //layout.addView(time);
        int s=0;
        boolean down=false;
        for(int i=0;i<test.size;i++){
            if(down){
                s--;
            }
            else {
                s++;
            }
            LinearLayout layout=new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            if(s % 7==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemViolet));
                down=true;
            }
            else if(s % 6==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemBlue));
            }
            else if(s%5==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemLightBlue));
            }
            else if(s%4==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemGreen));
            }
            else if(s%3==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemYellow));
            }
            else if(s%2==0){
                layout.setBackgroundColor(getResources().getColor(R.color.ItemOrange));
            }
            else{
                layout.setBackgroundColor(getResources().getColor(R.color.ItemRed));
                down=false;
            }

            TextView t=new TextView(this);
            t.setTextSize(20);
            t.setTypeface(Typeface.SERIF, Typeface.ITALIC);
            t.setText(test.quests[i]);
            layout.addView(t);
            if(test.isTexted[i]){
                EditText e=new EditText(this);
                answersE.add(e);
                layout.addView(answersE.lastElement());
                for(int j=0;j<test.answersCount;j++){
                    CheckBox c=new CheckBox(this);
                    c.setText(test.answers[i * test.answersCount + j]);
                    answersC.add(c);
                }
            }
            else{
                for(int j=0;j<test.answersCount;j++){
                    CheckBox c=new CheckBox(this);
                    c.setText(test.answers[i * test.answersCount + j]);
                    answersC.add(c);
                    layout.addView(answersC.lastElement());
                }
                EditText e=new EditText(this);
                answersE.add(e);
            }
            block.add(layout);
            Mainlayout.addView(layout);
        }
        pass=new Button(this);
        pass.setText("Сдать работу");
        Mainlayout.addView(pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pass.setVisibility(View.GONE);
                    int balls = 0;
                    int maxballs=0;
                    for (int i = 0; i < test.size; i++) {
                        block.get(i).setBackgroundColor(getResources().getColor(R.color.ItemRed));
                        if (!test.isTexted[i]) {
                            maxballs+=2;
                            boolean error=false;
                            boolean hastrue=false;
                            for (int j = 0; j < test.answersCount; j++) {
                                CheckBox c = answersC.get(i * test.answersCount + j);
                                c.setEnabled(false);
                                if (c.isChecked() && test.tans[i] - 1 != j) {
                                    c.setTextColor(getResources().getColor(R.color.Red));
                                    error=true;
                                } else if (test.tans[i] - 1 == j) {
                                    c.setTextColor(getResources().getColor(R.color.Green));
                                    if (c.isChecked()) {
                                        hastrue=true;
                                    }
                                }
                                answersC.set(i, c);
                            }
                            if(hastrue && !error){
                                block.get(i).setBackgroundColor(getResources().getColor(R.color.ItemGreen));
                                balls+=2;
                            }
                            else if(hastrue && error){
                                block.get(i).setBackgroundColor(getResources().getColor(R.color.ItemYellow));
                                balls++;
                            }
                        } else {
                            maxballs+=3;
                            EditText e = answersE.get(i);
                            e.setEnabled(false);
                            if (answersE.get(i).getText().toString().equalsIgnoreCase(test.textedAns[i])) {
                                e.setTextColor(getResources().getColor(R.color.Green));
                                balls += 3;
                            } else {
                                e.setTextColor(getResources().getColor(R.color.Red));
                            }
                            answersE.set(i, e);
                        }
                    }
                    TextView t=new TextView(getApplicationContext());
                    t.setText("Результат: "+balls+"/"+maxballs);
                    int part=maxballs/3;
                    if(balls<part){
                        t.setTextColor(getResources().getColor(R.color.Red));
                    }
                    if(balls>part){
                        t.setTextColor(getResources().getColor(R.color.Yellow));
                    }
                    if(balls>part*2){
                        t.setTextColor(getResources().getColor(R.color.Green));
                    }
                    if(balls==maxballs){
                        t.setText(t.getText().toString()+". Великолепно!");
                    }
                    Mainlayout.addView(t);
                    finish=new Button(getApplicationContext());
                    finish.setText("Завершить");
                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    Mainlayout.addView(finish);
                }
        });
    }
}
