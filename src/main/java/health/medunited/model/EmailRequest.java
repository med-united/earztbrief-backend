package health.medunited.model;

public class EmailRequest {
    private String contactname;
    private String contactemail;
    private String contactmessage;
    private String attachment;

    public EmailRequest(String contactname, String contactemail, String contactmessage, String attachment) {
        this.contactname = contactname;
        this.contactemail = contactemail;
        this.contactmessage = contactmessage;
        this.attachment = attachment;
    }

    public EmailRequest () {
        
    }

    public String getContactname() {
        return contactname;
    }

    public String getContactemail() {
        return contactemail;
    }

    public String getContactmessage() {
        return contactmessage;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public void setContactemail(String contactemail) {
        this.contactemail = contactemail;
    }

    public void setContactmessage(String contactmessage) {
        this.contactmessage = contactmessage;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}