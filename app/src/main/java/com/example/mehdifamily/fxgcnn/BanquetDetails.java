package com.example.mehdifamily.fxgcnn;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BanquetDetails extends AppCompatActivity {

    TextView textbanName;
    TextView textownName;
    TextView textownContact;
    TextView textbanAddress;
    TextView textbanCapacity;

    ViewPager viewPager;
    private Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banquet_details);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        textbanName = (TextView)findViewById( R.id.banName);
        textownName =(TextView)findViewById( R.id.textView2 );
        textownContact= (TextView)findViewById( R.id.textView4 );
        textbanAddress=(TextView)findViewById( R.id.textView6 );
        textbanCapacity=(TextView)findViewById( R.id.textView8 );

        Intent intent = getIntent();

        Button book = (Button)findViewById(R.id.btnBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BanquetDetails.this, BookForm.class);
                startActivity(intent);
            }
        });
    }
}
