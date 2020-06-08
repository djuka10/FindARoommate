package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.task.EditAdTask;
import rs.ac.uns.ftn.findaroommate.task.EditProfileTask;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class EditAdService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("EditProfileService", "onStartCommand");
        long adId = intent.getLongExtra("userId", -1l);
        AdDto ad = (AdDto)intent.getSerializableExtra("ad");

        int status = AppTools.getConnectivityStatus(getApplicationContext());

        if(status == AppTools.TYPE_NOT_CONNECTED){
            Log.e("EditProfileService", "The connection is not established");
        }
        new EditAdTask(getApplicationContext()).execute(adId);

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
