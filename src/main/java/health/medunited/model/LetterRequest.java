package health.medunited.model;
import java.util.ArrayList;

public class LetterRequest {
    private String contactName;
    private String contactEmail;
    private String contactMessage;
    private ArrayList<String> attachment;
    private ArrayList<String> datamatrices;

    public LetterRequest(String contactName, String contactEmail, String contactMessage, ArrayList<String> attachment, ArrayList<String> datamatrices) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactMessage = contactMessage;
        this.attachment = attachment;
        this.datamatrices = datamatrices;
    }

    public LetterRequest() {
        
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactMessage() {
        return contactMessage;
    }

    public ArrayList<String> getAttachment() {
        return attachment;
    }

    public ArrayList<String> getDatamatrices() {
        return datamatrices;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public void setAttachment(ArrayList<String> attachment) {
        this.attachment = attachment;
    }

    public void setDatamatrices(ArrayList<String> datamatrices) {
        this.datamatrices = datamatrices;
    }
}