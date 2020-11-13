package com.example.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView finalScoreView;
    int rightAnswer;
    int questionCount;
    int score;
    String gameDuration = 20 + "s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timerView = findViewById(R.id.timerView);

        timerView.setText(gameDuration);

        constraintLayout = findViewById(R.id.gameLayout);
        constraintLayout.setVisibility(View.INVISIBLE);

        finalScoreView = findViewById(R.id.finalScoreView);
        finalScoreView.setVisibility(View.INVISIBLE);

    }

    public void onStart(View view) {
        constraintLayout = findViewById(R.id.gameLayout);
        constraintLayout.setVisibility(View.VISIBLE);

        final Button startButton = findViewById(R.id.goButton);
        startButton.setVisibility(View.INVISIBLE);

        finalScoreView.setVisibility(View.INVISIBLE);

        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText("0/0");

        new CountDownTimer(20000, 1000) {
            final TextView timerView = findViewById(R.id.timerView);
            public void onTick(long msUntilDone) {
                timerView.setText((msUntilDone / 1000 + 1) + "s");
            }

            public void onFinish() {
                constraintLayout.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);

                finalScoreView.setText(String.format("%2d/%2d", score, questionCount));
                finalScoreView.setVisibility(View.VISIBLE);

                score = 0;
                questionCount = 0;
            }
        }.start();

        final TextView questionView = findViewById(R.id.questionView);
        questionView.setText(generateQuestion());
    }

    public String generateQuestion() {
        int operand1 = (int) (Math.random() * 99 + 1);
        int operand2 = (int) (Math.random() * 99 + 1);

        int wrongAnswer1 = (int) (Math.random() * 199 + 1);
        int wrongAnswer2 = (int) (Math.random() * 199 + 1);
        int wrongAnswer3 = (int) (Math.random() * 199 + 1);
        rightAnswer = operand1 + operand2;

        int randomNum = (int) (Math.random() * 3 + 1);

        Button answerBtn1 = findViewById(R.id.answerBtn1);
        Button answerBtn2 = findViewById(R.id.answerBtn2);
        Button answerBtn3 = findViewById(R.id.answerBtn3);
        Button answerBtn4 = findViewById(R.id.answerBtn4);

        if(randomNum == 1) {
            answerBtn2.setText(Integer.toString(wrongAnswer1));
            answerBtn3.setText(Integer.toString(wrongAnswer2));
            answerBtn4.setText(Integer.toString(wrongAnswer3));

            answerBtn1.setText(Integer.toString(rightAnswer));
        }

        if(randomNum == 2) {
            answerBtn1.setText(Integer.toString(wrongAnswer1));
            answerBtn3.setText(Integer.toString(wrongAnswer2));
            answerBtn4.setText(Integer.toString(wrongAnswer3));

            answerBtn2.setText(Integer.toString(rightAnswer));
        }

        if(randomNum == 3) {
            answerBtn1.setText(Integer.toString(wrongAnswer1));
            answerBtn2.setText(Integer.toString(wrongAnswer2));
            answerBtn4.setText(Integer.toString(wrongAnswer3));

            answerBtn3.setText(Integer.toString(rightAnswer));
        }

        if(randomNum == 4) {
            answerBtn1.setText(Integer.toString(wrongAnswer1));
            answerBtn2.setText(Integer.toString(wrongAnswer2));
            answerBtn3.setText(Integer.toString(wrongAnswer3));

            answerBtn4.setText(Integer.toString(rightAnswer));
        }

        final TextView questionView = findViewById(R.id.questionView);
        questionView.setText(String.format("%2d + %2d", operand1, operand2));

        return String.format("%2d + %2d", operand1, operand2);
    }

    public void answer(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();

        if(buttonText.equals(Integer.toString(rightAnswer))) {
            score++;
        }

        questionCount++;

        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText(String.format("%2d/%2d", score, questionCount));
        generateQuestion();
    }
}