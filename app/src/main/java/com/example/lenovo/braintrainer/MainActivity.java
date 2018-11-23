package com.example.lenovo.braintrainer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;
    boolean cDTIsCounting = false;

    TextView czasSekundyTW;
    TextView scoreTW;
    TextView zadanieTW;

    TextView number1TW;
    TextView number2TW;
    TextView number3TW;
    TextView number4TW;

    public int sum1, sum2, correctAnswer;


    int score, totalQuestions;
    int[] number = new int[4];
    boolean[] numberIsTaken = new boolean[4];

    long timeSciceLastClick = 0;

    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View view = this.getWindow().getDecorView();
        //view.setBackgroundColor(getResources().getColor(R.color.myYellow));

        czasSekundyTW = findViewById(R.id.textView6);
        zadanieTW = findViewById(R.id.textView7);
        scoreTW = findViewById(R.id.textView8);

        number1TW = findViewById(R.id.number1);
        number2TW = findViewById(R.id.number2);
        number3TW = findViewById(R.id.number3);
        number4TW = findViewById(R.id.number4);

        /*czasSekundyTW.setBackgroundResource(R.color.myPink);
        scoreTW.setBackgroundResource(R.color.myPurple);

        number1TW.setBackgroundResource(R.color.myLightBlue);
        number2TW.setBackgroundResource(R.color.myDarkBlue);
        number3TW.setBackgroundResource(R.color.myDarkBlue);
        number4TW.setBackgroundResource(R.color.myLightBlue);*/
        czasSekundyTW.setBackgroundResource(R.color.googleBlue);

        scoreTW.setBackgroundResource(R.color.googleGrean);

        number1TW.setBackgroundResource(R.color.googleYellow);
        number2TW.setBackgroundResource(R.color.googleRed);
        number3TW.setBackgroundResource(R.color.googleRed);
        number4TW.setBackgroundResource(R.color.googleYellow);
        mediaPlayer = MediaPlayer.create(this, R.raw.kid);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        randNumbers();
        setTWNumbers();
    }

    private void randNumbers()
    {
        for(int j = 0; j < numberIsTaken.length; j++)
        {
            numberIsTaken[j] = false;
        }
        int answer;

        Random random = new Random();

        sum1 = random.nextInt(98) +1;
        sum2 = random.nextInt(98) +1;

        answer = sum1 + sum2;

        correctAnswer = random.nextInt(4);
        number[correctAnswer] = answer;
        numberIsTaken[correctAnswer] = true;
        correctAnswer++;

        int iPlacement = 1;
        while (iPlacement< 4)
        {
            if(iPlacement == 1)
            {
                int a = random.nextInt(4);
                if(!numberIsTaken[a])
                {
                    number[a] = answer + 1;
                    numberIsTaken[a] = true;
                    iPlacement++;
                }
            }

            else if(iPlacement == 2)
            {
                int a = random.nextInt(4);
                if(!numberIsTaken[a])
                {
                    number[a] = answer -1 ;
                    numberIsTaken[a] = true;
                    iPlacement++;
                }
            }
            else if( iPlacement == 3)
            {
                int a = random.nextInt(4);
                if(!numberIsTaken[a])
                {
                    if(random.nextBoolean())
                    {
                        number[a] = answer -2;
                        numberIsTaken[a] = true;
                        iPlacement++;
                    }
                    else
                        {
                            number[a] = answer + 2;
                            numberIsTaken[a] = true;
                            iPlacement++;
                        }
                }
            }
        }


    }

    private void setTWNumbers()
    {
        String [] sNumber = new String[4];

        for(int i = 0; i < number.length; i++)
        {
            if(Integer.toString(number[i]).length() == 3)
            {
                sNumber[i] = Integer.toString(number[i]);
            }

            if(Integer.toString(number[i]).length() == 2)
            {
                sNumber[i] = " " + Integer.toString(number[i]) + " ";
            }

            if(Integer.toString(number[i]).length() == 1)
            {
                sNumber[i] = "  " + Integer.toString(number[i]) + "  ";
            }
        }

        number1TW.setText(sNumber[0]);
        number2TW.setText(sNumber[1]);
        number3TW.setText(sNumber[2]);
        number4TW.setText(sNumber[3]);

        String zadanie = Integer.toString(sum1) + " + " +
         Integer.toString(sum2);
        zadanieTW.setText(zadanie);
    }

    private void updateScore()
    {
        String text = Integer.toString(score) + "/"
                + Integer.toString(totalQuestions);
        scoreTW.setText(text);
    }

    public void numberClicked (View view)
    {


        if((System.currentTimeMillis() - timeSciceLastClick) > 600)
        {
            if(!cDTIsCounting)
            {
                cDTIsCounting = true;
                countDownTimer = new CountDownTimer(30*1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        String czas = Long.toString(l / 1000) + "s";
                        czasSekundyTW.setText(czas);
                    }

                    @Override
                    public void onFinish() {
                        score = 0;
                        totalQuestions = 0;
                        zadanieTW.setText("Kliknij aby zagrać ponownie");
                        //scoreTW.setVisibility(View.INVISIBLE);
                        //czasSekundyTW.setVisibility(View.INVISIBLE);
                        timeSciceLastClick = System.currentTimeMillis() + 1000;
                        cDTIsCounting = false;

                    }
                }.start();
            }

            totalQuestions++;
            timeSciceLastClick = System.currentTimeMillis();

            //specyficzne działania dla danego guzika
            int id = view.getId();
            int buttonInteger = -1;

            if (id == 2131165274)//number1
            {
                // Log.i("button", "number 1");
                buttonInteger = 1;
            } else if (id == 2131165275)//number2
            {
                //Log.i("button", "number 2");
                buttonInteger = 2;
            } else if (id == 2131165276)//nummber3
            {
                //Log.i("button", "number 3");
                buttonInteger = 3;
            } else if (id == 2131165277)//number4
            {
                //Log.i("button", "number 4");
                buttonInteger = 4;
            }

            if (buttonInteger == correctAnswer) {

                mediaPlayer.start();

                score++;

                view.animate().rotationBy(360f).setDuration(500).start();

                //Log.i("odp", "dobra odpowiedz");

            }
            else {

                //Log.i("odp", "zla odpowiedz");

                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(300,VibrationEffect.DEFAULT_AMPLITUDE));
                }

                else{
                    //deprecated in API 26
                    v.vibrate(300);
                }
            }

            randNumbers();
            setTWNumbers();
            updateScore();

        }
    }
}
