package at.htlvillach.bll;

public class Person {
    private int id;
    private int idActivity;
    private String firstname;
    private String lastname;

    public Person() {}

    public Person(String firstname, String lastname, int idActivity) {
        this.idActivity = idActivity;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return firstname + ' ' + lastname;
    }
}
