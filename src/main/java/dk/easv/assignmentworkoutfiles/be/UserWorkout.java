package dk.easv.assignmentworkoutfiles.be;

public class UserWorkout {
    private int id;
    private int userID;
    private int workoutID;
    private String date;

    public UserWorkout(int id, int userID, int workoutID, String date) {
        this.id = id;
        this.userID = userID;
        this.workoutID = workoutID;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getWorkoutID() {
        return workoutID;
    }
    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
