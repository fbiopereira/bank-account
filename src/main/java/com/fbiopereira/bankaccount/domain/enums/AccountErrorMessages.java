package com.fbiopereira.bankaccount.domain.enums;

public enum AccountErrorMessages {

    TRANSFER_DENIED_IN_DOMAIN("INVALID_OPERATION_01", "You can`t pass transfer type to Account model. You must use this type in a service or use case scenario"),
    INVALID_ACCOUNT_ID("INVALID_ACCOUNT_ID_01", "The account ID must be an int number greater than 0");

    private final String code;
    private final String message;

    AccountErrorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }



}
