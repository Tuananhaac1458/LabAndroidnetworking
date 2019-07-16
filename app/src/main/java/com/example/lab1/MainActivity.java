package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoadImageTask.Listner {

    Button btnb1, btnb3,btnl3;
    TextView tvmes;
    ImageView img;
private Bitmap bitmap = null;
private ProgressDialog prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnb1 = findViewById(R.id.btnb1);
        btnb3 = findViewById(R.id.btnb3);
        btnl3 = findViewById(R.id.btnl3);
        tvmes = findViewById(R.id.tvmes);
        img = findViewById(R.id.img);
        btnb1.setOnClickListener(this);

        btnb3.setOnClickListener(this);
        btnl3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnb1:

                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final  Bitmap bitmap = loadimg("https://www.pngfind.com/pngs/m/498-4982044_natsu-lightning-flame-dragon-mode-quotes-natsu-dragneel.png");
                        img.post(new Runnable() {
                            @Override
                            public void run() {
                                tvmes.setText("Image");
                                img.setImageBitmap(bitmap);
                            }
                        });
                    }
                });

                thread.start();

                break;

            case R.id.btnb3:
                prog = ProgressDialog.show(MainActivity.this,"","Downloading...");
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        bitmap = downlaodimg("https://i.pinimg.com/originals/99/59/1b/99591bbc318ecd41ae7bbf39a9106e12.jpg");
                        Message mgs = handlermess.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("aa","Img downing");
                        mgs.setData(bundle);
                        handlermess.sendMessage(mgs);
                     //   handlermess.handleMessage();
                    }
                };
                Thread aThread = new Thread(runnable);
                aThread.start();
                break;
            case R.id.btnl3:
                new LoadImageTask((LoadImageTask.Listner) this,this).execute("https://i.pinimg.com/736x/33/44/f7/3344f7435d4fe7001870bb23dbf07b94.jpg");

                break;
        }
    }


    private Bitmap downlaodimg(String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap loadimg(String link){
        URL url;
        Bitmap bitmap = null;
        try{
           url = new URL(link);
           bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Handler handlermess = new Handler(){
        public void handleMessage (Message mes){
            super.handleMessage(mes);
            Bundle bundle = mes.getData();
            String messs = bundle.getString("aa");
            tvmes.setText(messs);
            img.setImageBitmap(bitmap);
            prog.dismiss();
        }
    };


    @Override
    public void OnImageLoader(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
        tvmes.setText("Image Dowloaded");
    }

    @Override
    public void onError() {
        tvmes.setText("Err");
    }
}
