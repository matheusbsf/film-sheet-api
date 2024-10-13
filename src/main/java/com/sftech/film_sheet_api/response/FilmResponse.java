package com.sftech.film_sheet_api.response;

import java.util.List;

import com.sftech.film_sheet_api.model.Film;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmResponse {

    private List<Film> results;
}
