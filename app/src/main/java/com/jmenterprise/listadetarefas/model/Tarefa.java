package com.jmenterprise.listadetarefas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {

    //necessário o ID para poder modificar a tarefa posteriormente
    private Long id;
    private String nomeTarefa;

    //crie os Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }
}
