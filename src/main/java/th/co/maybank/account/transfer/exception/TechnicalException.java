package th.co.maybank.account.transfer.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends RuntimeException {

    protected HttpStatus httpStatus;
    private String errorMessage;

    public TechnicalException(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}
