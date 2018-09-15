package com.example.mehdifamily.fxgcnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchbyDate extends AppCompatActivity {
    DatePicker dpDate;
    TextView searched;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchby_date);
        mDatabase = FirebaseDatabase.getInstance().getReference("Reserved Date");
        dpDate = (DatePicker)findViewById(R.id.dpDate);
        searched = (TextView) findViewById(R.id.searched_date);
        // init
        // dpDate.init(2002, 10, 27, null);
    }

    public void getDate(View v) {
        StringBuilder builder=new StringBuilder();
        builder.append((dpDate.getMonth() + 1)+"/");//month is 0 based
        builder.append(dpDate.getDayOfMonth()+"/");
        builder.append(dpDate.getYear());
        searched.setText(builder.toString());
    }


}