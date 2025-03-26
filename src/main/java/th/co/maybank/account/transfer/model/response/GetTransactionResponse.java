package th.co.maybank.account.transfer.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionResponse extends BaseResponse {
    private List<Content> transactions;
    private PageResponse pageable;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content implements Serializable {

        private String transactionRefId;
        private String customerId;
        private String accountNo;
        private BigDecimal transactionAmount;
        private String description;
        private LocalDate txnAmount;
        private String txnTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResponse implements Serializable {

        private long totalPages;
        private boolean last;
        private long numberOfElements;
        private long totalElements;
        private long pageSize;
        private long pageNumber;
        private String prevCursor;
        private String nextCursor;
    }

    public static <T> PageResponse toPage(Page<T> page) {
        return PageResponse.builder()
                .last(page.isLast())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }
}
