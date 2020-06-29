package rs.ac.uns.ftn.findaroommate.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.model.User;

public class AppTools {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int calculateTimeTillNextSync(int minutes){
        return 1000 * 60 * minutes;
    }

    private static FirebaseAuth firebaseAuth;

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        AppTools.firebaseUser = firebaseUser;
    }

    private static FirebaseUser firebaseUser;

    public static void setLoggedUser(User loggedUser) {
        AppTools.loggedUser = loggedUser;
    }

    private static User loggedUser;

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static FirebaseUser getFirebaseUser(){
        if(firebaseUser == null){
            FirebaseAuth auth = getFirebaseAuth();
            if(auth == null){
                // error
            }
            firebaseUser = auth.getCurrentUser();
        }
        return firebaseUser;
    }

    public static User getLoggedUser() {
        if(loggedUser == null){
            FirebaseUser firebaseUser = getFirebaseUser();
            if(firebaseUser == null){
                loggedUser = null;
                return loggedUser;
            }
            List<User> list = User.getOneByEmail(firebaseUser.getEmail());
            if(!list.isEmpty()){
                loggedUser = list.get(0);
            } else {
                loggedUser = null;

            }
        }
        return loggedUser;
    }

    public static String createFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "IMG_"+ timeStamp + ".jpg";
    }

    public static String getDeviceId(){
        return FirebaseInstanceId.getInstance().getToken();
    }

}
