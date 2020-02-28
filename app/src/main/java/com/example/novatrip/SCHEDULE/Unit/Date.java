package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

public class Date implements Parcelable {

    private String unixTime;

    protected Date(Parcel in) {
        unixTime = in.readString();
        date = in.readString();
    }

    public Date( ) {
    }

    public static final Creator<Date> CREATOR = new Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };

    public String getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(unixTime);
        dest.writeString(date);
    }
}
