package Model;

public class Job {
    private long id;
    private String position;
    private String profession;
    private String description;
    private int experience;

    public Job() {
        this.id = id;
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", profession='" + profession + '\'' +
                ", description='" + description + '\'' +
                ", experience=" + experience +
                '}';
    }
}
