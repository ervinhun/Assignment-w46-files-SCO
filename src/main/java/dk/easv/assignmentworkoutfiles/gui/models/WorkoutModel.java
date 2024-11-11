package dk.easv.assignmentworkoutfiles.gui.models;

import dk.easv.assignmentworkoutfiles.be.OnePWorkout;
import dk.easv.assignmentworkoutfiles.be.Routine;
import dk.easv.assignmentworkoutfiles.be.User;
import dk.easv.assignmentworkoutfiles.be.UserWorkout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dk.easv.assignmentworkoutfiles.bll.*;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class WorkoutModel {
    private final WorkoutManager workoutManager = new WorkoutManager();
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<UserWorkout> workouts = FXCollections.observableArrayList();
    private final ObservableList<Routine> routines = FXCollections.observableArrayList();

    public void loadUsers() throws IOException {
        users.setAll(workoutManager.getUsers());
    }

    // Get observable list of users
    public ObservableList<User> getUsers() {
        return users;
    }

    // Add a new user
    public void addUser(String userName) throws IOException {
        User newUser = workoutManager.addUser(new User(-1, userName)); // create user with fake id, get user back with right id
        users.add(newUser);
    }

    public void deleteUser(User user) throws IOException {
        workoutManager.deleteUser(user);
        users.remove(user);
    }

    public void updateUser(User user) throws IOException {
        workoutManager.updateUser(user);
        users.remove(user);
        users.add(user);
    }

    public void loadWorkout() throws IOException {
        workouts.setAll(workoutManager.getWorkouts());
    }

    public ObservableList<UserWorkout> getWorkouts() {
        return workouts;
    }

    public void loadRoutine() throws IOException {
        routines.setAll(workoutManager.getRoutines());
    }

    public ObservableList<Routine> getRoutines() {
        return routines;
    }

    public void addRoutine(String description, String details, int time) throws IOException {
        Routine newRoutine = workoutManager.addRoutine(new Routine(-1, description, details, time)); // create user with fake id, get user back with right id
        routines.add(newRoutine);
    }
    public void deleteRoutine(Routine routine) throws IOException {
        workoutManager.deleteRoutine(routine);
        routines.remove(routine);
    }

    public void updateRoutine(Routine selectedRoutine) throws IOException {
        workoutManager.updateRoutine(selectedRoutine);
        routines.remove(selectedRoutine);
        routines.add(selectedRoutine);
    }

    public void addWorkout(int userID, int workoutID, String date) throws IOException {
        UserWorkout newWorkout = workoutManager.addWorkout(new UserWorkout(-1, userID, workoutID, date)); // create user with fake id, get user back with right id
        workouts.add(newWorkout);
    }

    public void deleteWorkout(UserWorkout selectedWorkOut) throws IOException {
        workoutManager.deleteWorkout(selectedWorkOut);
        workouts.remove(selectedWorkOut);
    }

    public void updateWorkout(UserWorkout selectedWorkOut) throws IOException {
        workoutManager.updateWorkout(selectedWorkOut);
        workouts.remove(selectedWorkOut);
        workouts.add(selectedWorkOut);
    }

    public ObservableList<OnePWorkout> getOnePersonWorkout(User user) throws IOException {
        int id = user.getId();
        List<Routine> routines = workoutManager.getRoutines();
        ObservableList<OnePWorkout> onePersonWorkouts = FXCollections.observableArrayList();
        for (UserWorkout userWorkout : workouts) {
            if (userWorkout.getUserID() == id) {
                String workoutDetail = "";
                for (Routine routine : routines) {
                    if (routine.getId() == userWorkout.getWorkoutID())
                        workoutDetail = routine.getDetails();
                }
                onePersonWorkouts.add (new OnePWorkout(user.getId(), user.getUsername(), workoutDetail, userWorkout.getDate()));
            }
        }
        return onePersonWorkouts;
    }
}
