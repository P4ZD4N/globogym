package com.p4zd4n.globogym.enums;

import java.util.Arrays;
import java.util.List;

public enum CoachSpecialization {

    STRENGTH,
    CARDIO,
    ENDURANCE,
    WEIGHT_LOSS,
    REHABILITATION,
    WOMEN_TRAINING;

    public static List<CoachSpecialization> getAllSpecializations() {
        return Arrays.asList(CoachSpecialization.values());
    }
}
