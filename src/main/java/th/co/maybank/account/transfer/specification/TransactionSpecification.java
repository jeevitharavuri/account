package th.co.maybank.account.transfer.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import th.co.maybank.account.transfer.model.entity.TransactionEntity;
import th.co.maybank.account.transfer.model.request.GetTransactionRequest;


public class TransactionSpecification {

    public static final String CUSTOMER_ID = "customer_id";
    public static final String DESCRIPTION = "description";
    public static final String ACCOUNT_NO = "account_no";

    public static Specification<TransactionEntity> searchByKeyword(GetTransactionRequest request) {


        Specification<TransactionEntity> specification = Specification.where(null);

        if (StringUtils.isNotBlank(request.getKeyword())) {
            specification = specification.and((findByWildCard(CUSTOMER_ID, request.getKeyword()))
                    .or(findByWildCard(DESCRIPTION, request.getKeyword())));
        }

        if ( request.getAccountNo().isEmpty()) {
            specification = (root, query, criteriaBuilder) ->
                    root.get(ACCOUNT_NO).in(request.getAccountNo());


        }
        Specification.where(
                sortByColumnName(specification,
                        request.getOrder(), request.getOrderBy()));
        return specification;
    }

    public static Specification<TransactionEntity> findByWildCard(String key,
                                                                  String value) {
        if (value != null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(key),
                            "%" + value + "%");
        } else {
            return null;
        }
    }

    public static Specification<TransactionEntity> sortByColumnName(
            Specification<TransactionEntity> spec, String order, String columnName) {

        return ((root, query, builder) -> {
            query = Sort.Direction.ASC.name().equalsIgnoreCase(order) ? query.orderBy(
                    builder.asc(root.get(columnName)))
                    : query.orderBy(builder.desc(root.get(columnName)));
            return spec.toPredicate(root, query, builder);
        });
    }

}
