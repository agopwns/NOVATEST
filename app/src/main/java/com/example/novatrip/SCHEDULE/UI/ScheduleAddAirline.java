package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdpaterScheduleDate;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulDateItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.Unit.AIRLINE;
import com.example.novatrip.SCHEDULE.Unit.Date;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.novatrip.SCHEDULE.UI.ScheduleOlympicDailyScedule.UnixTimeToDay;

public class ScheduleAddAirline extends AppCompatActivity {
    String TAG = "ScheduleAddAirline";
    Button btn_add_airline;
    BottomSheetDialog dialog;
    AdpaterScheduleDate adpaterScheduleDate;
    ArrayList<Date> dateArrayList;
    String[] toColum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sche_dule_add_airline_acrivity);

        btn_add_airline = findViewById(R.id.btn_add_airline);
        dateArrayList = new ArrayList<>();

        //메인에서 보낸 여행일자가 나열된 string 을 받음
        final Intent intent = getIntent();
        toColum =  intent.getStringArrayExtra("toColum");

        //arraylist로 변형.

        for(int i = 0; i <toColum.length ; i++){

            Date date = new Date();
            date.setDate(UnixTimeToDay(toColum[i]));
            date.setUnixTime(toColum[i]);
            Log.d(TAG, "onCreate: 추가한 날짜 "+ UnixTimeToDay(toColum[i]));
            Log.d(TAG, "onCreate: 추가한 날짜 "+toColum[i]);
            dateArrayList.add(date);

        }


        initBottomRecyclerviewLocalList(dateArrayList);



        //항공편을 등록하면 며칠에 추가할지 날짜 선택 다이얼로그가 뜸.
        btn_add_airline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        adpaterScheduleDate.ClickListener_clickLisenerSchedulePlaceItem(new ClickLisenerSchedulDateItem() {
            @Override
            public void OnItemClick(Date date, int position) {

                Log.d(TAG, "OnItemClick: 항공편 일정을 추가할 날짜를 선택하면 ");

                AIRLINE airline = new AIRLINE();

                airline.setName_airline("티웨이항공(TW)101");
                airline.setName_start_airport("인천국제공항(ICN)");
                airline.setStart_date_airline("2020-07-29");
                airline.setStart_time_airline("08:30:00");
                airline.setName_end_airport("도쿄(나리타)(NRT)");
                airline.setEnd_date_airline("2020-07-29");
                airline.setEnd_time_airport("11:00:00");
                airline.setLat_start_airport(37.4601908);
                airline.setLon_start_airport(126.4385017);
                airline.setLat_end_airport(35.7719867);
                airline.setLon_end_airport(140.3906561);

                Log.d(TAG, "OnItemClick: 사용자가 입력한 항공일정 정보를 받아와서 airline객체로 만든다. ");

                Intent intent1 = new Intent();
                intent1.putExtra("airline", airline);// 사용자가 입력한 항공편 정보
                intent1.putExtra("date",date);//사용자가 선택한 날짜.
                intent1.putExtra("position",position);//사용자가 선택한 날짜.
                setResult(RESULT_OK, intent1);
                dialog.dismiss();
                finish();


            }
        });
    }


    private String UnixTimeToDay(String unixTime) {
        java.util.Date date = new java.util.Date(Long.valueOf(unixTime));
        SimpleDateFormat time = new SimpleDateFormat("MM / dd", Locale.CHINA);
        return time.format(date);
    }

    public void initBottomRecyclerviewLocalList (ArrayList<Date> dateArrayList){

        //사용자가 입력했다고 가정하고 정보를 가져온다.




        View dialogView = getLayoutInflater().inflate(R.layout.item_schedule_bottom_local_dialog, null);

        //리사이클러뷰 init
        RecyclerView recyclerView_addTravelLocalList = (RecyclerView) dialogView.findViewById(R.id.recyclerview_bottom_local_daily);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);
        recyclerView_addTravelLocalList.setLayoutManager(LayoutManager);
        adpaterScheduleDate = new AdpaterScheduleDate(getApplicationContext() );
        recyclerView_addTravelLocalList.setAdapter(adpaterScheduleDate);
        adpaterScheduleDate.setLocalList(dateArrayList);

        adpaterScheduleDate.notifyDataSetChanged();

        Log.d(TAG, "initBottomRecyclerviewLocalList: 비행기 일정을 보여줄 다이얼로그 init ");
        dialog = new BottomSheetDialog(ScheduleAddAirline.this);
        dialog.setContentView(dialogView);

    }
}
