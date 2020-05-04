package rs.ac.uns.ftn.findaroommate.model;

public class Language {

    private int isoCode;
    private String name;

    public Language(int isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }

    public int getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(int isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
