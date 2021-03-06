package com.github.bitcoin.services.impl;

import com.github.bitcoin.entity.Account;
import com.github.bitcoin.entity.EntityStatus;
import com.github.bitcoin.exceptions.BadRequest;
import com.github.bitcoin.exceptions.NotFound;
import com.github.bitcoin.repository.AccountRepo;
import com.github.bitcoin.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepo accountRepo;

    @Override
    public Page<Account> readAllByStatus(EntityStatus status, Pageable pageable) {
        return this.accountRepo.findAllByStatus(status, pageable);
    }

    @Override
    public Account create(Account account) {
        return this.accountRepo.save(account);
    }

    @Override
    public Account readByUserId(UUID userId) {
        return this.accountRepo.findByUserId(userId)
                .orElseThrow(BadRequest::new);
    }

    @Override
    public void update(Account account) {
        this.accountRepo.save(account);
    }

}
