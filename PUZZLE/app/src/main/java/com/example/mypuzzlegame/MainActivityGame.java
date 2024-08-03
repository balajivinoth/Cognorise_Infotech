package com.example.mypuzzlegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;


import java.util.ArrayList;
import java.util.Collections;


public class MainActivityGame extends AppCompatActivity {


    TextView[][] views = new TextView[3][3];
    ArrayList<Integer> numbers = new ArrayList<>(9);
    int indexI = 0;
    int indexJ = 0;
    private static int movesCount;
    private Chronometer chronometer;
    private boolean isChronometerRunning;
    private TextView textView_count;
    private MyPair myPair;
    private ImageView btn_finish;
    private int playbackPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        textView_count = findViewById(R.id.txtMovesCounter);
        chronometer = findViewById(R.id.timer_textview);
        btn_finish = findViewById(R.id.btn_finish3);
        movesCount = 0;
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getInt("playbackPosition", 0);
        }








        findViewById(R.id.reload).setOnClickListener(v -> {

            movesCount = 0;
            Collections.shuffle(numbers);
            views[indexI][indexJ].setVisibility(View.VISIBLE);
            //loadViews();
            //loadRandomNumbers();
            describeNumbers();
            chronometer.setFormat(null);
            chronometer.start();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%s");
        });


        btn_finish.setOnClickListener(v -> finish());

        loadViews();
        loadRandomNumbers();
        describeNumbers();
        startChronometer();


        if (savedInstanceState != null) {
            movesCount = savedInstanceState.getInt("count");
            textView_count.setText(String.valueOf(movesCount));
        }
    }


    private void loadViews() {
        RelativeLayout relativeLayout = findViewById(R.id.relative);

        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            views[i / 3][i % 3] = (TextView) relativeLayout.getChildAt(i);

            views[i / 3][i % 3].setTag(new MyPair(i / 3, i % 3));

            views[i / 3][i % 3].setOnClickListener(v -> {
                MyPair data = (MyPair) v.getTag();
                canPermit(data.i, data.j);
            });
        }
    }

    private void loadRandomNumbers() {
        for (int i = 0; i < 9; i++) {
            numbers.add(i);
        }

        do {
            Collections.shuffle(numbers);
            findXposition();
        } while (!isSolved());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("count", movesCount);
        long delTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        outState.putLong("time", delTime);
        ArrayList<String> list = new ArrayList<>();
        outState.putBoolean("isChronometerRunning", isChronometerRunning);
        super.onSaveInstanceState(outState);
        RelativeLayout relativeLayout = findViewById(R.id.relative);
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            TextView textView = (TextView) relativeLayout.getChildAt(i);
            list.add(textView.getText().toString());
        }
        outState.putStringArrayList("buttons", list);
        outState.putInt("playbackPosition", playbackPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> list = savedInstanceState.getStringArrayList("buttons");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                views[i / 3][i % 3].setVisibility(View.INVISIBLE);
                views[i / 3][i % 3].setText("");

                indexI = i / 3;
                indexJ = i % 3;

            } else {
                views[i / 3][i % 3].setVisibility(View.VISIBLE);
                views[i / 3][i % 3].setText(list.get(i));
            }
        }
        long delTime = savedInstanceState.getLong("time", 0);
        chronometer.setBase(SystemClock.elapsedRealtime() + delTime);
        ArrayList<String> list1 = savedInstanceState.getStringArrayList("buttons");
        loadSavedNumbers(list1);
    }

    private void loadSavedNumbers(ArrayList<String> numbers) {

        textView_count.setText(String.valueOf(movesCount));
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals("")) {
                myPair = new MyPair(i % 3, i / 3);
            }
            views[i / 3][i % 3].setText(numbers.get(i));
        }
    }

    private void startChronometer() {
        if (!isChronometerRunning) {
            chronometer.start();
            isChronometerRunning = true;
        }
    }



    private void findXposition() {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 0) {
                indexI = i / 3;
                indexJ = i % 3;
            }
        }
    }

    private boolean isSolved() {
        int inversion_count = 0;

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 0) continue;
            for (int j = i + 1; j < numbers.size(); j++) {
                if (Integer.parseInt(numbers.get(j).toString()) > Integer.parseInt(numbers.get(i).toString())) {
                    inversion_count++;
                }
            }
        }

        return (inversion_count % 2 == 1) || ((3 - indexI) % 2 == 1);
    }

    private void describeNumbers() {
        Collections.shuffle(numbers);
        int count = 0;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size() - 1; j++) {
                if (numbers.get(i) > numbers.get(j)) count++;
            }
        }
        if (!((count % 2 == 0 && (3 - indexI) % 2 == 1) || (count % 2 == 1 && (3 - indexI) % 2 == 0)))
            describeNumbers();

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 0) {
                views[i / 3][i % 3].setVisibility(View.INVISIBLE);
                indexJ = i % 3;
                indexI = i / 3;
            } else views[i / 3][i % 3].setText(String.valueOf(numbers.get(i)));
        }
        textView_count.setText(String.valueOf(movesCount));
    }


    private void canPermit(int i, int j) {
        movesCount++;


        TextView movesCounterTextView = findViewById(R.id.txtMovesCounter);
        movesCounterTextView.setText(String.valueOf(movesCount));

        //click.start();
        boolean bool = (Math.abs(indexI - i) == 1 && indexJ == j) || (Math.abs(indexJ - j) == 1 && indexI == i);
        //click.setLooping(true);
        if (bool) {
            //userCoins += 1;
            views[indexI][indexJ].setVisibility(View.VISIBLE);
            views[indexI][indexJ].setText(views[i][j].getText());
            views[i][j].setVisibility(View.INVISIBLE);
            views[i][j].setText("");
            indexI = i;
            indexJ = j;
            isWin();
        }
    }

    private void isWin() {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            if (views[i / 3][i % 3].getText().equals(String.valueOf(i + 1))) {
                count++;
            }
        }
        if (count == 8) {
            PopupDialog.getInstance(this)
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Victory Achieved")
                    .setDescription("Congratulations, your skillful strategy and quick thinking have led you to a well-deserved victory!")
                    .setCancelable(false)
                    .setDismissButtonText("Next")
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            startActivity(new Intent(MainActivityGame.this, MainActivity.class));
                            finish();
                            super.onDismissClicked(dialog);
                        }
                    });
        }
    }


}