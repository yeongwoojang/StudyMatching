package com.stuty.studymatching.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.ACTIVITY.DetailBoardActivity;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<Writing>  boardList;
    private Context mContext;
    private int senderNumber;
    private String sender;
    public BoardAdapter(Context context, List<Writing> boardList, int senderNumber, String sender) {
        this.boardList = boardList;
        this.mContext = context;
        this.senderNumber = senderNumber;
        this.sender = sender;
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        private CardView cardView;
        private TextView writingNumber,writer, writingDay,title,content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.board_item);
            writingNumber = itemView.findViewById(R.id.writing_number);
            writer = itemView.findViewById(R.id.writer);
            writingDay = itemView.findViewById(R.id.writing_day);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }

        void onBind(int position) {
            this.position = position;
            writer.setText(boardList.get(position).getUserName());
            writingDay.setText(boardList.get(position).getWritingTime());
            title.setText(boardList.get(position).getTitle());
            content.setText(boardList.get(position).getContent());
             writingNumber.setText("No."+boardList.get(position).getWritingNumber());
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DetailBoardActivity.class);
            //userNumber넘기기
            intent.putExtra("senderNumber", senderNumber);
            intent.putExtra("sender",sender);
            intent.putExtra("recipient",boardList.get(position));
            //FCM을 받을 유저의 userNumber
            intent.putExtra("FcmRecipientNumber",boardList.get(position).getUserNumber());
            intent.putExtra("writer",writer.getText().toString());
            intent.putExtra("title",title.getText().toString());
            intent.putExtra("content",content.getText().toString());
            intent.putExtra("writingNumber",boardList.get(position).getWritingNumber());
            mContext.startActivity(intent);
        }
    }
}
