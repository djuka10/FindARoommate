package rs.ac.uns.ftn.findaroommate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BookReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
