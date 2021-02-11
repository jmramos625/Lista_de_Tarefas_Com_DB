package com.jmenterprise.listadetarefas.helper;

import com.jmenterprise.listadetarefas.model.Tarefa;

import java.util.List;

public interface ITarefaDAO {
    //aqui será criado 4 métodos
    //salvar, atualizar, deletar e listar as tarefas
    public boolean salvar (Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();



}
