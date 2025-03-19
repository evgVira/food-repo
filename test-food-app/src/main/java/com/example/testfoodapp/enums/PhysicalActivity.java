package com.example.testfoodapp.enums;

import lombok.Getter;

@Getter
public enum PhysicalActivity {

    MINIMAL(1.2),
    LOW(1.375),
    NORMAL(1.55),
    HIGH(1.725),
    EXTREME(1.9);

    private final Double value;

    PhysicalActivity(Double value) {
        this.value = value;
    }

}
