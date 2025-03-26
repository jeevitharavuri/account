package th.co.maybank.account.transfer.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import th.co.maybank.account.transfer.model.entity.TransactionEntity;
import th.co.maybank.account.transfer.repository.TransactionRepository;


@Component
public class TransactionItemsWriter implements ItemWriter<TransactionEntity> {

    private final TransactionRepository transactionRepository;

    public TransactionItemsWriter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void write(Chunk<? extends TransactionEntity> items) throws Exception {
        transactionRepository.saveAll(items);

    }
}
