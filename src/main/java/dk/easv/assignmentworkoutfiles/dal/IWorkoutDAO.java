package dk.easv.assignmentworkoutfiles.dal;

import dk.easv.assignmentworkoutfiles.be.UserWorkout;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;

import java.util.List;

public interface IWorkoutDAO {
    // Load users from the CSV file
    List<UserWorkout> getAll() throws WorkoutException;

    // Save (overwrite) the entire user list to the CSV file
    void clearAndSave(List<UserWorkout> workouts) throws WorkoutException;

    // Add a new user (append with no id in User)
    UserWorkout add(UserWorkout workout) throws WorkoutException;

    // Delete a user by ID (removes the user and rewrites the file)
    void delete(UserWorkout workout) throws WorkoutException;

    // Update a user (updates the user if it exists and rewrites the file)
    void update(UserWorkout workout) throws WorkoutException;

    // Get the next available user ID
    int getNextId() throws WorkoutException;
}
