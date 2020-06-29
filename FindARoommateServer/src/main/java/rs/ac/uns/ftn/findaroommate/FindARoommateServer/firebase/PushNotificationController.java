package rs.ac.uns.ftn.findaroommate.FindARoommateServer.firebase;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Review;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.AdService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.ReviewService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.UserDeviceService;

@RestController
@RequestMapping("/notification")
public class PushNotificationController {

    private UserDeviceService userDeviceService;
    private PushNotificationService pushNotificationService;
    private AdService adService;
    private ReviewService reviewService;
    
    private Logger logger = LoggerFactory.getLogger(PushNotificationController.class);

    public PushNotificationController(
    		AdService adService, 
    		UserDeviceService userDeviceService, 
    		PushNotificationService pushNotificationService,
    		ReviewService reviewService
    		) {
        this.pushNotificationService = pushNotificationService;
        this.userDeviceService = userDeviceService;
        this.adService = adService;
        this.reviewService = reviewService;
    }

    @PostMapping("/booking")
    public ResponseEntity<?> sendBookingNotification(@RequestBody PushNotificationRequest request) {
    	// retrieve to whom to send
    	try {
			String token = userDeviceService.getUserDeviceToken(request.getUserId(), request.getAdId());
			if(!token.isEmpty()) {
				request.setToken(token);
				
				// send notification
			    //pushNotificationService.sendPushNotificationToToken(request);
				
				Ad ad = adService.getOne(request.getAdId());
				
			    Map<String, String> data = new HashMap<String, String>();
			    data.put("fcmAction", "booking");
			    data.put("adEntityId", ad.getEntityId().toString());
			    data.put("adStatus", ad.getAdStatus().toString());
				
				pushNotificationService.sendPushNotificationToTokenWithData(request, token, data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
    	
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    
    @PostMapping("/review")
    public ResponseEntity<?> sendReviewNotification(@RequestBody PushNotificationRequest request) {
    	// retrieve to whom to send
    	try {
			String token = userDeviceService.getUserDeviceToken(request.getUserId(), request.getAdId());
			if(!token.isEmpty()) {
				request.setToken(token);
				
				Review review = reviewService.getOne(request.getReviewId());
				
			    Map<String, String> data = new HashMap<String, String>();
			    data.put("fcmAction", "review");
			    
			    data.put("entityId", review.getEntityId().toString());
			    data.put("rating", review.getRating().toString());
			    
			    data.put("author", review.getAuthor().getEntityId().toString());
			    data.put("ratedUser", review.getRatedUser().getEntityId().toString());
			    data.put("ad", review.getAd().getEntityId().toString());
			    
			    data.put("comment", review.getComment());
			    data.put("title", review.getTitle());
			    data.put("reviewerName", review.getAuthor().getFirstName());


				
				pushNotificationService.sendPushNotificationToTokenWithData(request, token, data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
    	
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
}
