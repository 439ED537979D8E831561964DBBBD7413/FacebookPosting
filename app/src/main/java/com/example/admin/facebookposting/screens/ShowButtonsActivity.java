package com.example.admin.facebookposting.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.facebookposting.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ShowButtonsActivity extends AppCompatActivity {

    Button picture;
    Button multiplePicture;
    Button friendList;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_show_buttons);

        callbackManager= CallbackManager.Factory.create();

        picture = (Button)findViewById(R.id.picture);
        multiplePicture = (Button)findViewById(R.id.multiplePicture);
        friendList = (Button)findViewById(R.id.friendlist);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());

                builder.setTitle("");
                builder.setMessage("Welcome to AndroidHive.info");
                builder.setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,1);
                    }
                });
                builder.setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,2);
                    }
                });
                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();
            }
        });

        multiplePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        friendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null) {
            Bitmap bm = (Bitmap)data.getExtras().get("data");
            //imageView.setImageBitmap(bm);
            dialogToPost(bm);
        }
        if(requestCode==2 && resultCode==RESULT_OK && data!=null) {
            Uri uri=data.getData();
            Bitmap bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                dialogToPost(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }//imageView.setImageBitmap(bm);
        }
    }

    private void dialogToPost(Bitmap bm) {
        final Bitmap bitmap = bm;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.picture_from_device);
        dialog.setTitle("Post to facebook");
        ImageView imgeView = (ImageView)dialog.findViewById(R.id.img);
        Button button = (Button)dialog.findViewById(R.id.post);
        imgeView.setImageBitmap(bitmap);

        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToWall(bitmap);
                dialog.dismiss();
            }
        });
    }

    private void postToWall(Bitmap bitmap) {

        AccessToken at = AccessToken.getCurrentAccessToken();
        if(at != null){
            //Bitmap bitmapimg = BitmapFactory.decodeFile(camImg);

            ByteArrayOutputStream blob=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,blob);

            byte[] bitmapdata=blob.toByteArray();
            String path = "me/photos";

            Bundle parameters = new Bundle();
          //  parameters.putString("message", status);
            parameters. putByteArray ("source", bitmapdata);

            GraphRequest.Callback cb = new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    if(response.getError()==null){
                        Toast.makeText(ShowButtonsActivity.this, "Successfully posted to Facebook", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("error",response.getError().toString());
                        Toast.makeText(ShowButtonsActivity.this, "Facebook: There was an error, Please Try Again", Toast.LENGTH_SHORT).show();

                    }
                }
            };
            Toast.makeText(this, "Uploading image", Toast.LENGTH_SHORT).show();

            GraphRequest request = new GraphRequest(at, path, parameters, HttpMethod.POST, cb);
            GraphRequestAsyncTask asynTaskGraphRequest = new GraphRequestAsyncTask (request);
            asynTaskGraphRequest.execute();
        }
        else {
            Toast.makeText(this, "not logedin", Toast.LENGTH_SHORT).show();
        }
    }

}
