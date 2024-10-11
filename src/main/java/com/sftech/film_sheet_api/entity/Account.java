package com.sftech.film_sheet_api.entity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "login", length = 20)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha", nullable = false, length = 50)
    private String senha;
    @ElementCollection
    @CollectionTable(name = "account_listas", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "listas_map")
    private Map<String, String> listas_map = new HashMap<>() {
        {
            put("Padrão", "");
        }
    };
}
