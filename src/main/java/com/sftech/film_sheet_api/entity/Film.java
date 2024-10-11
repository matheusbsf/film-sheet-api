package com.sftech.film_sheet_api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Film {

    private int id;
    private String title;
    private String original_title;
    private String original_language;
    private String overview;
    private String release_date;
    private double vote_average;
    private int runtime;
    // Endereços de imagens do filme (posters, banners, etc)
    private String backdrop_path;
    private String poster_path;

}
