package health.medunited.model;

public class PharmacyRequest {

    public PharmacyRequest(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public PharmacyRequest() {

    }

    private String pharmacyEmail;

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }
}
