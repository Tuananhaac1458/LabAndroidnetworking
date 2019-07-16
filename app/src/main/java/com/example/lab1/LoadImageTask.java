package com.example.lab1;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Bitmap bitmap = null;

    interface Listner{
        void OnImageLoader(Bitmap bitmap);
        void onError();
    }

    private Listner mListner;
    private ProgressDialog progressDialog;
    public LoadImageTask(Listner listenr, Context context){
        mListner = listenr;
        progressDialog = new ProgressDialog(context);
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
                return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
           // bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        }catch (IOException e ){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage("Download img");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }if(bitmap != null){
            mListner.OnImageLoader(bitmap);
        }else {
            mListner.onError();
        }
    }

}