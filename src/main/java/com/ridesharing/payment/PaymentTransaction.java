package com.ridesharing.payment;

public class PaymentTransaction {

    private final String transactionId;
    private final String tripId;
    private final double amount;

    private boolean successful;

    public PaymentTransaction(
            String transactionId,
            String tripId,
            double amount
    ) {
        this.transactionId = transactionId;
        this.tripId = tripId;
        this.amount = amount;
    }

    public void process() {

        // Simulated payment processing
        successful = true;

        System.out.println(
                "Payment successful. Transaction: "
                        + transactionId
                        + ", Amount: "
                        + amount
        );
    }

    public boolean isSuccessful() {
        return successful;
    }
}
