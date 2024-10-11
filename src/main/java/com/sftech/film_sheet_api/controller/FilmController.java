package com.sftech.film_sheet_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sftech.film_sheet_api.entity.Film;
import com.sftech.film_sheet_api.response.FilmResponse;
import com.sftech.film_sheet_api.service.FilmService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "api/v1/film")
public class FilmController {

    @Autowired
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Operation(summary = "Retorna lista com detalhes dos filmes que possuem as palavras-chave informadas")
    @GetMapping("/search")
    public List<FilmResponse> searchFilm(@RequestParam(required = true) String keyword) {
        return filmService.searchFilm(keyword);
    }

    @Operation(summary = "Busca o ID informado no banco de dados da The Movie Database e retorna os detalhes do filme encontrado")
    @GetMapping("/consult")
    public Film getFilmById(@RequestParam(required = true) Integer id) {
        return filmService.getFilmById(id);
    }

}
