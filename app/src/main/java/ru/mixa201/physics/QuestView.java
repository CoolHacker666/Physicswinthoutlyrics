package ru.mixa201.physics;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

public class QuestView extends AppCompatActivity {
    private Button b;
    private TextView z;
    private String DATABASE;
    private ListView vv;
    private ListView sv;
    private String quest;
    private String pack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_view);
        setTitle("Задачник");
        Intent i=getIntent();
        b=(Button)findViewById(R.id.btStart);
        z=(TextView)findViewById(R.id.tvQuest);
        vv=(ListView)findViewById(R.id.valuesView);
        sv=(ListView)findViewById(R.id.searchingView);
        DATABASE=i.getStringExtra("DATABASE");
        SQLiteDatabase db=openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
        String zz=i.getStringExtra("QUEST");
        pack=i.getStringExtra("PACKAGE");
        Log.i("QUERY",zz+" "+pack);
        Cursor rs=db.rawQuery("SELECT * FROM [Quests] WHERE name='" + zz + "' AND package=" + pack, null);
        rs.moveToFirst();
        zz=""+rs.getInt(0);
        Log.i("zz",zz);
        quest=zz;
        rs=db.rawQuery("SELECT * FROM [Quests] WHERE id="+zz,null);
        rs.moveToFirst();
        z.setText(rs.getString(1));
        rs=db.rawQuery("SELECT * FROM [Values] WHERE quest="+zz+" AND NOT type='answer'",null);
        rs.move(0);
        Vector<String> v=new Vector<String>();
        while (rs.moveToNext()){
            v.add(rs.getString(1)+" = "+rs.getDouble(2)+" "+rs.getString(3));
        }
        String[] a=new String[v.size()];
        for(int ii=0;ii<a.length;ii++){
            a[ii]=v.get(ii);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        vv.setAdapter(adapter);
        rs=db.rawQuery("SELECT * FROM [Targets] WHERE quest="+zz,null);
        rs.move(0);
        v.clear();
        while (rs.moveToNext()){
            v.add(rs.getString(1)+" - ?");
        }
        a=new String[v.size()];
        for(int ii=0;ii<a.length;ii++){
            a[ii]=v.get(ii);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        sv.setAdapter(adapter);
        db.close();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), QuestStepper.class);
                i.putExtra("DATABASE",DATABASE);
                i.putExtra("QUEST",quest);
                startActivity(i);
            }
        });
    }

}
