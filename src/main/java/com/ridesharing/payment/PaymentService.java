package com.ridesharing.payment;

import com.ridesharing.model.Trip;

import java.util.UUID;

public class PaymentService {

    public PaymentTransaction processPayment(Trip trip) {

        String transactionId =
                UUID.randomUUID().toString();

        PaymentTransaction transaction =
                new PaymentTransaction(
                        transactionId,
                        trip.getId(),
                        trip.getFare()
                );

        transaction.process();

        return transaction;
    }
}
