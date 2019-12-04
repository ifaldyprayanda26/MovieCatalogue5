package com.apps.ifaldyprayanda.moviecatalogue3;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    int timeLoad = 5000; // untuk mengatur waktu saat splashscreen
    TextView teksSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        teksSplash = findViewById(R.id.textView);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/Kavoon-Regular.ttf"); // perintah untuk mengubah font pada splash screen
        teksSplash.setTypeface(customfont);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {     // perintah untuk menjalankan splash screen
                //setelah loading maka akan langsung berpindah ke StartActivity
                Intent splash = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(splash);
                finish();

            }
        }, timeLoad);
    }
}
