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
public class UpdateTransactionResponse extends BaseResponse {
    private boolean isUpdated;
    private String message;
}
