package barriga.service;

import barriga.domain.Transaction;
import barriga.exceptions.ValidationException;
import barriga.repositories.TransactionDAO;

import java.time.LocalDateTime;
import java.util.Date;

public class TransactionService2 {
    private TransactionDAO dao;

    public Transaction save(Transaction transaction) {
        assertValidData(transaction);
        return dao.save(transaction);
    }

    public void assertValidData(Transaction transaction) {
        if (transaction == null) {
            throw new ValidationException("Transaction is null");
        }
        if (transaction.getDescription() == null) {
            throw new ValidationException("Transaction description is null");
        }
        if (transaction.getValue() == null) {
            throw new ValidationException("Transaction value is null");
        }
        if (transaction.getAccount() == null) {
            throw new ValidationException("Transaction account is null");
        }
        if (transaction.getDate() == null) {
            throw new ValidationException("Transaction date is null");
        }
        // Only for learning purposes
        if (new Date().getHours() > 7) {
            throw new ValidationException("Transaction date should not be greater than 7");
        }
        if (transaction.getStatus() == null) {
            transaction.setStatus(false);
        }
    }
}
