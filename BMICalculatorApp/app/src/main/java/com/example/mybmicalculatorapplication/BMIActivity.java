package com.example.mybmicalculatorapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    TextView mbmidisplay,mbmicategory,mgender;
    Button mgotomain;
    Intent intent;

    ImageView mimageview;
    String mbmi;
    float intbmi;

    String height;
    String weight;

    float intheight,intweight,intheight1;

    RelativeLayout mbackground;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        getSupportActionBar().setElevation(0);
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#1E1D1D"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        ///   ColorDrawable colorDrawable2=new ColorDrawable(Color.parseColor("#1E1D1D"));
        //      getSupportActionBar().setBackgroundDrawable(colorDrawable);


        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\"></font>"));
        getSupportActionBar().setTitle("Result");


        intent=getIntent();
        mbmidisplay=findViewById(R.id.bmidisplay);
        //    magedisplay=findViewById(R.id.agedisplay);
        //    mweightdisplay=findViewById(R.id.weightdisplay);
        mbmicategory = findViewById(R.id.bmicategorydispaly);
        mgotomain=findViewById(R.id.gotomain);

        mimageview=findViewById(R.id.imageview);

        //   mheightdisplay=findViewById(R.id.heightdisplay);
        mgender=findViewById(R.id.genderdisplay);
        mbackground=findViewById(R.id.contentlayout);


        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");


        intheight=Float.parseFloat(height);
        intweight=Float.parseFloat(weight);

        intheight1=intheight/100;
        intbmi=intweight/(intheight1*intheight1);


        mbmi=Float.toString(intbmi);
        System.out.println(mbmi);

        if(intbmi<18.5)
        {
            mbmicategory.setText("underweight");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);
            //   mimageview.setBackground(colorDrawable2);
        }
        else if(intbmi<24.9 && intbmi>18.5 )
        {
            mbmicategory.setText("Healthy Weight");
            mimageview.setImageResource(R.drawable.ok);
            // mbackground.setBackgroundColor(Color.YELLOW);
            //  mimageview.setBackground(colorDrawable2);
        }
        else if(intbmi <29.9 && intbmi>25)
        {
            mbmicategory.setText("Overweight");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);
            //mimageview.setBackground(colorDrawable2);
        }
        else
        {
            mbmicategory.setText("Obese Range");
            mbackground.setBackgroundColor(R.color.warn);
            mimageview.setImageResource(R.drawable.crosss);
            //  mimageview.setBackground(colorDrawable2);
        }

        //magedisplay.setText("your age is"+intent.getStringExtra("age"));
        //mheightdisplay.setText("Your Height is "+intent.getStringExtra("height"));
        //mweightdisplay.setText("Your Weight is "+intent.getStringExtra("weight"));
        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);


        mgotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}
