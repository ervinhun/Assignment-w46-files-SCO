package dk.easv.assignmentworkoutfiles.gui.controllers;

import dk.easv.assignmentworkoutfiles.be.OnePWorkout;
import dk.easv.assignmentworkoutfiles.be.Routine;
import dk.easv.assignmentworkoutfiles.be.User;
import dk.easv.assignmentworkoutfiles.be.UserWorkout;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;
import dk.easv.assignmentworkoutfiles.gui.models.WorkoutModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class WorkoutAppController implements Initializable {
    @FXML
    private ListView<User> lstUsers;

    @FXML
    private ListView<Routine> lstRoutines;

    @FXML
    private ListView<UserWorkout> lstUserWorkouts;

    @FXML
    private ListView<OnePWorkout> lstOneUserWorkout;

    @FXML
    private Label lblUserName;

    @FXML
    private Button btnClose;

    private final WorkoutModel workoutModel = new WorkoutModel();

    public void onLoadUsersClick(ActionEvent actionEvent) {
        try {
            workoutModel.loadUsers();
        } catch (WorkoutException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    public void onLoadRoutinesClick(ActionEvent actionEvent) {
        try {
            workoutModel.loadRoutine();
        } catch (WorkoutException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    public void onLoadUserWorkoutsClick(ActionEvent actionEvent) {
        try {
            workoutModel.loadWorkout();
        } catch (WorkoutException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstUsers.setItems(workoutModel.getUsers());
        lstUserWorkouts.setItems(workoutModel.getWorkouts());
        lstRoutines.setItems(workoutModel.getRoutines());
        lstUsers.setOnMouseClicked(event -> {
            try {
                handleDoubleClick(event, lstUsers);
            } catch (WorkoutException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleDoubleClick(MouseEvent event, ListView<User> lstUsers) throws WorkoutException {
        if (lstUsers.getSelectionModel().getSelectedItem() != null) {
            if (!lstRoutines.getItems().isEmpty() && !lstUserWorkouts.getItems().isEmpty()) {
                showFourthList(true);
                String[] name = lstUsers.getSelectionModel().getSelectedItem().toString().split(",");
                String nameToShow = name[1].trim();
                lblUserName.setText("User: " + nameToShow);
                lstOneUserWorkout.setItems(workoutModel.getOnePersonWorkout(lstUsers.getSelectionModel().getSelectedItem()));
            }
            else throw new RuntimeException("Lists must be loaded");
        }
        //workoutModel.getOnePersonWorkout();
        //lstOneUserWorkout.setItems(workoutModel.getOnePersonWorkout());

    }

    public void onAddUserClick(ActionEvent actionEvent) {
        try {
            workoutModel.addUser(getRandomName());
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onAddRoutineClick (ActionEvent actionEvent) {
        try {
            String[] toPut = getRandomRoutine();
            workoutModel.addRoutine(toPut[0], toPut[1], getRandomRoutineTime());
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onAddWorkoutClick(ActionEvent actionEvent) {
        try {
            int userID = getRandomUserID();
            int workoutID = getRandomWorkoutID();
            String date = getRnadomDate();
            workoutModel.addWorkout(userID, workoutID, date);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }




    public void onDeleteRoutineClick (ActionEvent actionEvent) {
        Routine selectedRoutine = lstRoutines.getSelectionModel().getSelectedItem();
        try {
            workoutModel.deleteRoutine(selectedRoutine);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onDeleteWorkoutClick (ActionEvent actionEvent) {
        UserWorkout selectedWorkOut = lstUserWorkouts.getSelectionModel().getSelectedItem();
        try {
            workoutModel.deleteWorkout(selectedWorkOut);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onUpdateWorkoutClick (ActionEvent actionEvent) {
        UserWorkout selectedWorkOut = lstUserWorkouts.getSelectionModel().getSelectedItem();
        //int userID = selectedWorkOut.getUserID();
        selectedWorkOut.setWorkoutID(getRandomWorkoutID());
        selectedWorkOut.setDate(getRnadomDate());
        try {
            workoutModel.updateWorkout(selectedWorkOut);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onUpdateRoutineClick (ActionEvent actionEvent) {
        Routine selectedRoutine = lstRoutines.getSelectionModel().getSelectedItem();
        String [] updating = getRandomRoutine();
        selectedRoutine.setDescription(updating[0]);
        selectedRoutine.setDetails(updating[1]);
        selectedRoutine.setTime(getRandomRoutineTime());
        try {
            workoutModel.updateRoutine(selectedRoutine);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    public void onDeleteUserClick(ActionEvent actionEvent) {
        User selectedUser = lstUsers.getSelectionModel().getSelectedItem();
        try {
            workoutModel.deleteUser(selectedUser);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }
    public void onUpdateUserClick(ActionEvent actionEvent) {
        User selectedUser = lstUsers.getSelectionModel().getSelectedItem();
        selectedUser.setUsername(getRandomName());
        try {
            workoutModel.updateUser(selectedUser);
        } catch (WorkoutException e) {
            showAlertWindow(e);
        }
    }

    private String getRandomName(){
        String[] randomNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hannah", "Ivan", "Jack", "Karen", "Liam", "Mia", "Nathan", "Olivia", "Paul", "Quinn", "Rita", "Steve", "Tina", "Uma", "Victor", "Wendy", "Xander", "Yara", "Zane"};
        Random r = new Random();
        return randomNames[r.nextInt(randomNames.length)];
    }

    private int getRandomRoutineTime() {
        int[] randomTime = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
        Random r = new Random();
        return randomTime[r.nextInt(randomTime.length)];
    }

    private String[] getRandomRoutine() {
        String[] randomDescription = {"Partly Body Strength", "Cardio Endurance SUper Training",
                "Yoga", "HIIT Circuit Training", "Core Stability", "Upper Body Strength",
                "Leg Day Workout"};
        String[] randomDetails = {"Partly Body Strength", "Cardio Endurance Training",
                "Yoga Flow", "HIIT Circuit Training", "Core Stability", "Upper Body Strength",
                "Leg Day Workout"};
        Random r = new Random();
        int index = r.nextInt(randomDescription.length);
        String[] returnString = new String[2];
        returnString[0] = randomDescription[index];
        returnString[1] = randomDetails[index];
        return returnString;
    }

    private int getRandomUserID() {
        if (workoutModel.getUsers().isEmpty()) {
            throw new IllegalStateException("First you need to load the users");
        }
        List<User> users = workoutModel.getUsers();
        if (users.isEmpty()) {
            throw new IllegalStateException("No users available to select from.");
        }
        int[] userIDs = new int[users.size()];
        Random r = new Random();
        int index = 0;
        for (User user : users) {
            userIDs[index] = user.getId();
            index++;
        }
        return userIDs[r.nextInt(userIDs.length)];
    }

    private int getRandomWorkoutID() {
        if (workoutModel.getRoutines().isEmpty()) {
            throw new IllegalStateException("First you need to load the routines");
        }
        List<Routine> workouts = workoutModel.getRoutines();
        int[] workoutIDs = new int[workouts.size()];
        Random r = new Random();
        int index = 0;
        for (Routine workout : workouts) {
            workoutIDs[index] = workout.getId();
            index++;
        }
        return workoutIDs[r.nextInt(workoutIDs.length)];
    }

    private String getRnadomDate() {
        Random r = new Random();
        int day = r.nextInt(30);
        if (day == 0)
            day = 1;
        if (day < 10)
            return ("2024-11-0"+day);
        return ("2024-11-" + day);
    }

    private void showAlertWindow(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        alert.showAndWait();
    }

    private void showFourthList (boolean show) {
        lstOneUserWorkout.setVisible(show);
        lblUserName.setVisible(show);
        btnClose.setVisible(show);
    }

    public void onCloseButtonClick(ActionEvent event) {
        showFourthList(false);
        lblUserName.setText("");
    }
}