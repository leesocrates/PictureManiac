package com.lee.pictureemaniac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lee.pictureemaniac.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    int action_image_requestcode = 1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(action_image_requestcode);
            }
        });
    }

    private void takePhoto(int ACTION_IMAGE_CAPTURE) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
        startActivityForResult(intent, ACTION_IMAGE_CAPTURE);
    }

    public static Uri getImageUri() {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/pictureemaniac");
        if (!file.exists()) {
            file.mkdirs();
        }
        File imageFile = new File(file, "/test.jpg");
        Uri imgUri = Uri.fromFile(imageFile);
        return imgUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == action_image_requestcode) {
//                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(getImageUri().getPath()), imageView);
//                imageView.setImageBitmap(getCompressImageFromFile(getImageUri().getPath()));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = getCompressImageFromFile(getImageUri().getPath());
        Utils.logE("filepath is : " + getImageUri().getPath());
        if (bitmap != null) {
            Utils.logE("onresume bitmap is not null");
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        } else {
            Utils.logE("onresume bitmap is null");
        }
//        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(getImageUri().getPath()), imageView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Utils.logE("onConfigurationChanged");
    }

    private Bitmap getCompressImageFromFile(String srcPath) {
        Utils.logE("getCompressImageFromFile start : " + System.currentTimeMillis());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        Utils.logE("bitmap width height is : "+newOpts.outWidth +" ,  "+newOpts.outHeight);

        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = 2;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;

//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Utils.logE("getCompressImageFromFile end : " + System.currentTimeMillis());
//        bitmap2Bytes(bitmap);
//        Utils.logE("bitmap new width height is : "+bitmap.getWidth() +" , "+bitmap.getHeight());
        return bitmap;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        byte[] buffer;
        Utils.logE("bitmap2Bytes start : " + System.currentTimeMillis());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.logE("bitmap2Bytes compress completed " + System.currentTimeMillis());
        buffer = baos.toByteArray();
        Utils.logE("bitmap2Bytes toByteArray() done " + System.currentTimeMillis() + " byte[] size is : " + buffer.length);
        return buffer;
    }

}
