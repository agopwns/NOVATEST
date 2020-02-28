package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterFragmentAddPlace;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleAddPlace;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleChoiceLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdpaterScheduleDate;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulPlaceItem;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.Place;
import com.yongbeom.aircalendar.AirCalendarDatePickerActivity;
import com.yongbeom.aircalendar.core.AirCalendarIntent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.novatrip.SCHEDULE.UI.ScheduleOlympicDailyScedule.UnixTimeToDay;


public class ScheduleAddHotelActivity extends AppCompatActivity {

    public  String TAG = "ScheduleAddHotelActivity";
    RecyclerView  recyclerview_localList;
    AdapterScheduleAddPlace adapterScheduleAddPlace;
    Button btn_add_hotel;
    RetroBaseApi retroBaseApi;
    TextView input_keyword ;
    ArrayList<Local> choiceLocalList;

    //현재 접속한 사용자를 보여줄 다이얼로그
    AlertDialog.Builder builder_checinout;
    AlertDialog dialog__checinout;
    EditText et_check_in , et_check_out;
    Button btn_add_term;

    long unixTime_in , unixTime_out;
    Place choice_hotel;



    public static SimpleDateFormat datetimeFormat_Date = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_local);

        //초기화
        recyclerview_localList = findViewById(R.id.recyclerview_localList);
        btn_add_hotel= findViewById(R.id.btn_add_travelPriod);
        input_keyword = findViewById(R.id.input_keyword);
        choiceLocalList = new ArrayList<>();
        //호텔을 보여주는 세로 리사이클러뷰 init
        initRecyclerviewChoiceHotelList();

        //input_keyword 키업
        input_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = input_keyword.getText().toString();
                Log.d(TAG, "onTextChanged: 검색한 키워드 " + keyword);
                searchKeyword(keyword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







        adapterScheduleAddPlace.ClickListener_clickLisenerSchedulePlaceItem(new ClickLisenerSchedulPlaceItem() {
            @Override
            public void OnItemClick(Place place, int position) {
                Log.d(TAG, "OnItemClick:  호텔 선택  클릭!");

                choice_hotel = place;

                initDialog_checkInOut();


            }
        });


    }


    public void initDialog_checkInOut(){
        builder_checinout = new AlertDialog.Builder(ScheduleAddHotelActivity.this);
        builder_checinout.setCancelable(true);//Back키 눌렀을 경우 Dialog Cancle 여부 설정
        View view = LayoutInflater.from(ScheduleAddHotelActivity.this).inflate(R.layout.dialog_schedule_add_hotel, null, false);
        InitUpdateDialog(view);
        builder_checinout.setView(view);
        dialog__checinout = builder_checinout.create();

        dialog__checinout.show();
    }

    public void InitUpdateDialog(View view) {

        //현재 접속한 사용자 목록을 보여줄 리사이클러뷰 연결.
        //리사이클러뷰 연결
        et_check_in = view.findViewById(R.id.et_check_in);
        et_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //달력 init
                initCalender("in", 2020, 7, 29 ,
                        2020, 8,02);
            }
        });


        et_check_out = view.findViewById(R.id.et_check_out);
        et_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 선택한 호텔 기간동안
                initCalender("out",2020, 7, 29 ,
                        2020, 8,02);
            }
        });
       Button btn_term = view.findViewById(R.id.btn_term);
        btn_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Log.d(TAG, "onClick: 기간 등록 ");
                        Intent intent = new Intent();
                        intent.putExtra("hotel",choice_hotel);
                        intent.putExtra("check_in", String.valueOf(unixTime_in));
                        intent.putExtra("check_out", String.valueOf(unixTime_out));

                Log.d(TAG, "onClick: 선택한 check_in  " +String.valueOf(unixTime_in) ) ;
                Log.d(TAG, "onClick: 선택한 check_out  " +String.valueOf(unixTime_out) ) ;

                String unixTime_check_in =  UnixTimeToDay(unixTime_in+"");
                String unixTime_check_out =  UnixTimeToDay(unixTime_out+"");


                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

                try {
                     Date date_check_in  = time.parse(unixTime_check_in);
                     Date date_check_out = time.parse(unixTime_check_out);

                    Log.d(TAG, "UnixTimeToDay:date_check_out  "+date_check_out);
                    Log.d(TAG, "UnixTimeToDay: date_check_in " + date_check_in);

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                        setResult(RESULT_OK, intent);
                        dialog__checinout.dismiss();
                        finish();

            }
        });

    }

    public Long componentTimeToTimestamp(int year, int month, int day ) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        return  c.getTimeInMillis() ;
    }



    public void initCalender(String type , int minDate_year ,int  minDate_month , int  minDate_date ,
                             int maxDate_year , int maxDate_month , int maxDate_date ){



        switch (type){
            case "in" :

                final Calendar pickedDate = Calendar.getInstance();
                Calendar minDate = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();

                DatePickerDialog datePickerDialog  = new DatePickerDialog(
                        ScheduleAddHotelActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                Log.d(TAG, "onDateSet: "+"select date : "+ year + "-"+(month+1)+"-"+dayOfMonth);
                                unixTime_in = componentTimeToTimestamp(year, month , dayOfMonth);

                                String  check_in = year + "-"+(month+1)+"-"+dayOfMonth;
                                Log.d(TAG, "onDateSet: ");
                                et_check_in.setText(check_in);
                                Toast.makeText(getApplicationContext(),"select date : "+ year + "-"+(month+1)+"-"+dayOfMonth,Toast.LENGTH_LONG).show();
                            }
                        },

                        pickedDate.get(Calendar.YEAR),
                        pickedDate.get(Calendar.MONTH),
                        pickedDate.get(Calendar.DATE)
                );


                minDate.set(minDate_year,minDate_month-1,minDate_date);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());

                maxDate.set(maxDate_year,maxDate_month-1,maxDate_date);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());


                datePickerDialog.show();


                break;

            case "out" :

                final Calendar pickedDate_out = Calendar.getInstance();
                Calendar minDate_out = Calendar.getInstance();
                Calendar maxDate_out = Calendar.getInstance();

//                pickedDate.set(2018,2-1,12);
                  DatePickerDialog datePickerDialog_out  = new DatePickerDialog(
                        ScheduleAddHotelActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                unixTime_out =   componentTimeToTimestamp(year, month , dayOfMonth);
                                String check_out = year + "-"+(month+1)+"-"+dayOfMonth;
                                Log.d(TAG, "onDateSet: "+"select date : "+ year + "-"+(month+1)+"-"+dayOfMonth);
                                et_check_out.setText(check_out);
                                Toast.makeText(getApplicationContext(),"select date : "+ year + "-"+(month+1)+"-"+dayOfMonth,Toast.LENGTH_LONG).show();
                            }
                        },

                          pickedDate_out.get(Calendar.YEAR),
                          pickedDate_out.get(Calendar.MONTH),
                          pickedDate_out.get(Calendar.DATE)
                  );
                       minDate_out.set(minDate_year,minDate_month-1,minDate_date);
                        datePickerDialog_out.getDatePicker().setMinDate(minDate_out.getTime().getTime());

                        maxDate_out.set(maxDate_year,maxDate_month-1,maxDate_date);
                        datePickerDialog_out.getDatePicker().setMaxDate(maxDate_out.getTimeInMillis());


                        datePickerDialog_out.show();


                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//        if (resultCode == RESULT_OK && requestCode == HotelIDX) {
//            if(data != null){
//                Toast.makeText(this, "Select Date range : " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE) + " ~ " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE), Toast.LENGTH_SHORT).show();
//            }
//        }
    }


    //사용자가 입력한 키워드와 일치하는 값이 서버에 있는지 확인.
    public void searchKeyword (String keyword ){
        retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
        retroBaseApi.postSearchLoaclKeyWord_Hotel(keyword).enqueue(new Callback<List<Place>>() {

            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                ArrayList<Place> localArrayList = new ArrayList<>();

                for(int i = 0; i < response.body().size() ; i++){
                    localArrayList.add(i,response.body().get(i));
                    Log.d(TAG, "onResponse: localArrayList.get(i).getName_olympic_game() "+localArrayList.get(i).getName_place_detail());
                }

                adapterScheduleAddPlace.setPlaceArrayList(localArrayList);
                adapterScheduleAddPlace.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

    //선택한 지역 보여주는 가로 리사이클러뷰
    public void initRecyclerviewChoiceHotelList (){
        @SuppressLint("WrongConstant")
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerview_localList.setLayoutManager(LayoutManager);
        adapterScheduleAddPlace = new AdapterScheduleAddPlace(getApplicationContext());
        recyclerview_localList.setAdapter(adapterScheduleAddPlace);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);

    }

}
