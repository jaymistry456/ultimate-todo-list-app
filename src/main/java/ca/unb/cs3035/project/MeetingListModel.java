package ca.unb.cs3035.project;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MeetingListModel {
    SimpleListProperty<MeetingWidget> todaysMeetingListProperty;
    SimpleListProperty<MeetingWidget> upcomingMeetingListProperty;
    SimpleListProperty<MeetingWidget> starredMeetingListProperty;
    SimpleListProperty<MeetingWidget> deletedMeetingListProperty;


    public MeetingListModel() {
        ArrayList<MeetingWidget> todaysMeetingList = new ArrayList<MeetingWidget>();
        ObservableList<MeetingWidget> observableTodaysMeetingList = (ObservableList<MeetingWidget>) FXCollections.observableArrayList(todaysMeetingList);
        todaysMeetingListProperty = new SimpleListProperty<MeetingWidget>(observableTodaysMeetingList);

        ArrayList<MeetingWidget> upcomingMeetingList = new ArrayList<MeetingWidget>();
        ObservableList<MeetingWidget> observableUpcomingMeetingList = (ObservableList<MeetingWidget>) FXCollections.observableArrayList(upcomingMeetingList);
        upcomingMeetingListProperty = new SimpleListProperty<MeetingWidget>(observableUpcomingMeetingList);

        ArrayList<MeetingWidget> starredMeetingList = new ArrayList<MeetingWidget>();
        ObservableList<MeetingWidget> observableStarredMeetingList = (ObservableList<MeetingWidget>) FXCollections.observableArrayList(starredMeetingList);
        starredMeetingListProperty = new SimpleListProperty<MeetingWidget>(observableStarredMeetingList);

        ArrayList<MeetingWidget> deletedMeetingList = new ArrayList<MeetingWidget>();
        ObservableList<MeetingWidget> observableDeletedMeetingList = (ObservableList<MeetingWidget>) FXCollections.observableArrayList(deletedMeetingList);
        deletedMeetingListProperty = new SimpleListProperty<MeetingWidget>(observableDeletedMeetingList);
    }

    public SimpleListProperty<MeetingWidget> todaysMeetingListProperty() {
        return this.todaysMeetingListProperty;
    }
    public SimpleListProperty<MeetingWidget> upcomingMeetingListProperty() {
        return this.upcomingMeetingListProperty;
    }
    public SimpleListProperty<MeetingWidget> starredMeetingListProperty() {
        return this.starredMeetingListProperty;
    }
    public SimpleListProperty<MeetingWidget> deletedMeetingListProperty() {
        return this.deletedMeetingListProperty;
    }

    public void addMeetingInTodaysMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        todaysMeetingListProperty.add(new MeetingWidget(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInUpcomingMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        upcomingMeetingListProperty.add(new MeetingWidget(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInStarredMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        starredMeetingListProperty.add(new MeetingWidget(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInDeletedMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        deletedMeetingListProperty.add(new MeetingWidget(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }
}
