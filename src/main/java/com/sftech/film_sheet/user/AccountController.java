package com.sftech.film_sheet.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sftech.film_sheet.film.Film;

import java.util.List;
import java.util.Optional;

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

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/watched")
    public List<Film> getListaAssistidos(@RequestParam(required = true) Account user) {
        return accountService.getListaAssistidos(user);
    }

    @GetMapping("/to_watch")
    public List<Film> getListaAssistir(@RequestParam(required = true) Account user) {
        return accountService.getListaAssistir(user);
    }

    @GetMapping("/profile")
    public Optional<?> consultProfile(@RequestParam Account user) {
        return accountService.consultProfile(user);
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account user) {
        if (accountService.consultProfile(user).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account newAccount = accountService.createAccount(user);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/update/watched/{action}/{userLogin}/{movieId}")
    public ResponseEntity<Account> updateAssistidos(
            @PathVariable String action,
            @PathVariable String userLogin,
            @PathVariable Integer movieId) {
        Account user = accountService.updateAssistidos(action, userLogin, movieId);
        return ResponseEntity.ok(user);
    }
}
