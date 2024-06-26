package com.beautysalon.demo.model;

import com.beautysalon.demo.utility.FileStore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Servizio {

    public static final String DIR_PAGES_SERVIZIO = "informations/servizio/";
    public static final String DIR_ADMIN_PAGES_SERVIZIO = "admin/servizio/";

    public static final String DIR_FOLDER_IMG = "servizio/profili";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;

    /*@NotBlank*/
    private Float prezzo;

    private String img;

    @ManyToOne
    private Professionista professionista;

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public Professionista getProfessionista() {
        return professionista;
    }

    public void setProfessionista(Professionista professionista) {
        this.professionista = professionista;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void eliminaImmagine() {
        FileStore.removeImg(DIR_FOLDER_IMG, this.getImg());
    }
}
