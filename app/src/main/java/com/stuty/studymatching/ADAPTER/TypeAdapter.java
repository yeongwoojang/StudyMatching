package com.stuty.studymatching.ADAPTER;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.R;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private TypeAdapterListener listener;

    private String[] types;
    private Context mContext;
    private boolean isPressedBt = false;
    private String typeValue;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        ViewHolder viewHolder = new TypeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            holder.onBind(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        private Button typeBt;
        private boolean isBtClick = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeBt = (Button) itemView.findViewById(R.id.type_item_bt);
        }

        void onBind(int position) {
            this.position = position;

            typeBt.setText(types[position]);
            typeBt.setOnClickListener(this);

            if(isPressedBt){
                if(!isBtClick){
                    typeBt.setEnabled(false);
                }
            }else{
                if(!isBtClick){
                    typeBt.setEnabled(true);
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (!isBtClick) {
                typeBt.setBackgroundResource(R.color.amber500);
                typeValue = typeBt.getText().toString();

            } else {
                typeBt.setBackgroundResource(R.color.amber200);
                typeValue = "PART";

            }
            isBtClick = !isBtClick;
            isPressedBt = !isPressedBt;

            Log.d("Type",typeValue);
            listener.updateType(typeValue);
            notifyDataSetChanged();

        }
    }

    public interface TypeAdapterListener{
        void updateType(String typeValue);
    }

    public void setListener(TypeAdapterListener listener){
        this.listener = listener;
    }
}
