package com.example.image_capture_tut;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class Photo extends Activity implements View.OnClickListener {

    Button btn;
    ImageView iv;
    ImageButton ib;

    Intent i;

    final static int cameraData= 0;

    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        initializer();


        @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.profpic);
        bmp = BitmapFactory.decodeStream(is);



    }

    public void initializer(){
        btn=(Button) findViewById(R.id.setWallPaper);
        iv=(ImageView) findViewById(R.id.ivReturnPic);
        ib=(ImageButton) findViewById(R.id.ibTakepic);
        btn.setOnClickListener(this);
        ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.setWallPaper:

                try {
                    getApplicationContext().setWallpaper(bmp);

                    //don't forget to add permession in manifest
                    // <uses-permission android:name="android.permission.SET_WALLPAPER"></uses-permission>

                    //IO=input output
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.ibTakepic:

                //Taking pic through the method image capture that already in the manifest and store in i intent variable
                i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                //cameraData variable will collect data
                //using startActivityForResult bcz we want to return some data
                //giving some data back
                startActivityForResult(i,cameraData);

                break;


        }

    }

    //this method allow us to get the data and we set our imageview within that method


    //resultcode=information gave it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //checking if operation is suceeded
        if (resultCode == RESULT_OK){

            //when start the camera activity and when it closes it put extras
            Bundle extras=data.getExtras();

            //setting bitmap based of those extras
            bmp=(Bitmap) extras.get("data");

            //iv for return image
            //setting image view based of bitmap
            iv.setImageBitmap(bmp);

        }


    }
}
