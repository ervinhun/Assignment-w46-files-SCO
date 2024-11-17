package dk.easv.assignmentworkoutfiles.dal;

import dk.easv.assignmentworkoutfiles.be.UserWorkout;
import dk.easv.assignmentworkoutfiles.exceptioins.WorkoutException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkoutDAO implements IWorkoutDAO {
    private final String splitChar = ";";
    private final Path filePath; // hardcoded path not good

    public WorkoutDAO() {
        filePath = Paths.get("user_workouts.csv");
    }

    // Load users from the CSV file
    @Override
    public List<UserWorkout> getAll() throws WorkoutException {
        List<UserWorkout> workouts = new ArrayList<>();
        if (Files.exists(filePath)) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (String line : lines) {
                String[] parts = line.split(splitChar);
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        int userId = Integer.parseInt(parts[1].trim());
                        int workoutId = Integer.parseInt(parts[2].trim());
                        String date = parts[3].trim();
                        workouts.add(new UserWorkout(id, userId, workoutId, date));
                    } catch (NumberFormatException e) {
                        // Log the error instead of printing it
                        Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.WorkoutDAO.class.getName()).log(Level.WARNING, "Invalid user ID format: " + parts[0], e);
                    }
                } else {
                    Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.WorkoutDAO.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                }
            }
        }
        return workouts;
    }

    // Save (overwrite) the entire user list to the CSV file
    @Override
    public void clearAndSave(List<UserWorkout> workouts) throws WorkoutException {
        List<String> lines = new ArrayList<>();
        for (UserWorkout workout : workouts) {
            lines.add(workout.getId() + splitChar + workout.getUserID() + splitChar
                    + workout.getWorkoutID() + splitChar + workout.getDate());
        }
        try {
            Files.write(filePath, lines); // Overwrites the file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Add a new user (append with no id in User)
    @Override
    public UserWorkout add(UserWorkout workout) throws WorkoutException {
        workout.setId(getNextId());
        String newWorkout = workout.getId() + splitChar + workout.getUserID() + splitChar
                + workout.getWorkoutID() + splitChar + workout.getDate();
        try {
            Files.write(filePath, List.of(newWorkout), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workout;
    }

    // Delete a user by ID (removes the user and rewrites the file)
    @Override
    public void delete(UserWorkout workout) throws WorkoutException {
        List<UserWorkout> workouts = getAll();
        workouts.removeIf(u -> u.getId() == workout.getId());
        clearAndSave(workouts);
    }

    // Update a user (updates the user if it exists and rewrites the file)
    @Override
    public void update(UserWorkout workout) throws WorkoutException {
        List<UserWorkout> workouts = getAll();
        boolean workoutFound = false;
        for (int i = 0; i < workouts.size(); i++) {
            if (workouts.get(i).getId() == workout.getId()) {
                workouts.set(i, workout); // Update the user
                workoutFound = true;
                break;
            }
        }
        if (!workoutFound) {
            Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.WorkoutDAO.class.getName()).log(Level.WARNING, "The Workout with ID " + workout.getId() + " not found for update.");
        }
        clearAndSave(workouts); // Save the updated list
    }

    // Get the next available user ID
    @Override
    public int getNextId() throws WorkoutException {
        List<UserWorkout> workouts = getAll();
        int maxId = 0;
        for (UserWorkout workout : workouts) {
            if (workout.getId() > maxId) {
                maxId = workout.getId();
            }
        }
        return maxId + 1;
    }
}


