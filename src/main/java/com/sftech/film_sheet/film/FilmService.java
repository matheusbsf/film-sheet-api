package com.sftech.film_sheet.film;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FilmService {

    // API Bearer Token
    @Value("${access.token}")
    private String accessToken;
    private final WebClient webClient;
    private String currentLanguage = "en-US";

    public FilmService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
    }

    // Retorna lista com detalhes de filmes que possuem as palavras-chave inseridas
    public List<FilmResponse> searchFilm(String input) {
        return webClient.get()
                .uri("/search/movie?include_adult=true&language=" + currentLanguage + "&query=" + input)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve().bodyToFlux(FilmResponse.class).collectList().block();
    }

    // Retorna detalhes do filme com o ID (int) do TMDB passado
    public Film getFilmById(Integer id) {
        return webClient.get().uri("/movie/" + id)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve().bodyToMono(Film.class).block();
    }

    // Recebe uma lista de IDs (int) de filmes e retorna uma lista com os detalhes
    public List<Film> getFilmList(List<Integer> list) {
        List<Film> tempList = new ArrayList<>();
        for (Integer id : list) {
            tempList.add(webClient.get().uri("/movie/" + id + "?language=" + currentLanguage)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve().bodyToMono(Film.class).block());
        }

        return tempList;
    }

}
