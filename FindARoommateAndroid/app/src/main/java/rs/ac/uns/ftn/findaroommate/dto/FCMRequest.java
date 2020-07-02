package rs.ac.uns.ftn.findaroommate.dto;

public class FCMRequest {
    public FCMData data;
    public String to;

    public FCMRequest(){

    }

    public FCMRequest(FCMData data, String to) {
        this.data = data;
        this.to = to;
    }
}
