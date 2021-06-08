package at.htlvillach.dal.dao;

import at.htlvillach.bll.Season;
import at.htlvillach.dal.DatabaseManager;

import javax.xml.crypto.Data;
import java.util.List;

public class SeasonDBDao implements Dao<Season> {
    @Override
    public List<Season> getAll() {
        return DatabaseManager.getInstance().getAllSeasons();
    }

    @Override
    public Season getById(int id) {
        return null;
    }

    @Override
    public boolean insert(Season item) {
        return false;
    }

    @Override
    public boolean delete(Season item) {
        return false;
    }

    @Override
    public boolean update(Season item) {
        return false;
    }
}
