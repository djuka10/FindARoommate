package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.os.AsyncTask;

public class AdItemsTask extends AsyncTask<Long,Void,Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public AdItemsTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Long... longs) {
        return null;
    }
}
