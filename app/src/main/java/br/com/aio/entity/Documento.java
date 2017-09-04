package br.com.aio.entity;

import java.io.File;

/**
 * Created by elton on 31/08/2017.
 */

public class Documento {

    private Long id;
    private String nome;
    private File arquivo;

    public Documento(){}

    public Documento(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

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

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

}
