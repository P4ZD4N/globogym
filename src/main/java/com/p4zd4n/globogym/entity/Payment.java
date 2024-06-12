package com.p4zd4n.globogym.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Payment implements Serializable {

    private LocalDate date;
    private Double amount;

    @Override
    public String toString() {
        return date + ": " + amount + " PLN";
    }
}
