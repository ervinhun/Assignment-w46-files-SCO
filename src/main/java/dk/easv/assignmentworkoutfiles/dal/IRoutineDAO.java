package dk.easv.assignmentworkoutfiles.dal;

import dk.easv.assignmentworkoutfiles.be.Routine;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;

import java.util.List;

public interface IRoutineDAO {
    // Load users from the CSV file
    List<Routine> getAll() throws WorkoutException;

    // Save (overwrite) the entire user list to the CSV file
    void clearAndSave(List<Routine> routines) throws WorkoutException;

    // Add a new user (append with no id in User)
    Routine add(Routine routine) throws WorkoutException;

    // Delete a user by ID (removes the user and rewrites the file)
    void delete(Routine routine) throws WorkoutException;

    // Update a user (updates the user if it exists and rewrites the file)
    void update(Routine routine) throws WorkoutException;

    // Get the next available user ID
    int getNextId() throws WorkoutException;
}
