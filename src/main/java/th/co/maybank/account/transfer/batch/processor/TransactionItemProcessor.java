package th.co.maybank.account.transfer.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import th.co.maybank.account.transfer.model.batch.stream.TransactionFile;
import th.co.maybank.account.transfer.model.entity.TransactionEntity;
import th.co.maybank.account.transfer.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TransactionItemProcessor implements ItemProcessor<TransactionFile, TransactionEntity> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public TransactionEntity process(TransactionFile item) throws Exception {
        return TransactionEntity.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountNumber(item.getAccountNumber())
                .trxAmount(new BigDecimal(item.getTrxAmount()))
                .customer(item.getCustomerId())
                .transactionType(TransactionType.valueOf(item.getDescription().toUpperCase()))
                .trxDate(LocalDate.parse(item.getTrxDate(), dateFormatter))
                .trxTime(item.getTrxTime())
                .build();
    }
}