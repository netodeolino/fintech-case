package com.challenge.users.application.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 401:
                return new UnauthorizedException(Constants.NOT_AUTHORIZED);
            case 404:
                return new NotFoundException(Constants.NOT_FOUND);
            case 422:
                return new UnprocessableException(Constants.UNPROCESSABLE);
            default:
                return new Exception("Error! Contact API admin.");
        }
    }

}
