package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import rs.ac.uns.ftn.findaroommate.task.AdItemsTask;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class AdItemsService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("AdItemsService", "onStartCommand");
        long adId = intent.getLongExtra("adId", -1l);

        int status = AppTools.getConnectivityStatus(getApplicationContext());

        if(status == AppTools.TYPE_NOT_CONNECTED){
            Log.e("AdItemsService", "The connection is not established");
        }
        new AdItemsTask(getApplicationContext()).execute(adId);

        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
