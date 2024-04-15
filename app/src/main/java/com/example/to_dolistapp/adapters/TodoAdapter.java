package com.example.to_dolistapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapp.R;
import com.example.to_dolistapp.databinding.ItemTodoBinding;
import com.example.to_dolistapp.room.Note;

public class TodoAdapter extends ListAdapter<Note, TodoAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDisp().equals(newItem.getDisp());
        }
    };
    public TodoAdapter() {
        super(CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note  = getItem(position);
        holder.binding.tvItemTodoName.setText(note.getTitle());
        holder.binding.tvItemTodoDesc.setText(note.getDisp());
    }

    public Note getNote(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemTodoBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTodoBinding.bind(itemView);
        }
    }

}
