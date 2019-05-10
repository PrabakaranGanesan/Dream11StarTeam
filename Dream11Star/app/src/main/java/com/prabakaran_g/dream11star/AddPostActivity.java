package com.prabakaran_g.dream11star;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPostActivity extends AppCompatActivity {




    private Toolbar matchToolbar;


    EditText mTitleEt, mDescrEt;
    ImageView mPostIv;
    Button mUploadBtn;

  //  Button mShowNotificationButton;
  /*  NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent mResultPendingIntent;
    TaskStackBuilder mTaskStackBuilder;
    Intent mResultIntent;

    */


    String mStoragePath = "All_Image_Uploads/";

    String mDatabasePath = "Data";

    Uri mFilePathUri;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        matchToolbar = (Toolbar)findViewById(R.id.addpost_page_toolbar);
        setSupportActionBar(matchToolbar);
        getSupportActionBar().setTitle("Dream11Star Teams");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);




        mTitleEt = findViewById(R.id.pTitleEt);
        mDescrEt = findViewById(R.id.pDescrEt);
        mPostIv = findViewById(R.id.pImageIv);
        mUploadBtn = findViewById(R.id.pUploadBtn);

   //    mShowNotificationButton = findViewById(R.id.btnShowNotification);
    /*    mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_action_dollar);
        mBuilder.setContentTitle("Dream11 Star");
        mBuilder.setContentText("!!! New Dream11 Star Team Available.. Click this !!! ");
        mResultIntent = new Intent(this,MainActivity.class);
        mTaskStackBuilder = TaskStackBuilder.create(this);
        mTaskStackBuilder.addParentStack(AddPostActivity.this);
        mTaskStackBuilder.addNextIntent(mResultIntent);
        mResultPendingIntent = mTaskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(mResultPendingIntent);
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        mShowNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationManager.notify(1, mBuilder.build());
            }
        });

*/





        mPostIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_REQUEST_CODE);

            }
        });



        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uploadDataToFirebase();

            }
        });



        mStorageReference = FirebaseStorage.getInstance().getReference(mStoragePath);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        mProgressDialog = new ProgressDialog(AddPostActivity.this);

    }




    private void uploadDataToFirebase()
    {
        if(mFilePathUri != null){
            mProgressDialog.setTitle("Uploading...");
            mProgressDialog.show();

            StorageReference storageReference2nd = mStorageReference.child(mStoragePath + System.currentTimeMillis()+ "." + getFileExtension(mFilePathUri));

            storageReference2nd.putFile(mFilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadUri = uriTask.getResult();



                            String mPostTitle = mTitleEt.getText().toString().trim();
                            String mPostDescr = mDescrEt.getText().toString().trim();
                            mProgressDialog.dismiss();
                            Toast.makeText(AddPostActivity.this, "uploaded successfully...", Toast.LENGTH_SHORT).show();
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(mPostTitle, mPostDescr, downloadUri.toString(), mPostTitle.toLowerCase());
                            String imageUploadId = mDatabaseReference.push().getKey();

                            mDatabaseReference.child(imageUploadId).setValue(imageUploadInfo);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();

                            Toast.makeText(AddPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            mProgressDialog.setTitle("Uploading...");

                        }
                    });




        }

        else
        {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
        }


    }




    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){

            mFilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);

                mPostIv.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }





        }


    }




}
