package za.ac.cput.sikunyecharity.Domain;

public class ProjectRegistration {

    private String projectId;
    private String userName;
    private String userEmail;

    public ProjectRegistration() {
    }

    public ProjectRegistration(String projectId, String userName, String userEmail) {
        this.projectId = projectId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
