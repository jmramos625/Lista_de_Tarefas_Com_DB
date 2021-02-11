package com.jmenterprise.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jmenterprise.listadetarefas.R;
import com.jmenterprise.listadetarefas.adapter.TarefaAdapter;
import com.jmenterprise.listadetarefas.helper.DBHelper;
import com.jmenterprise.listadetarefas.helper.RecyclerItemClickListener;
import com.jmenterprise.listadetarefas.helper.TarefaDAO;
import com.jmenterprise.listadetarefas.model.Tarefa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //crie o atributo para o recyclerView
    private RecyclerView recyclerListaTarefas;
    //crie o atributo do Tarefa Adapter
    private TarefaAdapter tarefaAdapter;
    //criando o atributo da lista
    private List<Tarefa> listaTarefas = new ArrayList<>();
    //criando o atributo da Tarefa Selecionada
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerListaTarefas = findViewById(R.id.recyclerListaTarefas);

        //Criando eventos de clique no recyclerView
        //depois de criado a classe dentro do pacote helper
        recyclerListaTarefas = findViewById(R.id.recyclerListaTarefas);


        //primeiro atributo é o context
        //segundo parametro é o proprio recycler view
        //terceiro é a classe que vai instanciar os clicks nas tarefas
        recyclerListaTarefas.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerListaTarefas,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //com isso foi criada a classe para os clicks
                        //aqui vai ser feito a edição do recycler view
                        Log.i("click","onItemClick");
                        //aqui vamos usar para fazer a atualização da tarefa
                        //primeiro recuperando a tarefa para edição
                        //aqui recuperamos a posição da tarefa
                        Tarefa tarefaSelecionada = listaTarefas.get(position);
                        //agora enviando a tarefa selecionada para a tela de adicionar tarefa
                        Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                        //agora vá para a outra tela
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //primeiro recuperando a tarefa que queremos deletar
                        tarefaSelecionada = listaTarefas.get(position);


                        //aqui vai ser feito a deleção do item
                        Log.i("clique","onLongItemClick");
                        //antes de remover, vamos mostrar um AlertDialog, para confirmar se vai excluir ou não
                        //crie o alert Dialog
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        //configurando titulo e mensagem
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir a tarefa: '" + tarefaSelecionada.getNomeTarefa() + "' ?");

                        //configurando os botões
                        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //aqui iremos exclui-la
                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                if (tarefaDAO.deletar(tarefaSelecionada)){

                                    Toast.makeText(getApplicationContext(),
                                            "Sucesso ao excluir: '" + tarefaSelecionada.getNomeTarefa()+"'!",
                                            Toast.LENGTH_SHORT).show();
                                    //pois é necessário recarregar a lista
                                    carregarListaTarefas();

                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir Tarefa",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                        dialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "Tarefa: '" + tarefaSelecionada.getNomeTarefa()+"' não excluida!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        //exbir a dialog
                        dialog.create();
                        dialog.show();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                }
        ));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vamos criar a intent que passará para a próxima activity
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaTarefas(){
        //vai pegar o recycler view e fazer a montagem das Tarefas

        //listar tarefas
        //criar uma lista de objetos de tarefas
        //criar a classe tarefa dentro de model
        //criando tarefas ainda de modo estático
        //usaremos o TarefaDAO para recuperar essas tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        //aqui vamos recuperar as tarefas
        listaTarefas = tarefaDAO.listar();



        //primeiro configurar um adapter
        //para criar o adapater é necessário criar uma classe
        //agora passar a lista para dentro do adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);



        //segundo configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerListaTarefas.setLayoutManager(layoutManager);
        recyclerListaTarefas.setHasFixedSize(true);
        recyclerListaTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        //por último adapter
        recyclerListaTarefas.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {
        //carregue o método das listas dentro do onStar
        carregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}