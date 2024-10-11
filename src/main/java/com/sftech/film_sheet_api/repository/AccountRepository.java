package com.sftech.film_sheet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sftech.film_sheet_api.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
