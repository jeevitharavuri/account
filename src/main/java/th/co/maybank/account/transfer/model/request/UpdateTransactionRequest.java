package th.co.maybank.account.transfer.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest extends BaseRequest {

    private String transactionId;
    private String description;
}
