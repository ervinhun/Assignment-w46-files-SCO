package dk.easv.assignmentworkoutfiles.be;

public class OnePWorkout {
    private int userID;
    private String userName;
    private String workoutDescription;
    private String date;

    public OnePWorkout(int userID, String userName, String workoutDescription, String date) {
        this.userID = userID;
        this.userName = userName;
        this.workoutDescription = workoutDescription;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getWorkoutDescription() {
        return workoutDescription;
    }
    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return workoutDescription + ", " + date;
    }
}
