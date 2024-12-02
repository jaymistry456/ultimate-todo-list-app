package ca.unb.cs3035.project;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MeetingListDeletedView extends Pane {

    public MeetingListDeletedView() {
        Main.meetingListModel.deletedMeetingListProperty().addListener(new ListChangeListener<MeetingWidget>() {
            @Override
            public void onChanged(Change<? extends MeetingWidget> c) {
                try {
                    displayList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void displayList() throws IOException {
        this.getChildren().clear();

        Pane root = new Pane();
        root.setPrefSize(1400, 585);

        ListView<HBox> meetingListView = new ListView<>();
        meetingListView.setPrefSize(1350, 500);
        meetingListView.setLayoutX(25);
        meetingListView.setLayoutY(5);
        meetingListView.setEditable(true);

        HBox titles = new HBox(15);

        Label meetingNameTitle = new Label("Meeting Name");
        meetingNameTitle.setPrefWidth(150);
        meetingNameTitle.setFont(Font.font("Arial", 20));

        Label meetingDescriptionTitle = new Label("Meeting Description");
        meetingDescriptionTitle.setPrefWidth(200);
        meetingDescriptionTitle.setFont(Font.font("Arial", 20));
        HBox.setHgrow(meetingDescriptionTitle, Priority.ALWAYS);

        Label meetingDateTitle = new Label("Meeting Date");
        meetingDateTitle.setPrefWidth(150);
        meetingDateTitle.setFont(Font.font("Arial", 20));

        Label meetingStartTimeTitle = new Label("Start Time");
        meetingStartTimeTitle.setPrefWidth(120);
        meetingStartTimeTitle.setFont(Font.font("Arial", 20));

        Label meetingEndTimeTitle = new Label("End Time");
        meetingEndTimeTitle.setPrefWidth(120);
        meetingEndTimeTitle.setFont(Font.font("Arial", 20));

        Label meetingDurationTitle = new Label("Duration");
        meetingDurationTitle.setPrefWidth(100);
        meetingDurationTitle.setFont(Font.font("Arial", 20));

        Label meetingStatusTitle = new Label("Status");
        meetingStatusTitle.setPrefWidth(120);
        meetingStatusTitle.setFont(Font.font("Arial", 20));

        titles.getChildren().addAll(meetingNameTitle, meetingDescriptionTitle, meetingDateTitle, meetingStartTimeTitle, meetingEndTimeTitle, meetingDurationTitle, meetingStatusTitle);

        meetingListView.getItems().add(titles);

        for(MeetingWidget m: Main.meetingListModel.deletedMeetingListProperty) {
            HBox hBox = new HBox(15);

            Label meetingName = new Label(m.getMeetingName());
            meetingName.setPrefWidth(150);
            meetingName.setFont(Font.font("Arial", 20));

            Label meetingDescription = new Label(m.getMeetingDescription());
            meetingDescription.setPrefWidth(200);
            meetingDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(meetingDescription, Priority.ALWAYS);

            Label meetingDate = new Label(m.getMeetingDate());
            meetingDate.setPrefWidth(150);
            meetingDate.setFont(Font.font("Arial", 20));

            Label meetingStartTime = new Label(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
            meetingStartTime.setPrefWidth(120);
            meetingStartTime.setFont(Font.font("Arial", 20));

            Label meetingEndTime = new Label(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
            meetingEndTime.setPrefWidth(120);
            meetingEndTime.setFont(Font.font("Arial", 20));

            Label meetingDuration = new Label(m.getMeetingDuration());
            meetingDuration.setPrefWidth(100);
            meetingDuration.setFont(Font.font("Arial", 20));

            Label meetingStatus = new Label(m.getMeetingStatus());
            meetingStatus.setPrefWidth(120);
            meetingStatus.setFont(Font.font("Arial", 20));

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);

            viewButton.setOnAction(e -> {
                try {
                    ViewMeeting.displayMeetingDetails(m);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button retoreButton = new Button("Retore");
            retoreButton.setPrefWidth(75);
            retoreButton.setPrefHeight(30);
            retoreButton.setOnAction(e -> {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                if(m.getMeetingDate().substring(4,6).equals(formatter.format(date))) {
                    Main.meetingListModel.addMeetingInTodaysMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                else {
                    Main.meetingListModel.addMeetingInUpcomingMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                Main.meetingListModel.deletedMeetingListProperty.remove(m);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(m.getMeetingName(), true);
                    if(deleteTask) {
                        Main.meetingListModel.deletedMeetingListProperty.remove(m);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(meetingName, meetingDescription, meetingDate, meetingStartTime, meetingEndTime, meetingDuration, meetingStatus, viewButton, retoreButton, deleteButton);

            meetingListView.getItems().add(hBox);
        }

        HBox emptyButtonHBox = new HBox(5);

        Region regionOne = new Region();
        Button emptyButton = new Button("Empty Bin");
        emptyButton.setPrefSize(200, 40);
        emptyButton.setTextFill(Color.WHITE);
        emptyButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
        emptyButton.setAlignment(Pos.CENTER);
        emptyButton.setId("emptyBin");
        emptyButton.setOnAction(event -> {
            try {
                Boolean emptyBin = EmptyBin.emptyBin();
                if(emptyBin) {
                    Main.meetingListModel.deletedMeetingListProperty.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Region regionTwo = new Region();

        emptyButtonHBox.getChildren().addAll(regionOne, emptyButton, regionTwo);

        HBox.setHgrow(regionOne, Priority.ALWAYS);
        HBox.setHgrow(regionTwo, Priority.ALWAYS);

        emptyButton.setLayoutX(600);
        emptyButton.setLayoutY(512);

        root.getChildren().addAll(meetingListView, emptyButton);

        this.getChildren().add(root);
    }
}

