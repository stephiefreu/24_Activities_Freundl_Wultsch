package at.htlvillach.gui;

import at.htlvillach.bll.Activity;
import at.htlvillach.bll.Person;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
        ObservableList<Activity> studentFxObservableList;
        studentFxObservableList = FXCollections.observableList(new ArrayList<>(activities));
        this.lvActivities.setItems(studentFxObservableList);
    }

    public void setPersonSet(Set<Person> people){
        this.personSet = people;
        ObservableList<Person> studentFxObservableList;
        studentFxObservableList = FXCollections.observableList(new ArrayList<>(people));
        this.tvPerson.setItems(studentFxObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureChoices();
        configureListView();

        this.createColumns();
        this.configureTableView();
    }

    private void configureListView() {
        //TODO: set standard Activities with standard season
        this.lvActivities.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.lvActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object newObject) {
                currentActivity = (Activity) newObject;
                showAssignedPeople();
            }
        });
    }

    private void configureChoices() {
        //TODO: read shit from DB
        this.cbSeasons.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //TODO: set Activity-List with filtered season
            }
        });
    }

    private void showAssignedPeople() {
        //TODO: read people with assigned activity from DB
        //TODO: display shit in tableview
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
                    ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstname(t.getNewValue());
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
                    ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastname(t.getNewValue());
                }
            }
        });
    }
}
