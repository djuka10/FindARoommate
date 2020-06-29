package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import rs.ac.uns.ftn.findaroommate.task.EditProfileTask;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class EditProfileService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("EditProfileService", "onStartCommand");
        long userId = intent.getLongExtra("userId", -1l);

        int status = AppTools.getConnectivityStatus(getApplicationContext());

        if(status == AppTools.TYPE_NOT_CONNECTED){
            Log.e("EditProfileService", "The connection is not established");
        }
        new EditProfileTask(getApplicationContext()).execute(userId);

        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
//        Intent intent = new Intent(getApplicationContext(), ServerErrorReceiver.class);
//        sendBroadcast(intent);

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
