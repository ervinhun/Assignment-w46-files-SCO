package dk.easv.assignmentworkoutfiles.dal;

import dk.easv.assignmentworkoutfiles.be.Routine;
import dk.easv.assignmentworkoutfiles.be.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoutineDAO {
    private final String splitChar = ";";
    private final Path filePath; // hardcoded path not good

    public RoutineDAO() {
        filePath = Paths.get("routines.csv");
    }

    // Load users from the CSV file
    public List<Routine> getAll() throws IOException {
        List<Routine> routines = new ArrayList<>();
        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(splitChar);
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String description = parts[1];
                        String details = parts[2];
                        int minutes = Integer.parseInt(parts[3].trim());
                        routines.add(new Routine(id, description, details, minutes));
                    } catch (NumberFormatException e) {
                        // Log the error instead of printing it
                        Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.RoutineDAO.class.getName()).log(Level.WARNING, "Invalid routine ID format: " + parts[0], e);
                    }
                } else {
                    Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.RoutineDAO.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                }
            }
        }
        return routines;
    }

    // Save (overwrite) the entire user list to the CSV file
    public void clearAndSave(List<Routine> routines) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Routine routine : routines) {
            lines.add(routine.getId() + splitChar + routine.getDescription() + splitChar +
                    routine.getDetails() + splitChar + routine.getTime());
        }
        Files.write(filePath, lines); // Overwrites the file
    }

    // Add a new user (append with no id in User)
    public Routine add(Routine routine) throws IOException {
        routine.setId(getNextId());
        String newRoutine = routine.getId() + splitChar + routine.getDescription() + splitChar +
                routine.getDetails() + splitChar + routine.getTime();
        Files.write(filePath, List.of(newRoutine), StandardOpenOption.APPEND);
        return routine;
    }

    // Delete a user by ID (removes the user and rewrites the file)
    public void delete(Routine routine) throws IOException {
        List<Routine> routines = getAll();
        routines.removeIf(u -> u.getId() == routine.getId());
        clearAndSave(routines);
    }

    // Update a user (updates the user if it exists and rewrites the file)
    public void update(Routine routine) throws IOException {
        List<Routine> routines = getAll();
        boolean routineFound = false;
        for (int i = 0; i < routines.size(); i++) {
            if (routines.get(i).getId() == routine.getId()) {
                routines.set(i, routine); // Update the user
                routineFound = true;
                break;
            }
        }
        if (!routineFound) {
            Logger.getLogger(dk.easv.assignmentworkoutfiles.dal.RoutineDAO.class.getName()).log(Level.WARNING, "Routine with ID " + routine.getId() + " not found for update.");
        }
        clearAndSave(routines); // Save the updated list
    }

    // Get the next available user ID
    public int getNextId() throws IOException {
        List<Routine> routines = getAll();
        int maxId = 0;
        for (Routine r : routines) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        return maxId + 1;
    }
}

