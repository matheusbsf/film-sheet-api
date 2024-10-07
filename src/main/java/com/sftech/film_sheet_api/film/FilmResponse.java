package com.sftech.film_sheet_api.film;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmResponse {

    private List<Film> results;
}
