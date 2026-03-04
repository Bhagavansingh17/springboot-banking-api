package com.bhagavan.bankingapi.repository;

import com.bhagavan.bankingapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}