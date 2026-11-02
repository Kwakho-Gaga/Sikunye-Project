package za.ac.cput.sikunyecharity.Domain;

public class Need {

    private int code;
    private String description;

    public Need() {
    }

    public Need(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return description + " (" + code + ")";
    }
}
