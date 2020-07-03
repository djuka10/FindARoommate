package rs.ac.uns.ftn.findaroommate.service.api;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

//    public static final String SERVER_API_PATH = "http://192.168.1.2:8089/server/";
    public static final String SERVER_API_PATH = "http://192.168.1.4:8089/server/";
 //      public static final String SERVER_API_PATH = "http://192.168.43.125:8089/server/";

    public static final String FCM_API_PATH = "https://fcm.googleapis.com/";

    public static final String USER_CHARACTERISTIC_API = "userCharacteristic";
    public static final String USER_API = "user";
    public static final String LANGUAGE_API = "language";
    public static final String NOTIFICATION_API = "notification";
    public static final String REVIEW_API = "review";

    public static final String AD_API = "ad";

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_API_PATH)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .client(test())
            .build();

    public static Retrofit fcmRetrofit = new Retrofit.Builder()
            .baseUrl(FCM_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static ReviewServiceApi reviewServiceApi = retrofit.create(ReviewServiceApi.class);
    public static UserServiceApi userServiceApi = retrofit.create(UserServiceApi.class);
    public static AdServiceApi adServiceApi = retrofit.create(AdServiceApi.class);
    public static LanguageServiceApi languageServiceApi = retrofit.create(LanguageServiceApi.class);
    public static UserCharacteristicServiceApi userCharacteristicServiceApi = retrofit.create(UserCharacteristicServiceApi.class);
    public static NotificationServiceApi notificationServiceApi = retrofit.create(NotificationServiceApi.class);
    public static FCMServiceApi fcmServiceApi = fcmRetrofit.create(FCMServiceApi.class);

}
