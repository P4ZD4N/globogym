package com.p4zd4n.globogym.entity;

import com.p4zd4n.globogym.enums.MembershipCardStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MembershipCard implements Serializable {

    @Serial
    private static final long serialVersionUID = -8490544777140165825L;
    private static List<MembershipCard> membershipCards = new ArrayList<>();
    private static Double price = 150D;
    private static Long counter = 1L;

    private Long id;
    private ClubMember owner;
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

    public void renew() {
        membershipCardStatus = MembershipCardStatus.ACTIVE;
        expirationDate = expirationDate.plusMonths(1);
    }

    public static MembershipCard findByUsername(String username) {

        Optional<MembershipCard> foundCard = membershipCards.stream()
                .filter(membershipCard -> membershipCard.getOwner().getUsername().equals(username))
                .findFirst();

        return foundCard.orElse(null);
    }

    public static void serializeMembershipCards() {
        try (FileOutputStream fileOut = new FileOutputStream("membershipCards.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(membershipCards);

            System.out.println("Membership cards successfully saved to membershipCards.bin");
        } catch (IOException e) {
            System.err.println("Error with writing membership cards to membershipCards.bin");
            e.printStackTrace();
        }
    }

    public static void deserializeMembershipCards() {

        try (FileInputStream fileIn = new FileInputStream("membershipCards.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            List<MembershipCard> membershipCards = (List<MembershipCard>) objectIn.readObject();

            MembershipCard.setMembershipCards(membershipCards);

            System.out.println("Membership cards successfully read from membershipCards.bin");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error with reading membership cards from membershipCards.bin");
            e.printStackTrace();
        }
    }

    public static List<MembershipCard> getMembershipCards() {
        return membershipCards;
    }

    public static void setMembershipCards(List<MembershipCard> membershipCards) {
        MembershipCard.membershipCards = membershipCards;
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        MembershipCard.price = price;
    }
}
