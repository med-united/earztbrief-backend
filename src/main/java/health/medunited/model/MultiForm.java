package health.medunited.model;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class MultiForm {

    @FormParam("file")
    @PartType("text/plain")
    public InputStream file;

    public MultiForm() {
    }

    public MultiForm(InputStream file) {
        this.file = file;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}
