package com.example.novatrip.SCHEDULE.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulDateItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulPlaceItem;
import com.example.novatrip.SCHEDULE.Unit.AIRLINE;
import com.example.novatrip.SCHEDULE.Unit.Date;
import com.example.novatrip.SCHEDULE.Unit.Local;

import java.util.ArrayList;

public class AdpaterScheduleDate extends RecyclerView.Adapter<AdpaterScheduleDate.holer> {

    public String TAG  = "AdpaterScheduleDate";
    ArrayList<Date> localList = new ArrayList<>() ;
    ClickLisenerSchedulDateItem clickLisenerSchedulDateItem;
    Context context;

    public AdpaterScheduleDate(Context context_){
        this.context = context_;
    }

    public void setLocalList (ArrayList<Date> localList){
        this.localList = localList;
    }

    public class  holer extends RecyclerView.ViewHolder {

        TextView tv_add_local ,tv_localName ;

        public holer(@NonNull View itemView) {
            super(itemView);
            tv_add_local = itemView.findViewById(R.id.tv_add_local);
            tv_localName = itemView.findViewById(R.id.tv_localName);
        }
    }


    @NonNull
    @Override
    public AdpaterScheduleDate.holer onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_addtravel_local, viewGroup, false);
        return new AdpaterScheduleDate.holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpaterScheduleDate.holer holder, final int position) {
        final Date date= localList.get(position);

        holder.tv_localName.setText(date.getDate());

        // 만약 선택하기 버튼을 클릭한다면,
        holder.tv_add_local.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLisenerSchedulDateItem.OnItemClick(date,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return localList.size();
    }



    //    //이 메소드는 ChattingRoomList액티비티에서 호출해서 이벤트를 정의할 예정.
    public void ClickListener_clickLisenerSchedulePlaceItem(ClickLisenerSchedulDateItem clickLisenerSchedulDateItem) {
        this.clickLisenerSchedulDateItem = clickLisenerSchedulDateItem;
    }


}
