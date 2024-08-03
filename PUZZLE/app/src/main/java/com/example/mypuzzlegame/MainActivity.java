package com.example.mypuzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageView1;
    private Handler handler = new Handler();
    private ImageView infoView;


    private Animation shrinkAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.button);
        imageView1 = findViewById(R.id.imgPuzzle);
        infoView = findViewById(R.id.infoButton);
        shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.shrink_animation);



        infoView.setOnClickListener(v -> {

            infoView.startAnimation(shrinkAnimation);
            startActivity(new Intent(this, MainActivityHelp.class));
        });

        imageView.setOnClickListener(v -> {

            imageView.startAnimation(shrinkAnimation);
            Intent intent = new Intent(MainActivity.this, MainActivityGame.class);
            startActivity(intent);
        });

        startAnimationLoop();
    }

    private void startAnimationLoop() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        imageView1.startAnimation(fadeInAnimation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView1.startAnimation(fadeOutAnimation);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView1.startAnimation(fadeInAnimation);
                        startAnimationLoop();
                    }
                }, fadeOutAnimation.getDuration());
            }
        }, fadeInAnimation.getDuration());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}