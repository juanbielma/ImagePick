package com.vogella.android.intent.imagepick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImagePickActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
        imageView = (ImageView) findViewById(R.id.result);
    }

    public void pickImage(View view)
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream inputStream = null;
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK);
            try{

                //recyle unused bitmaps
                if(bitmap != null)
                    bitmap.recycle();

                inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }finally {
                if(inputStream != null)
                    try {
                        inputStream.close();
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }


    }
}
