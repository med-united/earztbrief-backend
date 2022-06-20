package health.medunited.model;
import java.util.List;
import java.util.Optional;

public class LetterRequest {
    private String contactName;
    private String contactEmail;
    private String contactMessage;
    private List<String> attachment;
    private List<String> datamatrices;

    public LetterRequest(String contactName, String contactEmail, String contactMessage, List<String> attachment, List<String> datamatrices) {
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

    public List<String> getAttachment() {
        return attachment;
    }

    public Optional<List<String>> getDatamatrices() {
        return Optional.ofNullable(datamatrices);
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

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }

    public void setDatamatrices(List<String> datamatrices) {
        this.datamatrices = datamatrices;
    }
}