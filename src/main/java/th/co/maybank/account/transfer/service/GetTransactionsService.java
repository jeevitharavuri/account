package th.co.maybank.account.transfer.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import th.co.maybank.account.transfer.model.entity.TransactionEntity;
import th.co.maybank.account.transfer.model.request.GetTransactionRequest;
import th.co.maybank.account.transfer.model.response.GetTransactionResponse;
import th.co.maybank.account.transfer.repository.TransactionRepository;
import th.co.maybank.account.transfer.specification.TransactionSpecification;

import java.util.List;

@Service
public class GetTransactionsService implements BaseService<GetTransactionRequest, GetTransactionResponse> {


    private final TransactionRepository transactionRepository;

    public GetTransactionsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public GetTransactionResponse execute(GetTransactionRequest request) {

        Specification<TransactionEntity> specification = TransactionSpecification.searchByKeyword(request);
        var pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        var transactionEntities = transactionRepository.findAll(specification, pageable);

        if (transactionEntities.getContent().isEmpty()) {
            return new GetTransactionResponse();
        }

        List<GetTransactionResponse.Content> transactionContents = transactionEntities.getContent().stream()
                .map(this::mapToContent)
                .toList();

        GetTransactionResponse.PageResponse pageResponse = GetTransactionResponse.toPage(transactionEntities);


        return GetTransactionResponse.builder()
                .transactions(transactionContents)
                .pageable(pageResponse)
                .build();

    }

    private GetTransactionResponse.Content mapToContent(TransactionEntity transactionEntity) {
        return GetTransactionResponse.Content.builder()
                .transactionRefId(transactionEntity.getTransactionId())
                .customerId(transactionEntity.getCustomer())
                .accountNo(transactionEntity.getAccountNumber())
                .transactionAmount(transactionEntity.getTrxAmount())
                .description(transactionEntity.getTransactionType().name())
                .txnAmount(transactionEntity.getTrxDate())
                .txnTime(transactionEntity.getTrxTime())
                .build();
    }
}
