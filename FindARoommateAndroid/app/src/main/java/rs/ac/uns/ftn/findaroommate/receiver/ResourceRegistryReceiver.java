package rs.ac.uns.ftn.findaroommate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ResourceRegistryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
    }
}
