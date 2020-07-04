package rs.ac.uns.ftn.findaroommate.dto;

public class FCMData {
    private String fcmAction;
    private String message;
    private String sender;
    private String senderId;

    public FCMData(String fcmAction, String message, String sender, String senderId) {
        this.fcmAction = fcmAction;
        this.message = message;
        this.sender = sender;
        this.senderId = senderId;
    }

    public FCMData() {
    }
}
