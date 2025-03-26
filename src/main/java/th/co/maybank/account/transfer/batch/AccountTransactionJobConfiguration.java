package th.co.maybank.account.transfer.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import th.co.maybank.account.transfer.batch.processor.TransactionItemProcessor;
import th.co.maybank.account.transfer.batch.writer.TransactionItemsWriter;
import th.co.maybank.account.transfer.model.batch.stream.TransactionFile;
import th.co.maybank.account.transfer.model.entity.TransactionEntity;
import th.co.maybank.account.transfer.repository.TransactionRepository;

@Configuration
@EnableBatchProcessing
public class AccountTransactionJobConfiguration {


    @Bean
    public ItemReader<TransactionFile> reader() {
        FlatFileItemReader<TransactionFile> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("src/main/resources/data.txt"));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter("|");
                setNames("accountNumber", "trxAmount", "description", "trxDate", "trxTime", "customerId");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(TransactionFile.class);
            }});
        }});
        return reader;
    }

        @Bean
        public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                          ItemReader<TransactionFile> reader,
                          ItemProcessor<TransactionFile, TransactionEntity> processor,
                          ItemWriter<TransactionEntity> writer) {
            return new StepBuilder("accountTransferJobMainStep", jobRepository)
                    .<TransactionFile, TransactionEntity>chunk(10 ,transactionManager)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .build();


    }
    @Bean
    public Job accountTransactionJob(JobRepository jobRepository, Step accountTransferJobMainStep) {
        return new JobBuilder("accountTransactionJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(accountTransferJobMainStep)
                .build();
    }

    @Bean
    public ItemProcessor<TransactionFile, TransactionEntity> processor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public ItemWriter<TransactionEntity> writer(TransactionRepository transactionRepository) {
        return new TransactionItemsWriter(transactionRepository);
    }

}
