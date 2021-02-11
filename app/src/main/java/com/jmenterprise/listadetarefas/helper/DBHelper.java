package com.jmenterprise.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

//essa classe irá criar o banco de dados
//também para salvar, atualizar, deletar, inserir, etc.
public class DBHelper extends SQLiteOpenHelper {

    //no construtor só será passado o context, os demais serão variáveis diretas na classe
    //esse atributo determina a versão do seu app
    public static int VERSION = 1;
    //agora o nome do BD
    public static String NOME_DB = "DB_TAREFAS";
    //criando nome da tarefa como constante
    public static String TABELA_TAREFAS = "tarefas";


    public DBHelper(@Nullable Context context) {
        super(context, NOME_DB , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //usado pra criar o banco de dados em um primeiro momento
        //usando a classe OpenHelper do SQLITE não é necessário digitar diretamente os códigos em SQL
        //nos codigos em SQL lembre-se de não deixar os textos colados
        //TEXT como not null obriga colocar o nome da tarefa
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT NOT NULL ); ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");

        }catch (Exception e){
            Log.i("INFO DB","Erro ao criar tabela"+ e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //para usar em novas versões do aplicativo caso necessário usar outro bancos de dados
        //ou atualizar tabelas

    }
}
