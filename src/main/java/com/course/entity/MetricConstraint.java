package com.course.entity;

public class MetricConstraint {
    private int id;
    private String metric;
    private String constraintType;
    private float value;
    private String description;
    private int type;

    public MetricConstraint() {
    }

    public MetricConstraint(String metric, String constraintType, float value, String description, int type) {
        this.metric = metric;
        this.constraintType = constraintType;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public MetricConstraint(int id, String metric, String constraintType, float value, String description, int type) {
        this.id = id;
        this.metric = metric;
        this.constraintType = constraintType;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MetricConstraint{" +
                "id=" + id +
                ", metric='" + metric + '\'' +
                ", constraintType='" + constraintType + '\'' +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
