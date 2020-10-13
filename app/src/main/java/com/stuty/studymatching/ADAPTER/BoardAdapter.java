package com.stuty.studymatching.ADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<Writing> boardList;

    public BoardAdapter(List<Writing> boardList) {
        this.boardList = boardList;
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_recyclerview, parent, false);
        BoardAdapter.ViewHolder viewHolder = new BoardAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof BoardAdapter.ViewHolder) {
            holder.onBind(position);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private int position;
        private CardView cardView;
        private TextView writer, writingDay,title,content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.board_item);
            writer = itemView.findViewById(R.id.writer);
            writingDay = itemView.findViewById(R.id.writing_day);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }

        void onBind(int position) {
            this.position = position;
            writer.setText(boardList.get(position).getWriter());
            writingDay.setText(boardList.get(position).getWritingTime());
            title.setText(boardList.get(position).getTitle());
            content.setText(boardList.get(position).getContent());
        }
    }
}
