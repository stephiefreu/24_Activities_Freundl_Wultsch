package at.htlvillach.dal.dao;

import at.htlvillach.bll.Person;
import at.htlvillach.dal.DatabaseManager;

import java.util.List;

public class PersonDBDao implements Dao<Person>{

    @Override
    public List<Person> getAll() {
        return DatabaseManager.getInstance().getAllPersons();
    }

    @Override
    public Person getById(int id) {
        return DatabaseManager.getInstance().getPersonById(id);
    }

    @Override
    public boolean insert(Person item) {
        return false;
    }

    @Override
    public boolean delete(Person item) {
        return false;
    }

    @Override
    public boolean update(Person item) {
        return DatabaseManager.getInstance().updatePerson(item);
    }
}
