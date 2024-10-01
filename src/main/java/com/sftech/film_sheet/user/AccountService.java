package com.sftech.film_sheet.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sftech.film_sheet.film.Film;
import com.sftech.film_sheet.film.FilmService;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final FilmService filmService;

    public AccountService(AccountRepository accountRepository, FilmService filmService) {
        this.accountRepository = accountRepository;
        this.filmService = filmService;
    }

    // Busca um usuário pelo login e retorna todos os dados exceto a senha
    public Optional<?> consultProfile(Account account) {
        return accountRepository.findById(account.getLogin());
    }

    // Retorna a lista de filmes assistidos de um usuário
    public List<Film> getListaAssistidos(Account account) {
        return filmService.getFilmList(account.getLista_assistidos());
    }

    // Retorna lista de filmes na lista de assistir de um usuário
    public List<Film> getListaAssistir(Account account) {
        return filmService.getFilmList(account.getLista_assistir());
    }

    // Cadastra um novo login
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Adiciona ou remove o ID de um filme da lista de assistidos
    public Account updateAssistidos(String action, String userLogin, Integer movieId) {
        Account user = accountRepository.findById(userLogin)
                .orElseThrow(() -> new RuntimeException("Usuário não existe"));
        if (action.strip().equals("add") && !user.getLista_assistidos().contains(movieId)) {
            user.getLista_assistidos().add(movieId);
            return accountRepository.save(user);

        } else if (action.strip().equals("del") && user.getLista_assistidos().contains(movieId)) {
            user.getLista_assistidos().remove(movieId);
            return accountRepository.save(user);
        }

        throw new RuntimeException("Ação inválida.");
    }
}
