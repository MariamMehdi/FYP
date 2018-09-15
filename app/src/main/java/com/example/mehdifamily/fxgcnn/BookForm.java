package com.example.mehdifamily.fxgcnn;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BookForm extends AppCompatActivity implements View.OnClickListener {

    private Button dateBtn,cBooking;
    Calendar c;
    DatePickerDialog dpd;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private EditText clientname,persons,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);
        mDatabase = FirebaseDatabase.getInstance().getReference("Bookings");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Reserved Date");
        cBooking = (Button) findViewById(R.id.book);
        clientname = (EditText)findViewById(R.id.cliname);
        persons = (EditText)findViewById(R.id.person_no);
        contact = (EditText)findViewById(R.id.contact_no);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cnfrm = (Button) findViewById(R.id.book);
        cnfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookForm.this, "Booking Completed", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BookForm.this, MapsActivity.class);
                startActivity(i);
            }
        });

        cBooking.setOnClickListener(this);
        dateBtn = (Button)findViewById(R.id.date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(BookForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        dateBtn.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                },day ,month, year);
                dpd.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void saveBookingInfo() {
            String date = dateBtn.getText().toString().trim();
            String cliname = clientname.getText().toString().trim();
            String personnum = persons.getText().toString().trim();
            String contactnum = contact.getText().toString().trim();
            BanquetInfo banquetInfo = new BanquetInfo(date,cliname,personnum,contactnum);
            Reserved_Date rsrvd = new Reserved_Date(date);
            mDatabase.push().setValue(banquetInfo);
            mDatabase2.push().setValue(rsrvd);
            Toast.makeText(BookForm.this, "Your request has been submitted.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,MapsActivity.class);
            startActivity(i);
        }

    @Override
    public void onClick(View v) {
        if (v==cBooking){
            saveBookingInfo();
        }
    }
}
