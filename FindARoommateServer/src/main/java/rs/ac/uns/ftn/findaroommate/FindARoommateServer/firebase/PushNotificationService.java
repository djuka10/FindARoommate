package rs.ac.uns.ftn.findaroommate.FindARoommateServer.firebase;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotificationToTokenWithData(PushNotificationRequest request, String token, Map<String, String> data) {
        try {
            fcmService.sendMessageToTokenWithData(request, token, data);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

  
}