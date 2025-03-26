package th.co.maybank.account.transfer.service;


import th.co.maybank.account.transfer.model.request.BaseRequest;
import th.co.maybank.account.transfer.model.response.BaseResponse;

public interface BaseService<T extends BaseRequest, V extends BaseResponse> {
    V execute(T input);
}
