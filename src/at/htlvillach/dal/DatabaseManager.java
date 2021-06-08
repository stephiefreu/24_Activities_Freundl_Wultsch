package at.htlvillach.dal;

import at.htlvillach.bll.Activity;
import at.htlvillach.bll.Person;
import at.htlvillach.bll.Season;
import at.htlvillach.util.PropertyManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private String driver;
    private String url;
    private String username;
    private String password;
    private static DatabaseManager instance;
    private static HashMap<Integer, Person> personHashMap = null;

    private DatabaseManager() {
        PropertyManager.getInstance().setFilename("db.properties");
        driver = PropertyManager.getInstance().readProperty("driver", "oracle.jdbc.OracleDriver");
        url = PropertyManager.getInstance().readProperty("url", "jdbc:oracle:thin:@tcif.htl-villach.at:1521/orcl");
        username = PropertyManager.getInstance().readProperty("username", "d3a06");
        password = PropertyManager.getInstance().readProperty("password", "d3a06");
    }

    private Connection createConnection(){
        Connection con = null;
        //1. Laden des Treibers
        try{
            Class.forName(this.driver);
            //2. Erzeugen der Datenbankverbindung
            con = DriverManager.getConnection(url, username, password);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return con;
    }

    public static DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }


    public List<Person> getAllPersons() {
        personHashMap = getPersonHashMap();
        Map<Integer, Person> copy = Map.copyOf(personHashMap);
        return new ArrayList<>(copy.values());
    }

    public HashMap<Integer, Person> getPersonHashMap(){
        HashMap<Integer, Person> persons = new HashMap<>();
        Statement stmt;
        ResultSet resultSet;
        String query = "SELECT * FROM Person";

        try(Connection con = this.createConnection()){ //Ressource wird automatisch am Ende vom try geschlossen
            //resultSet wird erzeugt
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            //resultSet wird durchiteriert
            while (resultSet.next()){
                persons.put(resultSet.getInt(1), new Person(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return persons;
    }


    public Person getPersonById(int id) {
        if(personHashMap == null)
            personHashMap = instance.getPersonHashMap();
        return personHashMap.get(id);
    }

    public boolean updatePerson(Person p) {
        boolean result = false;
        PreparedStatement preparedStatement; //verhindert SQL injections, nur Parameter werden ausgetauscht bei 2. Zugriff
        String stmt_update = "UPDATE Person SET idActivity = ?, firstname = ?, lastname = ? WHERE id = ?";
        int numberrows = 0;

        try(Connection con = this.createConnection()){
            preparedStatement = con.prepareStatement(stmt_update, new String[]{"id"});
            preparedStatement.setInt(1, p.getIdActivity());
            preparedStatement.setString(2, p.getFirstname());
            preparedStatement.setString(3, p.getLastname());
            preparedStatement.setInt(4, p.getId());
            numberrows = preparedStatement.executeUpdate();
            if(numberrows > 0){
                result = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public List<Activity> getAllActivities() {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;
        String query = "SELECT * FROM Activity";

        try(Connection con = this.createConnection()){ //Ressource wird automatisch am Ende vom try geschlossen
            //resultSet wird erzeugt
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            //resultSet wird durchiteriert
            while (resultSet.next()){
                activities.add(new Activity(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return activities;
    }

    public List<Season> getAllSeasons() {
        ArrayList<Season> seasons = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;
        String query = "SELECT * FROM Season";

        try(Connection con = this.createConnection()){ //Ressource wird automatisch am Ende vom try geschlossen
            //resultSet wird erzeugt
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);

            //resultSet wird durchiteriert
            while (resultSet.next()){
                seasons.add(new Season(resultSet.getInt(1), resultSet.getString(2)));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return seasons;
    }
}
