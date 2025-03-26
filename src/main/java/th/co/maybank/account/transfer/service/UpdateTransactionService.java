package th.co.maybank.account.transfer.service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import th.co.maybank.account.transfer.exception.TechnicalException;
import th.co.maybank.account.transfer.model.enums.TransactionType;
import th.co.maybank.account.transfer.model.request.UpdateTransactionRequest;

import th.co.maybank.account.transfer.model.response.UpdateTransactionResponse;
import th.co.maybank.account.transfer.repository.TransactionRepository;

@Service
@Transactional
public class UpdateTransactionService implements BaseService<UpdateTransactionRequest,UpdateTransactionResponse>{

    private static final String TRANSACTION_NOT_FOUND = "Not found Transaction to update";
    private static final String TRANSACTION_UPDATED = "The transaction has been modified by another process.";
    private final TransactionRepository transactionRepository;

    public UpdateTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public UpdateTransactionResponse execute(UpdateTransactionRequest updateRequest) {
        var transactionEntity = transactionRepository.findById(updateRequest.getTransactionId()).
                orElseThrow(() -> new TechnicalException(HttpStatus.BAD_REQUEST, TRANSACTION_NOT_FOUND));

        transactionEntity.setTransactionType(TransactionType.valueOf(updateRequest.getDescription()));

        try {
            transactionRepository.save(transactionEntity);
        } catch (OptimisticLockingFailureException ex) {
            return new UpdateTransactionResponse(false,TRANSACTION_UPDATED);
        }
        return new UpdateTransactionResponse(true,null);
    }
}
