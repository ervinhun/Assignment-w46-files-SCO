package dk.easv.assignmentworkoutfiles.be;

public class Routine {
    private String description;
    private String details;
    private int time;

    public Routine(int id, String description, String details, int time) {
        this.id = id;
        this.description = description;
        this.details = details;
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (time > 0)
            this.time = time;
        else throw new IllegalArgumentException();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + ", " + description + ", " + details + ", " + time;
    }


}
