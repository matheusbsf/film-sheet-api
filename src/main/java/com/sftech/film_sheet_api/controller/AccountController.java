package com.sftech.film_sheet_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sftech.film_sheet_api.entity.Account;
import com.sftech.film_sheet_api.entity.Film;
import com.sftech.film_sheet_api.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Cadastra um novo usuário")
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account user) {
        if (accountService.consultProfile(user.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account newAccount = accountService.createAccount(user);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um usuário pelo login e retorna as informações básicas")
    @GetMapping("/profile")
    public Optional<?> consultProfile(@RequestParam String login) {
        return accountService.consultProfile(login);
    }

    @Operation(summary = "Retorna uma lista com os detalhes dos filmes de uma lista de IDs do usuario informado")
    @GetMapping("/list")
    public List<Film> getFilmLista(@RequestParam(required = true) String login,
            @RequestParam(required = true) String listName) {
        return accountService.getFilmLista(login, listName);
    }

    @Operation(summary = "Cria uma nova lista de filmes para o usuário informado")
    @PutMapping("/list/create/{login}/{listName}")
    public ResponseEntity<Account> createLista(
            @PathVariable String login,
            @PathVariable String listName) {
        Account user = accountService.createLista(login, listName);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Insere ou remove o ID de um filme na lista informada de um usuário de acordo com a ação escolhida")
    @PutMapping("/list/update/{action}/{login}/{listName}/{movieId}")
    public ResponseEntity<Account> updateLista(
            @PathVariable String action,
            @PathVariable String login,
            @PathVariable String listName,
            @PathVariable Integer movieId) {
        Account user = accountService.updateLista(action, login, listName, movieId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Remove a lista informada da conta de um usuário")
    @PutMapping("/list/delete/{login}/{listName}")
    public ResponseEntity<Account> deleteLista(
            @PathVariable String login,
            @PathVariable String listName) {
        Account user = accountService.deleteLista(login, listName);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Apaga o registro de um usuário através de seu login e senha")
    @DeleteMapping("/{login}/{senha}")
    public ResponseEntity<String> deleteAccount(@PathVariable String login, @PathVariable String senha) {
        if (accountService.deleteAccount(login, senha) == 0) {
            return new ResponseEntity<>("Usuário apagado com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Senha incorreta ou usuário não existe", HttpStatus.BAD_REQUEST);
        }
    }
}
