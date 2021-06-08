package at.htlvillach.bll;

public class Season {
    private int id;
    private String identifier;
    private static int largestId = 0;

    public Season() { }

    public Season(int id, String identifier) {
        setId(id);
        this.identifier = identifier;
    }

    public int getId() {
        return id;
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

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
