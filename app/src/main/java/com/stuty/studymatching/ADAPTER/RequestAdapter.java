package com.stuty.studymatching.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuty.studymatching.OBJECT.RecievedData;
import com.stuty.studymatching.R;
import com.stuty.studymatching.RTROFIT.RecievedNumberData;
import com.stuty.studymatching.RTROFIT.RecievedUserData;
import com.stuty.studymatching.RTROFIT.RetrofitClient;
import com.stuty.studymatching.RTROFIT.ServiceApi;
import com.stuty.studymatching.SERVER.RequestForFcm;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RecievedData> requestInfoList;
    private Context mContext;
    public RequestAdapter(List<RecievedData> requestInfoList, Context mContext) {
        this.requestInfoList = requestInfoList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        RequestAdapter.ViewHolder viewHolder = new RequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof RequestAdapter.ViewHolder) {
            holder.onBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return requestInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private CardView cardview;
        private TextView writingTitle, userName, requestTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            writingTitle = itemView.findViewById(R.id.writing_title);
            userName = itemView.findViewById(R.id.user_name);
            requestTime = itemView.findViewById(R.id.request_time);

        }

        void onBind(int position) {
            this.position = position;
            cardview.setOnClickListener(this);
            writingTitle.setText(requestInfoList.get(position).getWritingTitle());
            userName.setText(requestInfoList.get(position).getSender());
            requestTime.setText(requestInfoList.get(position).getRequestTime());
        }

        @Override
        public void onClick(View v) {
            ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
            RequestForFcm forFcm = new RequestForFcm(service);
            forFcm.updateCheckReq(new RecievedNumberData(requestInfoList.get(position).getRecievedNumber()));
        }
    }
}
