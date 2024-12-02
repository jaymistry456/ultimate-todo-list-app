package ca.unb.cs3035.project;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskListModel {

    SimpleListProperty<TaskWidget> todaysTaskListProperty;
    SimpleListProperty<TaskWidget> upcomingTaskListProperty;
    SimpleListProperty<TaskWidget> starredTaskListProperty;
    SimpleListProperty<TaskWidget> deletedTaskListProperty;


    public TaskListModel() {
        ArrayList<TaskWidget> todaysTaskList = new ArrayList<TaskWidget>();
        ObservableList<TaskWidget> observableTodaysTaskList = (ObservableList<TaskWidget>) FXCollections.observableArrayList(todaysTaskList);
        todaysTaskListProperty = new SimpleListProperty<TaskWidget>(observableTodaysTaskList);

        ArrayList<TaskWidget> upcomingTaskList = new ArrayList<TaskWidget>();
        ObservableList<TaskWidget> observableUpcomingTaskList = (ObservableList<TaskWidget>) FXCollections.observableArrayList(upcomingTaskList);
        upcomingTaskListProperty = new SimpleListProperty<TaskWidget>(observableUpcomingTaskList);

        ArrayList<TaskWidget> starredTaskList = new ArrayList<TaskWidget>();
        ObservableList<TaskWidget> observableStarredTaskList = (ObservableList<TaskWidget>) FXCollections.observableArrayList(starredTaskList);
        starredTaskListProperty = new SimpleListProperty<TaskWidget>(observableStarredTaskList);

        ArrayList<TaskWidget> deletedTaskList = new ArrayList<TaskWidget>();
        ObservableList<TaskWidget> observableDeletedTaskList = (ObservableList<TaskWidget>) FXCollections.observableArrayList(deletedTaskList);
        deletedTaskListProperty = new SimpleListProperty<TaskWidget>(observableDeletedTaskList);
    }

    public SimpleListProperty<TaskWidget> todaysTaskListProperty() {
        return this.todaysTaskListProperty;
    }
    public SimpleListProperty<TaskWidget> upcomingTaskListProperty() {
        return this.upcomingTaskListProperty;
    }
    public SimpleListProperty<TaskWidget> starredTaskListProperty() {
        return this.starredTaskListProperty;
    }
    public SimpleListProperty<TaskWidget> deletedTaskListProperty() {
        return this.deletedTaskListProperty;
    }

    public void addTaskInTodaysTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        todaysTaskListProperty.add(new TaskWidget(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInUpcomingTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        upcomingTaskListProperty.add(new TaskWidget(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInStarredListTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        starredTaskListProperty.add(new TaskWidget(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInDeletedTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        deletedTaskListProperty.add(new TaskWidget(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

}
