package com.fbiopereira.bankaccount.domain.exceptions;

public class AccountNotFoundException extends GenericException {

    public AccountNotFoundException(String code, String format, Object... values) {

        super(code, format, values);

    }

}
