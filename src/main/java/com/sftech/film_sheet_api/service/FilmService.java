package com.sftech.film_sheet_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sftech.film_sheet_api.model.Film;
import com.sftech.film_sheet_api.response.FilmResponse;

@Service
public class FilmService {

    // API Bearer Token
    @Value("${access.token}")
    private String accessToken;
    private final WebClient webClient;
    // Idioma das informações recolhidas da API da TMDB
    private String currentLanguage = "pt-BR";

    public FilmService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
    }

    /**
     * GET REQUEST:
     * Retorna lista com detalhes dos filmes que possuem as palavras-chave
     * informadas
     * 
     * @param input Palavras-chave separadas por vírgula (,)
     * @return A lista de resultados da busca
     */
    public List<FilmResponse> searchFilm(String input) {
        return webClient.get()
                .uri("/search/movie?include_adult=true&language=" + currentLanguage + "&query=" + input)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve().bodyToFlux(FilmResponse.class).collectList().block();
    }

    /**
     * GET REQUEST:
     * Busca o ID informado no banco de dados da The Movie Database e retorna os
     * detalhes do filme encontrado
     * 
     * @param id ID (Integer) do filme a ser consultado
     * @return Os detalhes do filme mapeados de acordo com a entity Film
     */
    public Film getFilmById(Integer id) {
        return webClient.get().uri("/movie/" + id)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve().bodyToMono(Film.class).block();
    }

    /**
     * MISC:
     * Recebe uma lista de IDs (Integer) de filmes e retorna uma lista com os filmes
     * detalhados
     * 
     * @param list Lista de números inteiros com IDs de filmes de acordo com a TMDB
     * @return A lista com objetos mapeados de acordo com a entity Film
     */
    public List<Film> getFilmListDetails(List<Integer> list) {
        List<Film> tempList = new ArrayList<>();
        for (Integer id : list) {
            tempList.add(webClient.get().uri("/movie/" + id + "?language=" + currentLanguage)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve().bodyToMono(Film.class).block());
        }
        return tempList;
    }

}
