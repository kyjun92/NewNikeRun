package com.cookandroid.newnike;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NikeActivity1 extends AppCompatActivity {
    // ========================================================================================2
    Chronometer ch;
    Button start, end;
    LinearLayout resultLayout;
    ProgressBar pb;
    String selectedGu;
    int counter;
    String timeGoal;
    private long timeWhenStopped = 0;
    int stoppedMilliseconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nike1);
        ch = findViewById(R.id.ch);
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        timeGoal = intent.getStringExtra("timeGoal");
        Log.d(">>>>>>>>>>>>>>", ""+timeGoal);
        selectedGu = intent1.getStringExtra("result1");
        Log.d(this.getClass().getName(),(String)ch.getText());
        if(selectedGu == null){
            ch.setText("00:00");
        }else{
            ch.setText(selectedGu);
            String chronoText1 = selectedGu;
            String array1[] = chronoText1.split(":");
            counter = (Integer.parseInt(array1[0]) * 60 * 1000
                    + Integer.parseInt(array1[1].trim()) * 1000) / 100;
        }
        pb = (ProgressBar)findViewById(R.id.progressBar);

// ========================================================================================2

        final ToggleButton tb = this.findViewById(R.id.toggleButton);
        tb.setOnClickListener(new View.OnClickListener() {
               private void showElapsedTime() {
                long elapsedMillis = SystemClock.elapsedRealtime() - ch.getBase();
             //   Toast.makeText(ChronoExample.this, "Elapsed milliseconds: " + elapsedMillis,
              //          Toast.LENGTH_SHORT).show();

            }


            // ========================================================================================3
            @Override
            public void onClick(View v) {
                   //파라메타 보내기
                Intent intent =new Intent(getApplicationContext(), NikeActivity2.class);
                //"result"  , 보낼값
                intent.putExtra("result", (String)ch.getText());
                intent.putExtra("timeGoal", timeGoal);
                final Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run()
                    {
                        counter++;
                        pb.setProgress(counter);

                        if(counter > Integer.parseInt(timeGoal) / 100){
                            t.cancel();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(NikeActivity1.this);
//                            builder.setTitle("목표달성").setMessage("계속 달리시겠습니까?");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//                                @Override
//                                public void onClick(DialogInterface dialog, int id)
//                                {
//                                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
//                                    Intent intent3 = new Intent(getApplicationContext(), NikeActivity2.class);
//                                    intent3.putExtra("result", (String)ch.getText());
//                                    startActivity(intent3);
//                                }
//                            });
//
//                            AlertDialog alertDialog = builder.create();
//
//                            alertDialog.show();
                        }
                    }
                };
                t.schedule(tt,0,Integer.parseInt(timeGoal) / 100);


                Toast.makeText(getApplicationContext(), ch.getText(), Toast.LENGTH_SHORT).show();
                String chronoText = ch.getText().toString();
                String array[] = chronoText.split(":");
                if (array.length == 2) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                            + Integer.parseInt(array[1].trim()) * 1000;
                } else if (array.length == 3) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                           + Integer.parseInt(array[1]) * 60 * 1000
                           + Integer.parseInt(array[2]) * 1000;
                }

                // ========================================================================================3
                //ch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
               // ch.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds + timeWhenStopped);
               // ch.start();
                if(tb.isChecked()){
                    // Set the initial value
                    //ch.setBase(SystemClock.elapsedRealtime());
                    ch.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds + timeWhenStopped);
                    ch.start();
                    Log.d("크로메터 상활 : ", "시작됨!!!!");
                }else{
                    startActivity(intent);
                    timeWhenStopped = ch.getBase() - SystemClock.elapsedRealtime();
                    ch.stop();
                    Toast.makeText(getApplicationContext(), ch.getText(), Toast.LENGTH_SHORT).show();
                }
            }



        });



    }
}

