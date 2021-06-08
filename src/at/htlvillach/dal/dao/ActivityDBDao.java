package at.htlvillach.dal.dao;

import at.htlvillach.bll.Activity;
import at.htlvillach.dal.DatabaseManager;

import java.util.List;

public class ActivityDBDao implements Dao<Activity> {
    @Override
    public List<Activity> getAll() {
        return DatabaseManager.getInstance().getAllActivities();
    }

    @Override
    public Activity getById(int id) {
        return null;
    }

    @Override
    public boolean insert(Activity item) {
        return false;
    }

    @Override
    public boolean delete(Activity item) {
        return false;
    }

    @Override
    public boolean update(Activity item) {
        return false;
    }
}
