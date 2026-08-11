package com.example.demo.service;

import com.example.demo.DBHelper.TransactionDBHelper;
import com.example.demo.DBHelper.FoodDBHelper;
import com.example.demo.model.Transaction;
import com.example.demo.model.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionDBHelper transactionDBHelper;

    @Autowired
    private FoodDBHelper foodDBHelper;


    // =====================================================
    //                 CREATE FOOD REQUEST
    // =====================================================

    /*
     * NGO requests food.
     *
     * IMPORTANT:
     *
     * NGO can request food only when:
     *
     * Food status = Available
     *
     * If food is:
     *
     * Reserved -> request not allowed
     * Donated  -> request not allowed
     * Expired  -> request not allowed
     */

    public Transaction createRequest(Transaction transaction) {

        // -------------------------------------------------
        // Check whether food exists
        // -------------------------------------------------

        Food food = foodDBHelper.getById(
                transaction.getFoodId()
        );

        if (food == null) {
            return null;
        }


        // -------------------------------------------------
        // Food must be Available
        // -------------------------------------------------

        if (!"Available".equalsIgnoreCase(
                food.getStatus())) {

            return null;
        }


        // -------------------------------------------------
        // Check whether this NGO already has
        // an active request for the same food
        // -------------------------------------------------

        List<Transaction> existingRequests =
                transactionDBHelper.getByFood(
                        transaction.getFoodId()
                );

        if (existingRequests != null) {

            for (Transaction existing :
                    existingRequests) {

                if (existing.getNgoId() != null
                        && existing.getNgoId()
                        .equals(transaction.getNgoId())) {

                    String status =
                            existing.getStatus();

                    /*
                     * Same NGO cannot create another
                     * request if its previous request
                     * is still active.
                     */

                    if ("Pending".equalsIgnoreCase(status)
                            || "Accepted".equalsIgnoreCase(status)) {

                        return null;
                    }
                }
            }
        }


        // -------------------------------------------------
        // Create new request
        // -------------------------------------------------

        transaction.setStatus("Pending");

        String currentTime =
                LocalDateTime.now().toString();

        transaction.setRequestedAt(currentTime);

        return transactionDBHelper.add(transaction);
    }


    // =====================================================
    //                 ACCEPT REQUEST
    // =====================================================

    /*
     * User accepts ONE NGO request.
     *
     * Example:
     *
     * Food F101
     *
     * NGO A -> Pending
     * NGO B -> Pending
     * NGO C -> Pending
     *
     * User accepts NGO B.
     *
     * Result:
     *
     * NGO A -> Rejected
     * NGO B -> Accepted
     * NGO C -> Rejected
     *
     * Food -> Reserved
     */

    public Transaction acceptRequest(String transactionId) {

        // Find selected transaction
        Transaction selectedTransaction =
                transactionDBHelper.getById(transactionId);

        if (selectedTransaction == null) {
            return null;
        }


        // Only Pending requests can be accepted
        if (!"Pending".equalsIgnoreCase(
                selectedTransaction.getStatus())) {

            return null;
        }


        String foodId =
                selectedTransaction.getFoodId();


        // -------------------------------------------------
        // Check food exists
        // -------------------------------------------------

        Food food =
                foodDBHelper.getById(foodId);

        if (food == null) {
            return null;
        }


        // -------------------------------------------------
        // Food must currently be Available
        // -------------------------------------------------

        if (!"Available".equalsIgnoreCase(
                food.getStatus())) {

            return null;
        }


        // -------------------------------------------------
        // Check whether this food already has
        // another accepted transaction
        // -------------------------------------------------

        List<Transaction> acceptedTransactions =
                transactionDBHelper.getAcceptedByFood(foodId);

        if (acceptedTransactions != null
                && !acceptedTransactions.isEmpty()) {

            return null;
        }


        // -------------------------------------------------
        // Accept selected request
        // -------------------------------------------------

        selectedTransaction.setStatus("Accepted");

        selectedTransaction.setAcceptedAt(
                LocalDateTime.now().toString()
        );


        Transaction accepted =
                transactionDBHelper.update(
                        transactionId,
                        selectedTransaction
                );


        // -------------------------------------------------
        // Reject all other pending requests
        // for the same food
        // -------------------------------------------------

        List<Transaction> pendingTransactions =
                transactionDBHelper.getPendingByFood(foodId);

        if (pendingTransactions != null) {

            for (Transaction transaction :
                    pendingTransactions) {

                // Don't reject selected transaction
                if (!transaction.getTransactionId()
                        .equals(transactionId)) {

                    transaction.setStatus("Rejected");

                    transactionDBHelper.update(
                            transaction.getTransactionId(),
                            transaction
                    );
                }
            }
        }


        // -------------------------------------------------
        // Change food status
        //
        // Available -> Reserved
        // -------------------------------------------------

        foodDBHelper.updateStatus(
                foodId,
                "Reserved"
        );


        return accepted;
    }


    // =====================================================
    //                 REJECT REQUEST
    // =====================================================

    public Transaction rejectRequest(
            String transactionId) {

        Transaction transaction =
                transactionDBHelper.getById(
                        transactionId
                );

        if (transaction == null) {
            return null;
        }


        // Only Pending request can be rejected
        if (!"Pending".equalsIgnoreCase(
                transaction.getStatus())) {

            return null;
        }


        transaction.setStatus("Rejected");


        return transactionDBHelper.update(
                transactionId,
                transaction
        );
    }


    // =====================================================
    //                 CANCEL REQUEST
    // =====================================================

    /*
     * NGO cancels an accepted request.
     *
     * Example:
     *
     * NGO B -> Accepted
     * Food  -> Reserved
     *
     * NGO B cancels.
     *
     * Result:
     *
     * NGO B -> Cancelled
     * Food  -> Available
     *
     * Other NGOs can request the food again.
     */

    public Transaction cancelRequest(
            String transactionId,
            String reason,
            String cancelledBy) {


        Transaction transaction =
                transactionDBHelper.getById(
                        transactionId
                );


        if (transaction == null) {
            return null;
        }


        // -------------------------------------------------
        // Only Accepted request can be cancelled
        // -------------------------------------------------

        if (!"Accepted".equalsIgnoreCase(
                transaction.getStatus())) {

            return null;
        }


        // -------------------------------------------------
        // Cancel transaction
        // -------------------------------------------------

        transaction.setStatus("Cancelled");

        transaction.setCancellationReason(reason);

        transaction.setCancelledBy(cancelledBy);

        transaction.setCancelledAt(
                LocalDateTime.now().toString()
        );


        Transaction cancelled =
                transactionDBHelper.update(
                        transactionId,
                        transaction
                );


        // -------------------------------------------------
        // Make food available again
        //
        // Reserved -> Available
        // -------------------------------------------------

        foodDBHelper.updateStatus(
                transaction.getFoodId(),
                "Available"
        );


        /*
         * IMPORTANT:
         *
         * We do NOT delete the cancelled transaction.
         *
         * It remains in MongoDB as:
         *
         * NGO B -> Cancelled
         *
         * This preserves transaction history.
         */


        return cancelled;
    }


    // =====================================================
    //                 COMPLETE DONATION
    // =====================================================

    /*
     * NGO has received the food.
     *
     * Accepted -> Completed
     *
     * Food:
     * Reserved -> Donated
     */

    public Transaction completeRequest(
            String transactionId) {


        Transaction transaction =
                transactionDBHelper.getById(
                        transactionId
                );


        if (transaction == null) {
            return null;
        }


        // -------------------------------------------------
        // Only Accepted request can be completed
        // -------------------------------------------------

        if (!"Accepted".equalsIgnoreCase(
                transaction.getStatus())) {

            return null;
        }


        transaction.setStatus("Completed");

        transaction.setCompletedAt(
                LocalDateTime.now().toString()
        );


        Transaction completed =
                transactionDBHelper.update(
                        transactionId,
                        transaction
                );


        // -------------------------------------------------
        // Food is now permanently donated
        // -------------------------------------------------

        foodDBHelper.updateStatus(
                transaction.getFoodId(),
                "Donated"
        );


        return completed;
    }


    // =====================================================
    //                 GET FOOD REQUESTS
    // =====================================================

    /*
     * Get all requests for a particular food.
     *
     * Useful for the User/Donor.
     */

    public List<Transaction> getRequestsForFood(
            String foodId) {

        return transactionDBHelper.getByFood(
                foodId
        );
    }


    // =====================================================
    //             GET PENDING FOOD REQUESTS
    // =====================================================

    /*
     * Used when donor wants to see NGOs
     * currently waiting for the food.
     */

    public List<Transaction> getPendingRequestsForFood(
            String foodId) {

        return transactionDBHelper
                .getPendingByFood(foodId);
    }


    // =====================================================
    //             GET ACCEPTED FOOD REQUEST
    // =====================================================

    public List<Transaction> getAcceptedRequestForFood(
            String foodId) {

        return transactionDBHelper
                .getAcceptedByFood(foodId);
    }
}
