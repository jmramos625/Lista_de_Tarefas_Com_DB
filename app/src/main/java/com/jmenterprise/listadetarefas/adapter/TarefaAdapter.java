package com.jmenterprise.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmenterprise.listadetarefas.R;
import com.jmenterprise.listadetarefas.model.Tarefa;

import java.util.List;

//faça a herença do adapter
public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    //criar o construtor que vai receber a lista de tarefas
    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aqui iremos retornar a view que é o item de lista
        //para isso precisamos criar um layout
        //dê o context do proprio parent e depois o inflate vai ser o layout do adapter
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa_adapter, parent, false);

        //agora vamos instanciar a classe myViewHolder e passar o atributo itemLista
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //vamos pegar a lista de tarefas e definir pela position delas
        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());

    }

    @Override
    public int getItemCount() {
        //aqui dentro contar os itens que estarão sendo enviados
        //usando o próprio tamanho da lista de tarefas
        return this.listaTarefas.size();
    }

    //nesse caso é necessário o view holder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //vamos criar o textView da tarefa
        TextView tarefa;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tarefa = itemView.findViewById(R.id.ConstraintLayout);
        }
    }
}
