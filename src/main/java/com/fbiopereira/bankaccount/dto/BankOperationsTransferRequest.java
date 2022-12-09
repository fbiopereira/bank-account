package com.fbiopereira.bankaccount.dto;

import com.fbiopereira.bankaccount.domain.enums.OperationType;

public class BankOperationsTransferRequest extends BankOperationsRequest {


    private int origin;

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }


}
