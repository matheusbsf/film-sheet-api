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

@RestController
@RequestMapping(path = "api/v1/film")
public class FilmController {

    @Autowired
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/search")
    public List<FilmResponse> searchFilm(@RequestParam(required = true) String keyword) {
        return filmService.searchFilm(keyword);
    }

    @GetMapping("/consult")
    public Film getFilmById(@RequestParam(required = true) Integer id) {
        return filmService.getFilmById(id);
    }

}
