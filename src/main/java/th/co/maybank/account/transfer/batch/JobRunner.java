package th.co.maybank.account.transfer.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {

        private final JobLauncher jobLauncher;

        private final Job accountTransactionJob;

    public JobRunner(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.accountTransactionJob = job;
    }

    @Override
        public void run(String... args) throws JobExecutionException {
            JobExecution jobExecution = jobLauncher.run(accountTransactionJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            //add logger
            System.out.println("Job Status: " + jobExecution.getStatus());
        }
}
