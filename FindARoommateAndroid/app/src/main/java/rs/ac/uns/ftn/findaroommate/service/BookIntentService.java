package rs.ac.uns.ftn.findaroommate.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import rs.ac.uns.ftn.findaroommate.utils.AppTools;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BookIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "rs.ac.uns.ftn.findaroommate.service.action.FOO";
    private static final String ACTION_BAZ = "rs.ac.uns.ftn.findaroommate.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "rs.ac.uns.ftn.findaroommate.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "rs.ac.uns.ftn.findaroommate.service.extra.PARAM2";

    public BookIntentService() {
        super("BookIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i("BookIntentService", "onStartCommand");
            long userId = intent.getLongExtra("userId", -1l);

            int status = AppTools.getConnectivityStatus(getApplicationContext());

            if(status == AppTools.TYPE_NOT_CONNECTED){
                Log.e("BookIntentService", "The connection is not established");
            }

            Log.i("REZ", "doInBackground");
        }
    }


}
