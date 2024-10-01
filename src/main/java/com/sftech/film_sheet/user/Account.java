package com.sftech.film_sheet.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @NonNull
    @Column(name = "login", unique = true)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    private String senha;
    @Column(name = "lista_assistidos", unique = true)
    private List<Integer> lista_assistidos;
    @Column(name = "lista_assistir", unique = true)
    private List<Integer> lista_assistir;

}
