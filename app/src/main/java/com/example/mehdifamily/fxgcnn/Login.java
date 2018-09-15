package com.example.mehdifamily.fxgcnn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private  EditText email, password;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), Home.class));
        }


        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC );

        email = (EditText) findViewById( R.id.myemail );
        password = (EditText) findViewById( R.id.mypassword );
        login = (Button) findViewById( R.id.login );
        textViewSignup  = (TextView) findViewById(R.id.reg);

        progressDialog = new ProgressDialog( this );

        awesomeValidation.addValidation( this, R.id.myemail, Patterns.EMAIL_ADDRESS, R.string.emailerror );
        awesomeValidation.addValidation( this, R.id.mypassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror );


        login.setOnClickListener( this );
        textViewSignup.setOnClickListener( this );
    }

    private void userlogin() {
        String myemail = email.getText().toString().trim();
        String mypassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(myemail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(mypassword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Verifying User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(myemail, mypassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else{
                            //display some message here
                            Toast.makeText(Login.this,"Could not login. Please try again.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            userlogin();
        }

        if(v == textViewSignup){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}