package com.example.shubham.serviceandbroadcastreceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartService,btnStopService,btnGetValue;
    private EditText valueHolder,threadNumber;
    private Intent startServiceIntent;

    MyService myService;
    boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_StartService);
        btnStopService = findViewById(R.id.btn_StopService);
        btnGetValue = findViewById(R.id.btn_getValue);
        valueHolder = findViewById(R.id.et_valueHolder);
        threadNumber = findViewById(R.id.et_threadNumber);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.shubham.sendBroadcast");
        registerReceiver(broadcastReceiver,intentFilter);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnGetValue.setOnClickListener(this);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder)service;
            myService = myServiceBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            valueHolder.setText("All Threads Completed");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_StartService:
                startServiceIntent = new Intent(this,MyService.class);
                //startService(startServiceIntent);
                bindService(startServiceIntent,mServiceConnection,Context.BIND_AUTO_CREATE);

            case R.id.btn_StopService:
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                //stopService(startServiceIntent);

            case R.id.btn_getValue:
                if(mServiceBound) {
                    String number = threadNumber.getText().toString();
                    String value = myService.getValue(number);
                    valueHolder.setText(value);
                }
        }



    }

    /*private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }*/
}
