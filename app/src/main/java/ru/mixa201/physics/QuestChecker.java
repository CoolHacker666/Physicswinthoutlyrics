package ru.mixa201.physics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mixa201.physics.Questionary.Watcher;

public class QuestChecker extends AppCompatActivity {
    private TextView yfttv,yftv;
    private TextView yattv;
    private EditText yaet;
    private TextView tfttv,tftv;
    private TextView tattv,tatv;
    private TextView rttv, rtv;
    private Button bt;

    private TextView byId(int id){
        return (TextView)findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_checker);
        yfttv=byId(R.id.yfttv);
        yftv=byId(R.id.yftv);
        yattv=byId(R.id.yattv);
        yaet=(EditText)findViewById(R.id.yaet);
        bt=(Button)findViewById(R.id.btCheckNum);
        tfttv=byId(R.id.tfttv);
        tftv=byId(R.id.tftv);
        tattv=byId(R.id.tattv);
        tatv=byId(R.id.tatv);
        rttv=byId(R.id.rttv);
        rtv=byId(R.id.rtv);
        final Intent i=getIntent();
        yftv.setText(i.getStringExtra("CALC"));
        final Double tanswer=i.getDoubleExtra("RESULT",0);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yfttv.setVisibility(View.GONE);
                yftv.setVisibility(View.GONE);
                yattv.setVisibility(View.GONE);
                yaet.setVisibility(View.GONE);
                bt.setVisibility(View.GONE);
                tfttv.setVisibility(View.VISIBLE);
                tftv.setVisibility(View.VISIBLE);
                tattv.setVisibility(View.VISIBLE);
                tatv.setVisibility(View.VISIBLE);
                rttv.setVisibility(View.VISIBLE);
                rtv.setVisibility(View.VISIBLE);
                Double answer=Double.parseDouble(yaet.getText().toString());
                tftv.setText(i.getStringExtra("TRUE"));
                tatv.setText(""+tanswer);
                int r=0;
                if(yftv.getText().toString().equals(tftv.getText().toString())){
                    r+=3;
                }
                r+=Watcher.check(tanswer,answer);
                rtv.setText(r+"/5");
            }
        });
    }
}
