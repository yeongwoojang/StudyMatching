package com.stuty.studymatching.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.R;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    String[] types;
    private Context mContext;

    public TypeAdapter(String[] types, Context mContext) {
        this.types = types;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return types.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_type, parent, false);
        ViewHolder viewHolder = new TypeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.typeBt.setText(types[position]);
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private Button typeBt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeBt =(Button)itemView.findViewById(R.id.type_item_bt);
        }
    }
}
