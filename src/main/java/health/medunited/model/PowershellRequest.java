package health.medunited.model;

import org.jboss.resteasy.annotations.jaxrs.FormParam;

import java.io.File;

public class PowershellRequest {

    @FormParam("file")
    private File file;

    @FormParam("email")
    private String doctorEmail;

    public PowershellRequest() {
    }

    public PowershellRequest(File file, String doctorEmail) {
        this.file = file;
        this.doctorEmail = doctorEmail;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }
}
