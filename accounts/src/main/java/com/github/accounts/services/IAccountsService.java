package com.github.accounts.services;

import com.github.accounts.entity.Account;

import java.util.UUID;

public interface IAccountsService {

    Account create(Account account);

    Account readByUserId(UUID userId);

    Account readById(Long id);

    Account readByEmailAndPhone(String email, String phone);

    void update(Account account);

    void remove(Long id);

}
