package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import rs.ac.uns.ftn.findaroommate.utils.AppTools;

/**
 * Created by milossimic on 4/6/16.
 */
public class DemoService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("REZ", "onStartCommand");

        //Intent ints = new Intent(MainActivity.SYNC_DATA);
        int status = AppTools.getConnectivityStatus(getApplicationContext());

        //ints.putExtra(RESULT_CODE, status);

        //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
        if(status == AppTools.TYPE_WIFI){
            new DemoTask(getApplicationContext()).execute();


        }

        //sendBroadcast(ints);

        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
