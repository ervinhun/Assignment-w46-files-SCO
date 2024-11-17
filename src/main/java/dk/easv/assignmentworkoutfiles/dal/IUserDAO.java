package dk.easv.assignmentworkoutfiles.dal;

import dk.easv.assignmentworkoutfiles.be.User;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;

import java.util.List;

public interface IUserDAO {
    // Load users from the CSV file
    List<User> getAll() throws WorkoutException;

    // Save (overwrite) the entire user list to the CSV file
    void clearAndSave(List<User> users) throws WorkoutException;

    // Add a new user (append with no id in User)
    User add(User user) throws WorkoutException;

    // Delete a user by ID (removes the user and rewrites the file)
    void delete(User user) throws WorkoutException;

    // Update a user (updates the user if it exists and rewrites the file)
    void update(User user) throws WorkoutException;

    // Get the next available user ID
    int getNextId() throws WorkoutException;
}
