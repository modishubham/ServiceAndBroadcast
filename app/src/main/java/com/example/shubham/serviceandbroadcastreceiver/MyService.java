package com.example.shubham.serviceandbroadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {         //implements NotifyingOnCompletion{
    private static final String MY_TAG = "mytag";
    private String thread1Value;
    private String thread2Value;
    private String thread3Value;
    Thread t1;
    Thread t2;
    Thread t3;
    private int count = 0;
    //private NotifyingThread t1;
    //private NotifyingThread t2;
    //private NotifyingThread t3;
    //int counter;
    //@Override
    //public void notifyOnComplete() {
    //    Log.d("qw", "notifyOnComplete: ");
    //    counter++;
    //    if(counter==3){
    //        stopSelf();
    //    }
    //}

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder mBinder = new MyServiceBinder();



    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /*@Override
    public void onCreate() {
        super.onCreate();
        t1 = new NotifyingThread(10,2000);
        t2 = new NotifyingThread(20,1000);
        t3 = new NotifyingThread(15,1500);
        t1.addListener(this);
        t2.addListener(this);
        t3.addListener(this);
        t1.start();
        t2.start();
        t3.start();
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getBaseContext(),"Service Started",Toast.LENGTH_LONG).show();
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int n=1;
                while(n<10) {
                    try {
                        Log.e(MY_TAG,"thread1 "+n);
                        thread1Value = "thread1 "+String.valueOf(n);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    n++;
                }
                count++;
                sendBroadcastAndNotifyUser(count);
            }
        });
        t1.start();
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int n=1;
                while(n<15) {
                    try {
                        Log.e(MY_TAG,"thread2 "+n);
                        thread2Value = "thread2 "+String.valueOf(n);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    n++;
                }
                count++;
                sendBroadcastAndNotifyUser(count);
            }
        });
        t2.start();
        t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int n=1;
                while(n<15) {
                    try {
                        Log.e(MY_TAG,"thread3 "+n);
                        thread3Value = "Thread3 "+String.valueOf(n);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    n++;
                }
                count++;
                sendBroadcastAndNotifyUser(count);
            }
        });
        t3.start();
    }

    private void sendBroadcastAndNotifyUser(int count) {
        if(count==3) {
            Intent broadcastInten = new Intent();
            broadcastInten.setAction("com.shubham.sendBroadcast");
            sendBroadcast(broadcastInten);
        } else {
            return;
        }
    }

    public String getValue(String number) {
        if(number.equals("1") && t1.isAlive()) {
            return thread1Value;
        } else if(number.equals("2") && t2.isAlive()) {
            return thread2Value;
        } else if(number.equals("3") && t3.isAlive()) {
            return thread3Value;
        } else if (number.equals("1") && t1.isAlive()==false) {
            return "Thread 1 completed its Task";
        } else if (number.equals("2") && t2.isAlive()==false) {
            return "Thread 2 completed its Task";
        } else if (number.equals("3") && t3.isAlive()==false) {
            return "Thread 3 completed its Task";
        } else {
            return "this thread is not available";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(),"Service Destroyed",Toast.LENGTH_LONG).show();
    }
}
