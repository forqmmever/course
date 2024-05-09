package com.course.pojo;

public class MetricConstraint {
    private int id;
    private String metric;
    private String constraintType;
    private float value;

    @Override
    public String toString() {
        return "MetricConstraint{" +
                "id=" + id +
                ", metric='" + metric + '\'' +
                ", constraintType='" + constraintType + '\'' +
                ", value=" + value +
                '}';
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
