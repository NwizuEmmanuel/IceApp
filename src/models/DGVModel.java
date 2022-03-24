package models;

public class DGVModel {
    String internValue;
    String otherValue;

    public DGVModel(String internValue, String otherValue) {
        this.internValue = internValue;
        this.otherValue = otherValue;
    }

    public String getInternValue() {
        return internValue;
    }

    public String getOtherValue() {
        return otherValue;
    }
}
