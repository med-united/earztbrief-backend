package health.medunited.model;

public enum MailSubjects {

    EARZTBRIEF("Rezeptanforderung als eArztbrief"),
    PHARMACYNOTIFIER("Anforderung Mitteilung");

    public final String value;

    MailSubjects(String value) {
        this.value = value;
    }
}
