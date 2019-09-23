package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity"; //tag for logcat
    private static final String KEY_INDEX = "index"; //to maintain index after orientation change
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton; //initialize button variables
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView; //initialize textview variable

    private Question[] mQuestionBank = new Question[]{ //initialize question array with answers
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0; //default index, initialize
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called"); //tag on create
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){ //checks if state has been saved from orientation switch
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0); //sets to saved index
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); //sets question text


        mTrueButton = (Button) findViewById(R.id.true_button); //sets true button text
        mTrueButton.setOnClickListener(new View.OnClickListener(){ //when clicked, checks correct/incorrect status
            @Override public void onClick(View v){
                checkAnswer(true);

            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button); //same as true button
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                checkAnswer(false);

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){ //when clicked, changes question text
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){ //checks if user cancelled before cheating
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart(){ //log on start
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){ //log on resume
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){ //log on pause
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){ //when orientation changes,
        super.onSaveInstanceState(savedInstanceState);           //saves state and index
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop(){ //log on stop
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){ //log on destroy
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



     private void updateQuestion() { //method called by next button, changes question text
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){ //method called by true/false buttons
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater){ //checks if user completed the cheat activity
            messageResId = R.string.judgment_toast;
        } else {

            if (userPressedTrue == answerIsTrue) { //checks whether correct or incorrect
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show(); //toast of correct vs incorrect
    }
}
