package com.derrick.park.countryquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    protected TextView mText;
    protected Button mTrueButton;
    protected Button mFalseButton;
    protected ImageButton mPrevious_question;
    protected ImageButton mNext_question;
    protected int mCurrentIndex = 0;

    // creating an array with all the country questions and their boolean values
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

        //  Implementing the method to advance to next question
        //  when user clicks on the text view.
        mText = (TextView) findViewById(R.id.textview);
        mText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // Displays the "correct" toast when user answer is correct
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        //  Displays the "incorrect" toast when user answer is wrong
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        //  Implementing the method to go back to previous question when user
        //  clicks on the rewind button
        mPrevious_question = (ImageButton) findViewById(R.id.previous_question);
        mPrevious_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                previousQuestion();
            }
        });

        //  Implementing the method to advance to next question when user
        //  clicks on the fast-forward button
        mNext_question = (ImageButton) findViewById(R.id.next_question);
        mNext_question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextQuestion();
            }
        });
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
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
