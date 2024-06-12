package com.p4zd4n.globogym.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClubMember extends User {

    private static final long serialVersionUID = -4495183180571699662L;

    private MembershipCard membershipCard;
    private List<Payment> paymentsHistory = new ArrayList<>();
    private Double balance = 0D;

    public ClubMember(String username, String email, String password, String firstName, String lastName, LocalDate birthDate) {
        super(username, email, password, firstName, lastName, birthDate);
    }

    public void increaseBalance(Double amount) {

        balance += amount;
        updatePaymentsHistory(LocalDate.now(), amount);
    }

    public void reduceBalance(Double amount) {

        balance -= amount;
        updatePaymentsHistory(LocalDate.now(), -amount);
    }

    public void updatePaymentsHistory(LocalDate date, Double amount) {

        paymentsHistory.add(new Payment(date, amount));
        User.serializeUsers();
    }
}
