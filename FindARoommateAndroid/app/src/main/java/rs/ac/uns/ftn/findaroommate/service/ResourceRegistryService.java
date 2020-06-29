package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import rs.ac.uns.ftn.findaroommate.task.ResourceRegistryTask;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;


public class ResourceRegistryService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("ResourceService", "onStartCommand");
        long adId = intent.getLongExtra("adId", -1l);

        int status = AppTools.getConnectivityStatus(getApplicationContext());

        if(status == AppTools.TYPE_NOT_CONNECTED){
            Log.e("ResourceService", "The connection is not established");
        }
        new ResourceRegistryTask(getApplicationContext()).execute(adId);

        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
//        Intent intent = new Intent(getApplicationContext(), EditProfileReceiver.class);
//        sendBroadcast(intent);

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
