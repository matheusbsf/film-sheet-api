package com.sftech.film_sheet_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sftech.film_sheet_api.entity.Account;
import com.sftech.film_sheet_api.entity.Film;
import com.sftech.film_sheet_api.service.AccountService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account user) {
        if (accountService.consultProfile(user.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account newAccount = accountService.createAccount(user);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public Optional<?> consultProfile(@RequestParam String login) {
        return accountService.consultProfile(login);
    }

    @GetMapping("/list")
    public List<Film> getFilmLista(@RequestParam(required = true) String login,
            @RequestParam(required = true) String listName) {
        return accountService.getFilmLista(login, listName);
    }

    @PutMapping("/list/create/{login}/{listName}")
    public ResponseEntity<Account> createLista(
            @PathVariable String login,
            @PathVariable String listName) {
        Account user = accountService.createLista(login, listName);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/list/update/{action}/{login}/{listName}/{movieId}")
    public ResponseEntity<Account> updateLista(
            @PathVariable String action,
            @PathVariable String login,
            @PathVariable String listName,
            @PathVariable Integer movieId) {
        Account user = accountService.updateLista(action, login, listName, movieId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/list/delete/{login}/{listName}")
    public ResponseEntity<Account> deleteLista(
            @PathVariable String login,
            @PathVariable String listName) {
        Account user = accountService.deleteLista(login, listName);
        return ResponseEntity.ok(user);
    }
}
