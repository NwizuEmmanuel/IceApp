package models;

public class DGVModel {
    String DGV;
    String CreationDate;

    public DGVModel(String DGV, String CreationDate) {
        this.DGV = DGV;
        this.CreationDate = CreationDate;
    }

    public String getDGV() {
        return DGV;
    }

    public String getCreationDate() {
        return CreationDate;
    }
}
