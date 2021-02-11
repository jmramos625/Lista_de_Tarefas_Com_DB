package com.jmenterprise.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jmenterprise.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{
    //dentro dessa classe vamos ter os métodos para:
    //salvar, atualizar, deletar e listar o nosso conjunto de tarefas
    //com os métodos feitos no ITarefaDAO, ele são implementados na classe que Herda dele

    //criando atributos para poder definir se vai escrever ou apagar algo
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        //esse context do construtor será o context do DB para fazer a implementação dos dados
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    //para salvar será necessário o DBHelper com um construtor
    @Override
    public boolean salvar(Tarefa tarefa) {
        //necessário colocar dentro de um try catch
        try {
            //instanciando a classe que vai pode colocar os dados na tabela
            ContentValues cv = new ContentValues();
            //por último o valor que será inserido
            //primeiro parametro é o nome do campo e depois o valor colocado nele
            //agora vamos colocar a propria tarefa como atributo, com isso podemos chamá-la na classe AdicionarTarefaActivity
            //não é necessário colocar o ID pois ele será feito de forma automática pelo DB
            cv.put("nome", tarefa.getNomeTarefa());


            //agora o insert no tabela
            //primeiro parametro do insert é o nome da tabela
            escreve.insert(DBHelper.TABELA_TAREFAS, null, cv);

        }catch (Exception e){
            //vai retornar falso caso não consiga adicionar os dados
            Log.i("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;

        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        //método para atualizar tarefa
        //agora recebendo a tarefa e a atualizando
        //criando um try catch

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            //utilizando o UPDATE
            //é necessário criar um array de string pois com ele vamos atualizar mais um campo no BD
            //dentro desse string de args colocamos os argumentos necessários
            //com isso dentro do array converta o getID para o toString
            //o id=? é usado para determinar o argumento no proximo parametro
            String[] args = {tarefa.getId().toString()};
            escreve.update(DBHelper.TABELA_TAREFAS,cv,"id=?", args);

        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        //aqui vamos usar o código pra deletar a tarefa
        try {
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args);

        }catch (Exception e){
            Log.i("INFO", "Erro ao excluir tarefa" + e.getMessage());
            return false;

        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        //cria o array para a lista
        List<Tarefa> tarefas = new ArrayList<>();

        //agora usando o SQL para poder retornar a tabela
        //selecionando todos os dados
        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        //crie o cursor para ser percorrido
        Cursor c = le.rawQuery(sql, null);
        //while para percorrer o cursor
        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();

            //criar variáveis para melhorar a organização
            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));


            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            //agora criando o List como o array da tarefas
            tarefas.add(tarefa);


        }

        return tarefas;
    }


}
