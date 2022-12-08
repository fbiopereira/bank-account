package com.fbiopereira.bankaccount.data.repository;

import com.fbiopereira.bankaccount.domain.model.Account;

public interface AccountRepository<T> {

    T save(Account account);

    T findAccountById(int accountId);

    T deleteAll();


}
