package ca.unb.cs3035.project;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class AddMeeting {
    static MeetingWidget newMeeting;

    public static MeetingWidget getMeetingDetails() throws IOException {
        newMeeting = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-meeting.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Add Meeting");

        Scene secondaryScene = new Scene(root, 700, 650);

        TextField meetingNameTextField = (TextField) secondaryScene.lookup("#meetingNameInput");
        TextArea meetingDescriptionTextArea = (TextArea) secondaryScene.lookup("#meetingDescriptionInput");

        DatePicker datePicker = (DatePicker) secondaryScene.lookup("#meetingDateInput");


        String hours[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] minutes = new String[60];
        String[] AMPM = {"AM", "PM"};

        for(int i=0; i<60; i++) {
            if(i<10) {
                minutes[i] = "0" + i;
            }
            else {
                minutes[i] = String.valueOf(i);
            }
        }

        ComboBox startHoursComboBox = (ComboBox) secondaryScene.lookup("#meetingStartHours");
        startHoursComboBox.setItems(FXCollections.observableArrayList(hours));

        ComboBox startMinutesComboBox = (ComboBox) secondaryScene.lookup("#meetingStartMinutes");
        startMinutesComboBox.setItems(FXCollections.observableArrayList(minutes));

        ComboBox startAMPMComboBox = (ComboBox) secondaryScene.lookup("#meetingStartAMPM");
        startAMPMComboBox.setItems(FXCollections.observableArrayList(AMPM));

        ComboBox endHoursComboBox = (ComboBox) secondaryScene.lookup("#meetingEndHours");
        endHoursComboBox.setItems(FXCollections.observableArrayList(hours));

        ComboBox endMinutesComboBox = (ComboBox) secondaryScene.lookup("#meetingEndMinutes");
        endMinutesComboBox.setItems(FXCollections.observableArrayList(minutes));

        ComboBox endAMPMComboBox = (ComboBox) secondaryScene.lookup("#meetingEndAMPM");
        endAMPMComboBox.setItems(FXCollections.observableArrayList(AMPM));

        RadioButton meetingCompleted = (RadioButton) secondaryScene.lookup("#meetingCompleted");
        RadioButton meetingInProgress = (RadioButton) secondaryScene.lookup("#meetingInProgress");
        RadioButton meetingNotStarted = (RadioButton) secondaryScene.lookup("#meetingNotStarted");



        Button meetingSubmit = (Button) secondaryScene.lookup("#meetingSubmit");
        Button meetingCancel = (Button) secondaryScene.lookup("#meetingCancel");



        meetingSubmit.setOnAction(e -> {
            int duration = 0;

            if(startAMPMComboBox.getValue().toString()==endAMPMComboBox.getValue().toString()) {
                if(startHoursComboBox.getValue().toString()!="12" || (startHoursComboBox.getValue().toString()=="12" && endHoursComboBox.getValue().toString()=="12")) {
                    duration = (Integer.valueOf((String) endHoursComboBox.getValue()) - Integer.valueOf((String) startHoursComboBox.getValue()))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
                else {
                    duration = (Integer.valueOf((String) endHoursComboBox.getValue()) - (Integer.valueOf((String) startHoursComboBox.getValue())-12))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
            }
            else if(startAMPMComboBox.getValue().toString()=="AM" && endAMPMComboBox.getValue().toString()=="PM") {
                if(endHoursComboBox.getValue().toString()!="12" && startHoursComboBox.getValue().toString()!="12") {
                    duration = ((Integer.valueOf((String) endHoursComboBox.getValue()) + 12) - Integer.valueOf((String) startHoursComboBox.getValue()))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
                else if(endHoursComboBox.getValue().toString()=="12" && startAMPMComboBox.getValue().toString()!="12") {
                    duration = ((Integer.valueOf((String) endHoursComboBox.getValue()) + 12) - (Integer.valueOf((String) startHoursComboBox.getValue()) - 12))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
                else if(endHoursComboBox.getValue().toString()!="12" && startAMPMComboBox.getValue().toString()=="12") {
                    duration = (Integer.valueOf((String) endHoursComboBox.getValue()) - Integer.valueOf((String) startHoursComboBox.getValue()))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
                else if(endHoursComboBox.getValue().toString()=="12" && startAMPMComboBox.getValue().toString()=="12") {
                    duration = (Integer.valueOf((String) endHoursComboBox.getValue()) - (Integer.valueOf((String) startHoursComboBox.getValue()) - 12))*60 + (Integer.valueOf(endMinutesComboBox.getValue().toString()) - Integer.valueOf(startMinutesComboBox.getValue().toString()));
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            LocalDate date = datePicker.getValue();

            String meetingStatus;
            if(meetingCompleted.isSelected()) {
                meetingStatus = "Completed";
            }
            else if(meetingInProgress.isSelected()) {
                meetingStatus = "In Progress";
            }
            else {
                meetingStatus = "Not Started";
            }

            newMeeting = new MeetingWidget(meetingNameTextField.getText(), meetingDescriptionTextArea.getText(), formatter.format(date), startHoursComboBox.getValue().toString(), startMinutesComboBox.getValue().toString(), startAMPMComboBox.getValue().toString(), endHoursComboBox.getValue().toString(), endMinutesComboBox.getValue().toString(), endAMPMComboBox.getValue().toString(),  String.valueOf(duration), meetingStatus);
            secondaryStage.close();
        });

        meetingCancel.setOnAction(e -> {
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();

        if(newMeeting!=null) {
            return newMeeting;
        }
        return null;
    }
}
