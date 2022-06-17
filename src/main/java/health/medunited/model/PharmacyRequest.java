package health.medunited.model;

import java.util.Date;

public class PharmacyRequest {

    private String pharmacyEmail;

    private String patient;

    private String doctor;

    private int pzn;

    private String status;

    private Date requestDate;

    public PharmacyRequest() {
    }
    public PharmacyRequest(String pharmacyEmail, String patient, String doctor, int pzn, String status, Date requestDate) {
        this.pharmacyEmail = pharmacyEmail;
        this.patient = patient;
        this.doctor = doctor;
        this.pzn = pzn;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getPzn() {
        return pzn;
    }

    public void setPzn(int pzn) {
        this.pzn = pzn;
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
