package health.medunited.model;

public class EmailRequest {
    private String contactname;
    private String contactemail;
    private String contactmessage;
    private String attachment;
    private ArrayList<String> datamatrices;

    public EmailRequest(String contactname, String contactemail, String contactmessage, String attachment, ArrayList<String> datamatrices) {
        this.contactname = contactname;
        this.contactemail = contactemail;
        this.contactmessage = contactmessage;
        this.attachment = attachment;
        this.datamatrices = datamatrices;
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

    public ArrayList<String> getDatamatrices() { return datamatrices; }

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

    public void setDatamatrices(Array<String> datamatrices) {this.datamatrices = datamatrices; }

    public void addDatamatrice(ArrayList<String> datamatrices, String datamatrix) {
        this.datamatrices.add(datamatrix);
    }
}