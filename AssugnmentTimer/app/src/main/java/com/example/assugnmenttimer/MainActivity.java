package com.example.assugnmenttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.assugnmenttimer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int workoutLimit = 0;
    int restLimit = 0;
    CountDownTimer workoutTimer;
    CountDownTimer restTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.workoutLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.workoutLimit.getText().length()==0)
                    workoutLimit = 0;
                else workoutLimit = Integer.parseInt(binding.workoutLimit.getText().toString());
            }
        });

        binding.restLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.restLimit.getText().length()==0)
                    restLimit = 0;
                else restLimit = Integer.parseInt(binding.restLimit.getText().toString());
            }
        });

        binding.startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutLimit==0)
                    Toast.makeText(MainActivity.this, "Please Set Workout Limit First!", Toast.LENGTH_LONG).show();
                else
                    startWorkoutTimer();
            }
        });

        binding.stopWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWorkout();
            }
        });

        binding.startRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restLimit==0)
                    Toast.makeText(MainActivity.this, "Please Set Rest Limit First!", Toast.LENGTH_LONG).show();
                else
                    startRestTimer();
            }
        });

        binding.stopRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRest();
            }
        });

    }

    void startWorkoutTimer(){
        binding.stopWorkout.setEnabled(true);
        binding.startWorkout.setEnabled(false);
        binding.prgressbarWorkout.setMax(workoutLimit);
        workoutTimer = new CountDownTimer(workoutLimit* 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.prgressbarWorkout.setProgress((int) (workoutLimit-(millisUntilFinished/1000)));
                        binding.currentTimeWorkout.setText(String.valueOf(workoutLimit-(millisUntilFinished/1000)));
                        binding.remainingTimeWorkout.setText(String.valueOf(millisUntilFinished/1000));
                    }
                });
            }

            public void onFinish() {
                notifyAlert();
                binding.prgressbarWorkout.setProgress(0);
                binding.currentTimeWorkout.setText("00");
                binding.remainingTimeWorkout.setText("00");
                binding.stopWorkout.setEnabled(false);
                binding.startWorkout.setEnabled(true);
            }
        };

        workoutTimer.start();
    }

    void stopWorkout(){
        workoutTimer.cancel();
        binding.prgressbarWorkout.setProgress(0);
        binding.currentTimeWorkout.setText("00");
        binding.remainingTimeWorkout.setText("00");
        binding.stopWorkout.setEnabled(false);
        binding.startWorkout.setEnabled(true);
    }

    void startRestTimer(){
        binding.stopRest.setEnabled(true);
        binding.startRest.setEnabled(false);
        binding.progressbarRest.setMax(restLimit);
        restTimer = new CountDownTimer(restLimit* 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressbarRest.setProgress((int) (restLimit-(millisUntilFinished/1000)));
                        binding.currentTimeRest.setText(String.valueOf(restLimit-(millisUntilFinished/1000)));
                        binding.remainingTimeRest.setText(String.valueOf(millisUntilFinished/1000));
                    }
                });
            }

            public void onFinish() {
                notifyAlert();
                binding.progressbarRest.setProgress(0);
                binding.currentTimeRest.setText("00");
                binding.remainingTimeRest.setText("00");
                binding.stopRest.setEnabled(false);
                binding.startRest.setEnabled(true);
            }
        };

        restTimer.start();
    }

    void stopRest(){
        restTimer.cancel();
        binding.progressbarRest.setProgress(0);
        binding.currentTimeRest.setText("00");
        binding.remainingTimeRest.setText("00");
        binding.stopRest.setEnabled(false);
        binding.startRest.setEnabled(true);
    }

    void notifyAlert(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}