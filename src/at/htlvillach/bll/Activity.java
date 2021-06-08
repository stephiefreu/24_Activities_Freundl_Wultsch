package at.htlvillach.bll;

public class Activity {
    private int id;
    private int idSeason;
    private String identifier;
    private int largestId = 0;

    public Activity() {}

    public Activity(int id, int idSeason, String identifier) {
        setId(id);
        this.idSeason = idSeason;
        this.identifier = identifier;
    }

    public int getId() {
        return id;
    }

    public int getIdSeason() {
        return idSeason;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setId(int id) {
        if(id > largestId)
            largestId = id;
        this.id = id;
    }

    public void setId() {
        this.id = largestId++;
    }

    public void setIdSeason(int idSeason) {
        this.idSeason = idSeason;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
