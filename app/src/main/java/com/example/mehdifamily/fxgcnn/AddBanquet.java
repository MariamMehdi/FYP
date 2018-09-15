package com.example.mehdifamily.fxgcnn;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class AddBanquet extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText BanquetName, OwnerName, OwnerContact, editTextLatitude,editTextLongitude, Capacity;
    private Button AddBanquet,banPicture;
    private AwesomeValidation awesomeValidation;
    private Uri imageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase,ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banquet);

        mStorageRef = FirebaseStorage.getInstance().getReference("Banquet");
        mDatabase = FirebaseDatabase.getInstance().getReference("Banquet");
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        BanquetName = (EditText) findViewById(R.id.bname);
        OwnerName = (EditText) findViewById(R.id.owname);
        OwnerContact = (EditText) findViewById(R.id.owncontact);
        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        Capacity = (EditText) findViewById(R.id.bancapacity);
        banPicture = (Button)findViewById( R.id.banPicture );
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        AddBanquet = (Button) findViewById(R.id.addbanquet);

        Spinner sp = (Spinner) findViewById(R.id.spin);

        ref = FirebaseDatabase.getInstance().getReference("Budget").child("Ranges");

        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, retrieve()));


        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.bname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.owname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.owncontact, "^03+[0-9 ]{9}", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.bancapacity, "^[0-1]{1}[0-9]{3}$", R.string.quantityerror);
        awesomeValidation.addValidation(this, R.id.editTextLatitude, "^(\\+|-)?((\\d((\\.)|\\.\\d{1,6})?)|(0*?[0-8]\\d((\\.)|\\.\\d{1,6})?)|(0*?90((\\.)|\\.0{1,6})?))$", R.string.laterror);
        awesomeValidation.addValidation(this, R.id.editTextLongitude, "^(\\+|-)?((\\d((\\.)|\\.\\d{1,6})?)|(0*?\\d\\d((\\.)|\\.\\d{1,6})?)|(0*?1[0-7]\\d((\\.)|\\.\\d{1,6})?)|(0*?180((\\.)|\\.0{1,6})?))$", R.string.longerror);

        banPicture.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();
            }
        } );

        AddBanquet.setOnClickListener(this);
    }

    public ArrayList<String> retrieve() {
        final ArrayList<String> list = new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,list);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,list);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> list) {
        list.clear();
        for (DataSnapshot ds : snapshot.getChildren()) {
            String range1 = ds.getValue(Adapter.class).getRange1();
            String range2 = ds.getValue(Adapter.class).getRange2();
            String range3 = ds.getValue(Adapter.class).getRange3();
            String range4 = ds.getValue(Adapter.class).getRange4();
            String range5 = ds.getValue(Adapter.class).getRange5();
            String range6 = ds.getValue(Adapter.class).getRange6();
            String range7 = ds.getValue(Adapter.class).getRange7();
            list.add(range1);
            list.add(range2);
            list.add(range3);
            list.add(range4);
            list.add(range5);
            list.add(range6);
            list.add(range7);
        }

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void saveUserInformation() {
        if (awesomeValidation.validate() && imageUri != null) {
            Toast.makeText(AddBanquet.this, "Saved", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,MapsActivity.class);
            startActivity(i);
            StorageReference fileReference  = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },5000);
                    String uploadId = mDatabase.push().getKey();
                    Toast.makeText(AddBanquet.this,"Upload Sucessful",Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(uploadId,BanquetName.getText().toString().trim(),
                            OwnerName.getText().toString().trim(),
                            OwnerContact.getText().toString().trim(),
                            Double.parseDouble(editTextLatitude.getText().toString().trim()),
                            Double.parseDouble(editTextLongitude.getText().toString().trim()),
                            Capacity.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    mDatabase.child(uploadId).setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBanquet.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
    }

    private  void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult(intent, PICK_IMAGE_REQUEST );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

        }
    }

    @Override
    public void onClick(View v) {
        if (v==AddBanquet){
            saveUserInformation();
        }


    }
}
