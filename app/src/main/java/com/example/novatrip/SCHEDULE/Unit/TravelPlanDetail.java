package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 서버에 레트로핏으로 상세 여행정보를 받아올 때
 * 정보를 담을 클래스
 * **/

public class TravelPlanDetail implements Parcelable {

    private int idx_travel_plan_detail ;
    private int idx_travel_plan ;
    private String unixtime_travel_plan_detail;

    private int idx_place_or_olympic ;
    private int category_travel_plan_detail ;
    // 1 : 올림픽 경기
    // 2 : 관광
    // 3 : airline
    // 4 : 호텔
    private int travel_route_order ;

    private String arrival_time_user;
    private String finish_time_user;
    private String new_travel_plan_detail;

    protected TravelPlanDetail(Parcel in) {
        idx_travel_plan_detail = in.readInt();
        idx_travel_plan = in.readInt();
        unixtime_travel_plan_detail = in.readString();
        idx_place_or_olympic = in.readInt();
        category_travel_plan_detail = in.readInt();
        travel_route_order = in.readInt();
        arrival_time_user = in.readString();
        finish_time_user = in.readString();
        new_travel_plan_detail = in.readString();
    }

    public static final Creator<TravelPlanDetail> CREATOR = new Creator<TravelPlanDetail>() {
        @Override
        public TravelPlanDetail createFromParcel(Parcel in) {
            return new TravelPlanDetail(in);
        }

        @Override
        public TravelPlanDetail[] newArray(int size) {
            return new TravelPlanDetail[size];
        }
    };

    public int getIdx_travel_plan_detail() {
        return idx_travel_plan_detail;
    }

    public void setIdx_travel_plan_detail(int idx_travel_plan_detail) {
        this.idx_travel_plan_detail = idx_travel_plan_detail;
    }

    public int getIdx_travel_plan() {
        return idx_travel_plan;
    }

    public void setIdx_travel_plan(int idx_travel_plan) {
        this.idx_travel_plan = idx_travel_plan;
    }

    public String getUnixtime_travel_plan_detail() {
        return unixtime_travel_plan_detail;
    }

    public void setUnixtime_travel_plan_detail(String unixtime_travel_plan_detail) {
        this.unixtime_travel_plan_detail = unixtime_travel_plan_detail;
    }

    public int getIdx_place_or_olympic() {
        return idx_place_or_olympic;
    }

    public void setIdx_place_or_olympic(int idx_place_or_olympic) {
        this.idx_place_or_olympic = idx_place_or_olympic;
    }

    public int getCategory_travel_plan_detail() {
        return category_travel_plan_detail;
    }

    public void setCategory_travel_plan_detail(int category_travel_plan_detail) {
        this.category_travel_plan_detail = category_travel_plan_detail;
    }

    public int getTravel_route_order() {
        return travel_route_order;
    }

    public void setTravel_route_order(int travel_route_order) {
        this.travel_route_order = travel_route_order;
    }

    public String getArrival_time_user() {
        return arrival_time_user;
    }

    public void setArrival_time_user(String arrival_time_user) {
        this.arrival_time_user = arrival_time_user;
    }

    public String getFinish_time_user() {
        return finish_time_user;
    }

    public void setFinish_time_user(String finish_time_user) {
        this.finish_time_user = finish_time_user;
    }

    public String getNew_travel_plan_detail() {
        return new_travel_plan_detail;
    }

    public void setNew_travel_plan_detail(String new_travel_plan_detail) {
        this.new_travel_plan_detail = new_travel_plan_detail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx_travel_plan_detail);
        dest.writeInt(idx_travel_plan);
        dest.writeString(unixtime_travel_plan_detail);
        dest.writeInt(idx_place_or_olympic);
        dest.writeInt(category_travel_plan_detail);
        dest.writeInt(travel_route_order);
        dest.writeString(arrival_time_user);
        dest.writeString(finish_time_user);
        dest.writeString(new_travel_plan_detail);
    }
}
