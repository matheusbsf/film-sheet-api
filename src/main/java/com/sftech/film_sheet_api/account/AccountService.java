package com.sftech.film_sheet_api.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sftech.film_sheet_api.film.Film;
import com.sftech.film_sheet_api.film.FilmService;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final FilmService filmService;

    public AccountService(AccountRepository accountRepository, FilmService filmService) {
        this.accountRepository = accountRepository;
        this.filmService = filmService;
    }

    /**
     * POST REQUEST:
     * Cadastra um novo usuário
     * 
     * @param account RequestBody com nome de usuário e senha
     * @return A conta cadastrada
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    //
    /**
     * GET REQUEST:
     * Busca um usuário pelo login e retorna as informações básicas
     * 
     * @param login O nome da conta desejada
     * @return A conta encontrada
     */
    public Optional<?> consultProfile(String login) {
        return accountRepository.findById(login);
    }

    /**
     * GET REQUEST:
     * Retorna uma lista com os detalhes dos filmes de uma lista de IDs de um
     * usuario
     * 
     * @param login    O nome da conta que possui a lista desejada
     * @param listName O nome da lista desejada
     * @return A lista detalhada de filmes
     */
    public List<Film> getFilmLista(String login, String listName) {
        Account user = accountRepository.findById(login).orElseThrow(() -> new RuntimeException("Usuário não existe"));
        if (user.getListas_map().containsKey(listName)) {
            return filmService.getFilmListDetails(getIdList(user.getListas_map(), listName));
        } else {
            throw new RuntimeException("Lista não existe");
        }
    }

    /**
     * PUT REQUEST:
     * Cria uma nova lista de filmes para um usuário
     * 
     * @param login    O nome da conta que irá armazenar a lista
     * @param listName O nome desejado para a lista
     * @return A conta com as informações atualizadas
     */
    public Account createLista(String login, String listName) {
        Account user = accountRepository.findById(login)
                .orElseThrow(() -> new RuntimeException("Usuário não existe"));
        if (user.getListas_map().containsKey(listName)) {
            throw new RuntimeException("Já existe uma lista com este nome");
        }
        user.getListas_map().put(listName, "");

        return accountRepository.save(user);
    }

    /**
     * PUT REQUEST:
     * Insere ou remove o ID de um filme na lista escolhida de um usuário de acordo
     * com a ação escolhida
     * 
     * @param action   A ação desejada: "add" ou "del"
     * @param login    O nome da conta que possui a lista desejada
     * @param listName O nome da lista desejada
     * @param movieId  O ID do filme no banco de dados da The Movie Database
     * @return A conta com as informações atualizadas
     */
    public Account updateLista(String action, String login, String listName, Integer movieId) {
        Account user = accountRepository.findById(login).orElseThrow(() -> new RuntimeException("Usuário não existe"));
        if (!user.getListas_map().containsKey(listName)) {
            throw new RuntimeException("Lista não existe");
        }
        if (action.equals("add") && !getIdList(user.getListas_map(), listName).contains(movieId)) {
            String listString = user.getListas_map().get(listName).concat(movieId + ",");
            user.getListas_map().put(listName, listString);

            return accountRepository.save(user);
        } else if (action.equals("del") && getIdList(user.getListas_map(), listName).contains(movieId)) {
            List<Integer> userList = getIdList(user.getListas_map(), listName);
            userList.remove(movieId);
            String listString = "";
            for (Integer i : userList) {
                listString = listString.concat(String.valueOf(i) + ",");
            }
            user.getListas_map().put(listName, listString);

            return accountRepository.save(user);
        }
        throw new RuntimeException("Ação inválida, tente novamente");
    }

    /**
     * PUT REQUEST:
     * Remove a lista escolhida da conta de um usuário
     * 
     * @param login    O nome da conta que possui a lista
     * @param listName O nome da lista para exclusão
     * @return A conta com as informações atualizadas
     */
    public Account deleteLista(String login, String listName) {
        Account user = accountRepository.findById(login).orElseThrow(() -> new RuntimeException("Usuário não existe"));
        if (!user.getListas_map().containsKey(listName)) {
            throw new RuntimeException("Lista não existe");
        }
        user.getListas_map().remove(listName);

        return accountRepository.save(user);
    }

    /**
     * MISC:
     * Encontra o valor dentro do listasMap com o nome(Key) passado e retorna uma
     * nova lista formatada de números inteiros para que possa ser utilizada em
     * outros métodos GET do AccountService
     * 
     * @param userMap  O listasMap do usuário que contém a lista desejada
     * @param listName O nome da lista desejada
     * @return A lista com os valores formatados em números inteiros
     */
    public List<Integer> getIdList(Map<String, String> userMap, String listName) {
        List<Integer> novaLista = new ArrayList<>();
        if (!userMap.containsKey(listName)) {
            throw new RuntimeException("Lista não existe");
        } else if (userMap.get(listName).equals("")) {
            return novaLista;
        }
        for (String s : userMap.get(listName).split(",")) {
            novaLista.add(Integer.valueOf(s));
        }

        return novaLista;
    }

}
