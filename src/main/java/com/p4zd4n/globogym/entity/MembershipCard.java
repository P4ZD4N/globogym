package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.MembershipCardStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MembershipCard {

    private static List<MembershipCard> membershipCards = new ArrayList<>();
    private static Long counter = 1L;

    private Long id;
    private ClubMember owner;
    private Double price = 150D;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;
    private MembershipCardStatus membershipCardStatus;

    public MembershipCard(ClubMember clubMember) {

        if (clubMember.getMembershipCard() != null || clubMember.getBalance() < price) {
            throw new IllegalArgumentException();
        }

        id = counter++;
        owner = clubMember;
        purchaseDate = LocalDate.now();
        expirationDate = LocalDate.now().plusMonths(1);
        membershipCardStatus = MembershipCardStatus.ACTIVE;

        clubMember.reduceBalance(price);
        membershipCards.add(this);
    }

    public static List<MembershipCard> getMembershipCards() {
        return membershipCards;
    }

    public static void setMembershipCards(List<MembershipCard> membershipCards) {
        MembershipCard.membershipCards = membershipCards;
    }
}
