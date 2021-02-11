package com.jmenterprise.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.jmenterprise.listadetarefas.R;
import com.jmenterprise.listadetarefas.helper.TarefaDAO;
import com.jmenterprise.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        //chame o EditText
        editTarefa = findViewById(R.id.textTarefa);

        //agora recuperar a tarefa passada caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //agora configurar tarefa selecionada na caixa de texto
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    //criando o método para o Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSalvar:
                //Ação para o item Salvar
                //agora vamos salvar os items
                //instanciamos um TarefaDAO e com ele teremos todas as classes necessárias para implementação
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                //Crie um String para pegar a tarefa
                String nomeTarefa = editTarefa.getText().toString();

                //fazer uma verificação do nome tarefa
                if (tarefaAtual != null){//edicao
                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizando no banco de dados
                        if (tarefaDAO.atualizar(tarefa)){
                            Toast.makeText(AdicionarTarefaActivity.this,
                                    "Tarefa Atualizada",
                                    Toast.LENGTH_SHORT).show();
                            //para fechar a activity usamos o método finish
                            finish();
                        }else {
                            Toast.makeText(AdicionarTarefaActivity.this,
                                    "Não foi possível atualizar",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                else {
                    //salvando
                    //ante de salvar vamos instanciar uma tarefa
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);
                    //agora passe o objeto tarefa para salvar
                    if (tarefaDAO.salvar(tarefa)){
                        Toast.makeText(AdicionarTarefaActivity.this,
                                "Salvo",
                                Toast.LENGTH_SHORT).show();
                        //para fechar a activity usamos o método finish
                        finish();
                    }else {
                        Toast.makeText(AdicionarTarefaActivity.this,
                            "Não foi possível salvar",
                            Toast.LENGTH_SHORT).show();
                    }
                }





                break;
        }

        return super.onOptionsItemSelected(item);
    }
}