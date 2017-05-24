package com.derrick.park.countryquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

public class QuizActivity extends AppCompatActivity {
    protected TextView mText;
    protected Button mTrueButton;
    protected Button mFalseButton;
    protected ImageButton mPrevious_question;
    protected ImageButton mNext_question;
    protected int mCurrentIndex = 0;
    protected int correct = 0;
    final static String TAG = "QuizActivity";

    // creating an array with all the country questions and their boolean values.
    protected Question [] questions = new Question[] {
            new Question((R.string.question_usa), false),
            new Question((R.string.question_canada), false),
            new Question((R.string.question_japan), true),
            new Question((R.string.question_france), true),
            new Question((R.string.question_brazil), false),
            new Question((R.string.question_italy), true),
            new Question((R.string.question_australia), false),
            new Question((R.string.question_spain), true),
            new Question((R.string.question_germany), false),
            new Question((R.string.question_netherlands),true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate() called");

        //  Implementing the method to advance to next question when user clicks on the text view.
        mText = (TextView) findViewById(R.id.textview);
        mText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // Displays the "correct" toast when user answer is correct and disables buttons.
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                disableButtons();
            }
        });

        //  Displays the "incorrect" toast when user answer is wrong and disables buttons.
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                disableButtons();
            }
        });

        //  Implementing the method to go back to previous question when user clicks on the rewind button.
        mPrevious_question = (ImageButton) findViewById(R.id.previous_question);
        mPrevious_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                previousQuestion();
                enableButtons();
            }
        });

        //  Implementing the method to advance to next question when user clicks on the fast-forward button.
        mNext_question = (ImageButton) findViewById(R.id.next_question);
        mNext_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkScore();   //  if the user is on the last question
                nextQuestion();
                enableButtons();    //  if the user answered the previous question
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    // Creating the method to go back to previous question
    private void previousQuestion() {
        if (mCurrentIndex > 0) {
            mCurrentIndex = (mCurrentIndex - 1) % questions.length;
        }
        else
            mCurrentIndex = questions.length - 1;
        int question = questions[mCurrentIndex].getTextResId();
        mText.setText(question);
    }

    // Creating the method to advance to next question
    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % questions.length;
        int question = questions[mCurrentIndex].getTextResId();
        mText.setText(question);
    }

    //  Creating the method to check if answer is correct or wrong and display the according toast
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (answerIsTrue == userPressedTrue) {
            messageResId = R.string.correct_toast;
            correct++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    //  Creating the method to disable "true" and "false" buttons after one of them is clicked.
    protected void disableButtons() {
            if (mFalseButton.isPressed() || mTrueButton.isPressed())
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
    }

    //  Creating the method to enable "true" and "false" buttons when previous or next buttons are clicked.
    protected void enableButtons() {
        if (mPrevious_question.isPressed() || mNext_question.isPressed()) {
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
        }
    }

    // Creating the method to display a toast with user's score after all 10 questions are answered.
    protected void checkScore () {
        if (mCurrentIndex == questions.length-1) {
            NumberFormat pct = NumberFormat.getPercentInstance();
            double result = (double) correct/questions.length;
            Toast res = new Toast(getApplicationContext());
            res.makeText(QuizActivity.this, "Your score: " + pct.format(result), Toast.LENGTH_LONG).show();
            correct = 0;
        }
    }
}
