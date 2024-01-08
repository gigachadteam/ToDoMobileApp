package com.example.todoapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class Work extends Worker {

    Context context;

    public Work(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("work", "network");
        Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
        return Result.success();
    }

    public static void networkCheck(){
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Work.class)
                .setInitialDelay(2, TimeUnit.SECONDS)
                .setConstraints(getConst())
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    public static Constraints getConst(){
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        return constraints;
    }


}
