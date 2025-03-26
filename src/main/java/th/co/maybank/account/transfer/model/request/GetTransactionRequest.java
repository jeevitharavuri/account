package th.co.maybank.account.transfer.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class  GetTransactionRequest extends BaseRequest {

    @NotNull
    private List<String> accountNo;
    @Builder.Default
    private String order = Direction.ASC.name();
    @Builder.Default
    private String orderBy = "transactionAmount";
    @Builder.Default
    private Integer pageSize = 10;
    @Builder.Default
    private Integer pageNumber = 0;
    private String keyword;


}
