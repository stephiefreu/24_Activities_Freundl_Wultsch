package at.htlvillach.gui;

import at.htlvillach.bll.Activity;
import at.htlvillach.bll.Person;
import at.htlvillach.bll.Season;
import at.htlvillach.dal.dao.Dao;
import at.htlvillach.dal.dao.PersonDBDao;
import at.htlvillach.dal.dao.SeasonDBDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML
    private ChoiceBox cbSeasons;
    @FXML
    private ListView lvActivities;
    @FXML
    private TableView tvPerson;

    private TableColumn<Person, String> tcFirstname = null;
    private TableColumn<Person, String> tcLastname = null;
    private Set<Activity> activitySet = new HashSet<>();
    private Set<Person> personSet = new HashSet<>();
    private Activity currentActivity = null;

    public void setActivitySet(Set<Activity> activities){
        this.activitySet = activities;
        this.cbSeasons.getSelectionModel().select(0);
        filterBySeason((Season) cbSeasons.getSelectionModel().getSelectedItem());
    }

    public void setPersonSet(Set<Person> people){
        this.personSet = people;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureChoices();
        configureListView();

        this.createColumns();
        this.configureTableView();
    }

    private void configureListView() {
        this.lvActivities.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.lvActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object newObject) {
                currentActivity = (Activity) newObject;
                showAssignedPeople();
            }
        });
    }

    private void showAssignedPeople() {
        if(currentActivity != null){
            List<Person> people = personSet.stream().filter(person -> { return person.getIdActivity() == currentActivity.getId();}).collect(Collectors.toList());

            ObservableList<Person> studentFxObservableList;
            studentFxObservableList = FXCollections.observableList(new ArrayList<>(people));
            this.tvPerson.setItems(studentFxObservableList);
        } else {
            this.tvPerson.getItems().clear();
        }
    }

    private void configureChoices() {
        Dao<Season> daoSeason = new SeasonDBDao();
        List<Season> seasons = daoSeason.getAll();
        this.cbSeasons.getItems().addAll(seasons);

        this.cbSeasons.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                Season s = (Season) cbSeasons.getItems().get(newVal.intValue());
                filterBySeason(s);
            }
        });
    }

    public void filterBySeason(Season s){
        List<Activity> activities = activitySet.stream().filter((activity -> { return activity.getIdSeason() == s.getId(); })).collect(Collectors.toList());

        ObservableList<Activity> studentFxObservableList;
        studentFxObservableList = FXCollections.observableList(activities);
        lvActivities.setItems(studentFxObservableList);
    }

    private void createColumns() {
        tcFirstname = new TableColumn<>("Firstname");
        tcLastname = new TableColumn<>("Lastname");
        tcFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        tcLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        this.tvPerson.getColumns().addAll(tcFirstname, tcLastname);
    }

    private void configureTableView() {
        this.tvPerson.setEditable(true);

        tcFirstname.setCellFactory(TextFieldTableCell.forTableColumn());
        tcFirstname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                try{
                    Integer.parseInt(t.getNewValue());
                    tvPerson.refresh();
                }catch (Exception ex){
                    Person currentPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    currentPerson.setFirstname(t.getNewValue());
                    currentPerson.update(new PersonDBDao());
                }
            }
        });

        tcLastname.setCellFactory(TextFieldTableCell.forTableColumn());
        tcLastname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                try{
                    Integer.parseInt(t.getNewValue());
                    tvPerson.refresh();
                }catch (Exception ex){
                    Person currentPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    currentPerson.setLastname(t.getNewValue());
                    currentPerson.update(new PersonDBDao());}
            }
        });
    }
}
