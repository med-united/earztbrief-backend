package health.medunited.model;

public class LetterRequest {
    private String contactName;
    private String contactEmail;
    private String contactMessage;
    private String attachment;

    public LetterRequest(String contactName, String contactEmail, String contactMessage, String attachment) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactMessage = contactMessage;
        this.attachment = attachment;
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

    public String getAttachment() {
        return attachment;
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

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}