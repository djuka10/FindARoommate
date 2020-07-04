package rs.ac.uns.ftn.findaroommate.FindARoommateServer.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationResponse {

    private int status;
    private String message;
    
}
