package com.course.utils;

import java.util.Collections;
import java.util.List;

public class Aggregation {
    public static float MaxValue(List<Float> values){
        return Collections.max(values);
    }
    public static float MinValue(List<Float> values){
        return Collections.min(values);
    }
    public static float SumValue(List<Float> values){
        float sum = 0.0f;
        for(float num : values) sum += num;
        return sum;
    }
    public static float AvgValue(List<Float> values){
        return SumValue(values) / values.size();
    }

    public static float RateValue(List<Float> values){
        return values.get(0) - values.get(values.size() - 1);
    }

}
