package dk.easv.assignmentworkoutfiles.bll;

import dk.easv.assignmentworkoutfiles.be.OnePWorkout;
import dk.easv.assignmentworkoutfiles.be.Routine;
import dk.easv.assignmentworkoutfiles.be.User;
import dk.easv.assignmentworkoutfiles.be.UserWorkout;
import dk.easv.assignmentworkoutfiles.dal.*;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkoutManager {
    private final IUserDAO userDAO = new UserDAO();
    private final IWorkoutDAO workoutDAO = new WorkoutDAO();
    private final IRoutineDAO routineDAO = new RoutineDAO();


    // Add a new user
    public User addUser(User user) throws WorkoutException {
        if (sanityCheckUserName(user.getUsername())) {
            return userDAO.add(user);
        }
        return null;
    }

    // Update an existing user
    public void updateUser(User updatedUser) throws WorkoutException {
        if (sanityCheckUserName(updatedUser.getUsername())) {
            userDAO.update(updatedUser);
        }
    }

    // Get all users
    public List<User> getUsers() throws WorkoutException {
        return userDAO.getAll();
    }

    // Delete a user
    public void deleteUser(User user) throws WorkoutException {
        userDAO.delete(user);
    }




    /**
     * User workout part
     */
    // Get all users
    public List<UserWorkout> getWorkouts() throws WorkoutException {
        return workoutDAO.getAll();
    }

    public UserWorkout addWorkout(UserWorkout userWorkout) throws WorkoutException {
        if (checkForCorrectWorkout(userWorkout.getUserID(), userWorkout.getWorkoutID(), userWorkout.getDate())) {
            return workoutDAO.add(userWorkout);
        }
        return null;
    }

    public void deleteWorkout(UserWorkout selectedWorkOut) throws WorkoutException {
        workoutDAO.delete(selectedWorkOut);
    }

    public void updateWorkout(UserWorkout selectedWorkOut) throws WorkoutException {
        workoutDAO.update(selectedWorkOut);
    }






    /**
     * Routine part
     */
    // Get all users
    public List<Routine> getRoutines() throws WorkoutException {
        return routineDAO.getAll();
    }
    public Routine addRoutine(Routine routine) throws WorkoutException {
        if (!routine.getDescription().isEmpty()) {
            return routineDAO.add(routine);
        }
        return null;
    }

    public void deleteRoutine(Routine routine) throws WorkoutException {
        routineDAO.delete(routine);
    }

    public void updateRoutine(Routine selectedRoutine) throws WorkoutException {
        if (!selectedRoutine.getDescription().isEmpty() && !selectedRoutine.getDetails().isEmpty()) {
            routineDAO.update(selectedRoutine);
        }
    }

    // Sanity check, just an example of some logic
    private boolean sanityCheckUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name is empty or null");
        }
        if (userName.length() < 2 || userName.length() > 20) {
            throw new IllegalArgumentException("User name must be between 2 and 20 characters");
        }
        if (!userName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("User name contains invalid characters");
        }
        return true;
    }






    /**
     * Checking the info for correctness
     */

    private boolean checkForCorrectWorkout(int userID, int workoutID, String date) throws WorkoutException {
        WorkoutDAO workoutDAO = new WorkoutDAO();
        List<UserWorkout> workouts = null;
        try {
            workouts = new ArrayList<>(workoutDAO.getAll());
        } catch (WorkoutException e) {
            throw new RuntimeException(e);
        }
        UserDAO userDAO = new UserDAO();
        List<User> users = null;
        try {
            users = new ArrayList<>(userDAO.getAll());
        } catch (WorkoutException e) {
            throw new RuntimeException(e);
        }
        List<Integer> userIDs = new ArrayList<>();
        List<Integer> workoutIDs = new ArrayList<>();
        String[] day = date.split("-");
        if (date == null || date == "" || day.length != 3) {
            throw new IllegalArgumentException("Date is empty or null");
        }
        else if (day[2].length()<2)
            day[2] = "0" + day[2];
        date = day[0] + "-" + day[1] + "-" + day[2];
        for (User u: users) {
            userIDs.add(u.getId());
        }
        for (UserWorkout u: workouts) {
            workoutIDs.add(u.getId());
        }
        if (userIDs.isEmpty() || !userIDs.contains(userID)) {
            throw new IllegalArgumentException("User id is empty or does not contain userId: " + userID);
        }
        if (workoutIDs.isEmpty() || !workoutIDs.contains(workoutID)) {
            throw new IllegalArgumentException("Workout id is empty or does not contain workoutId: " + workoutID);
        }
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(date);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(date + " date format is invalid");
        }
        return true;
    }
}
