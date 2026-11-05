package za.ac.cput.sikunyecharity.Domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Project {

    private String projectId;
    private String name;
    private String ownerName;
    private String description;
    private String status;
    private Date startDate;
    private Date endDate;
    private BigDecimal goalAmount;
    private int progressPercent;

    public Project() {
    }

    public Project(String projectId, String name, String ownerName, String description,
            String status, Date startDate, Date endDate, BigDecimal goalAmount) {
        this(projectId, name, ownerName, description, status, startDate,
                endDate, goalAmount, 0);
    }

    public Project(String projectId, String name, String ownerName, String description,
            String status, Date startDate, Date endDate, BigDecimal goalAmount,
            int progressPercent) {
        this.projectId = projectId;
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalAmount = goalAmount;
        this.progressPercent = progressPercent;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }
}
