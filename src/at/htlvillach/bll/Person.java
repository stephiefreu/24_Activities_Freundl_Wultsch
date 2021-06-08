package at.htlvillach.bll;

import at.htlvillach.dal.dao.Dao;

public class Person {
    private int id;
    private int idActivity;
    private String firstname;
    private String lastname;
    private static int largestId = 0;

    public Person() {}

    public Person(int id, int idActivity, String firstname, String lastname) {
        setId(id);
        this.idActivity = idActivity;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(String firstname, String lastname, int idActivity) {
        setId();
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
        if(id > largestId)
            largestId = id;
        this.id = id;
    }

    public void setId() {
        this.id = largestId++;
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

    public boolean update(Dao<Person> dao){
        return dao.update(this);
    }
}
