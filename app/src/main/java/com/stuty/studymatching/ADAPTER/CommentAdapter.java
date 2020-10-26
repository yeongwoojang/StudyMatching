package com.stuty.studymatching.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.ACTIVITY.DetailBoardActivity;
import com.stuty.studymatching.OBJECT.Comment;
import com.stuty.studymatching.OBJECT.Writing;
import com.stuty.studymatching.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment>  commentList;
    private Context mContext;
    public CommentAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.mContext = context;
    }




    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof CommentAdapter.ViewHolder) {
            holder.onBind(position);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private int position;
        private TextView userName,commentCntent,dateTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            commentCntent = itemView.findViewById(R.id.comment_content);
            dateTime = itemView.findViewById(R.id.write_time);
        }

        void onBind(int position) {
            this.position = position;
            userName.setText(commentList.get(position).getUsername());
            commentCntent.setText(commentList.get(position).getCommentContent());
            dateTime.setText(commentList.get(position).getCommentTime());

        }


    }
}
