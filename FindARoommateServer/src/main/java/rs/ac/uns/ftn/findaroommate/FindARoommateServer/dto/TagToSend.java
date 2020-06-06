package rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto;

public class TagToSend {

    private String name;

    private String dateModified;

    public TagToSend() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
