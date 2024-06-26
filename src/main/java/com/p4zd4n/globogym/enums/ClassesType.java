package com.p4zd4n.globogym.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ClassesType {

    STRENGTH("Strength"),
    CARDIO("Cardio"),
    ENDURANCE("Endurance"),
    WEIGHT_LOSS("Weight Loss"),
    REHABILITATION("Rehabilitation"),
    WOMEN_TRAINING("Women Training");

    private final String type;

    ClassesType(String type) {
        this.type = type;
    }

    public static List<ClassesType> getAll() {
        return Arrays.asList(ClassesType.values());
    }

    public static ClassesType getByString(String str) {

        return switch (str) {
            case "Strength" -> ClassesType.STRENGTH;
            case "Cardio" -> ClassesType.CARDIO;
            case "Endurance" -> ClassesType.ENDURANCE;
            case "Weight Loss" -> ClassesType.WEIGHT_LOSS;
            case "Rehabilitation" -> ClassesType.REHABILITATION;
            case "Women Training" -> ClassesType.WOMEN_TRAINING;
            default -> throw new IllegalArgumentException("Unknown EventType: " + str);
        };
    }
}
