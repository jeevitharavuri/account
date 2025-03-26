package th.co.maybank.account.transfer.controller;

import org.springframework.web.bind.annotation.*;
import th.co.maybank.account.transfer.model.request.GetTransactionRequest;
import th.co.maybank.account.transfer.model.request.UpdateTransactionRequest;
import th.co.maybank.account.transfer.model.response.GetTransactionResponse;
import th.co.maybank.account.transfer.model.response.UpdateTransactionResponse;
import th.co.maybank.account.transfer.service.GetTransactionsService;
import th.co.maybank.account.transfer.service.UpdateTransactionService;

@RestController
@RequestMapping("/v1/transaction")

public class TransactionController {

    private final GetTransactionsService getTransactionsService;
    private final UpdateTransactionService updateTransactionService;

    public TransactionController(GetTransactionsService getTransactionsService, UpdateTransactionService updateTransactionService) {
        this.getTransactionsService = getTransactionsService;
        this.updateTransactionService = updateTransactionService;
    }


    @PatchMapping("/{transactionId}/update")
    public UpdateTransactionResponse updateTransactionDescription(
            @PathVariable String transactionId, @RequestParam String description) {

        return updateTransactionService.execute(UpdateTransactionRequest.builder()
                .transactionId(transactionId)
                .description(description)
                .build());

    }

    @GetMapping(value = "/customer/items")
    public GetTransactionResponse getTransaction(
            @RequestBody GetTransactionRequest getBillTransactionRequest) {
        return getTransactionsService.execute(getBillTransactionRequest);
    }


}