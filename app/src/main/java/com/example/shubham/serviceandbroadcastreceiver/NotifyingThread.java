package com.example.shubham.serviceandbroadcastreceiver;

import android.util.Log;

public class NotifyingThread extends Thread {
    private int i;
    private  int off;
    NotifyingThread(int i, int off){
        this.i = i;
        this.off = off;
    }
    private NotifyingOnCompletion listener;
    void addListener(NotifyingOnCompletion listener){
        this.listener = listener;
    }
    @Override
    public void run() {
        try{
            while (i>0){
                try{
                    Thread.sleep(off);
                    Log.d(""+off + " at ", "run: "+i);
                    i--;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){}
        finally {
            listener.notifyOnComplete();
        }
    }


}
