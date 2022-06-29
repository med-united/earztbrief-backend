package health.medunited.model;

import java.util.Date;
import java.util.List;

public class PharmacyRequest {

    private String pharmacyEmail;

    private List<String> patients;

    private List<String> doctors;

    private List<String> pzns;

    private String status;

    private Date requestDate;

    public PharmacyRequest() {
    }
    public PharmacyRequest(String pharmacyEmail, List<String> patients, List<String> doctors, List<String> pzns, String status, Date requestDate) {
        this.pharmacyEmail = pharmacyEmail;
        this.patients = patients;
        this.doctors = doctors;
        this.pzns = pzns;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public List<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<String> doctors) {
        this.doctors = doctors;
    }

    public List<String> getPzns() {
        return pzns;
    }

    public void setPzns(List<String> pzns) {
        this.pzns = pzns;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
