package health.medunited.model;

public enum MailSubjects {

    EARZTBRIEF("Rezeptanforderung als eArztbrief"),
    PHARMACYNOTIFIER("Anforderung Mitteilung"),
    T2MED("Rezeptanforderung als t2med");

    public final String value;

    MailSubjects(String value) {
        this.value = value;
    }
}
