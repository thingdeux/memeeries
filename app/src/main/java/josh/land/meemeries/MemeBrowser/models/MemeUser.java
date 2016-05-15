package josh.land.meemeries.MemeBrowser.models;

public class MemeUser {
    private double id;
    private String name;

    // Model has to have an empty construtor for Firebase.
    public MemeUser() {
    }

    public MemeUser(final String name) {
        this.name = name;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
