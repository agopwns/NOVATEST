package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AIRLINE implements Parcelable {

    private String name_airline;

    private String name_start_airport;
    private String start_date_airline;
    private String start_time_airline;
    private Double lat_start_airport;
    private Double lon_start_airport;

    private String name_end_airport;
    private String end_date_airline;
    private String end_time_airport;

    public Double getLat_start_airport() {
        return lat_start_airport;
    }

    public void setLat_start_airport(Double lat_start_airport) {
        this.lat_start_airport = lat_start_airport;
    }

    public Double getLon_start_airport() {
        return lon_start_airport;
    }

    public void setLon_start_airport(Double lon_start_airport) {
        this.lon_start_airport = lon_start_airport;
    }

    public Double getLat_end_airport() {
        return lat_end_airport;
    }

    public void setLat_end_airport(Double lat_end_airport) {
        this.lat_end_airport = lat_end_airport;
    }

    public Double getLon_end_airport() {
        return lon_end_airport;
    }

    public void setLon_end_airport(Double lon_end_airport) {
        this.lon_end_airport = lon_end_airport;
    }

    private Double lat_end_airport;
    private Double lon_end_airport;


    protected AIRLINE(Parcel in) {
        name_airline = in.readString();
        name_start_airport = in.readString();
        start_date_airline = in.readString();
        start_time_airline = in.readString();
        if (in.readByte() == 0) {
            lat_start_airport = null;
        } else {
            lat_start_airport = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon_start_airport = null;
        } else {
            lon_start_airport = in.readDouble();
        }
        name_end_airport = in.readString();
        end_date_airline = in.readString();
        end_time_airport = in.readString();
        if (in.readByte() == 0) {
            lat_end_airport = null;
        } else {
            lat_end_airport = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon_end_airport = null;
        } else {
            lon_end_airport = in.readDouble();
        }
    }

    public static final Creator<AIRLINE> CREATOR = new Creator<AIRLINE>() {
        @Override
        public AIRLINE createFromParcel(Parcel in) {
            return new AIRLINE(in);
        }

        @Override
        public AIRLINE[] newArray(int size) {
            return new AIRLINE[size];
        }
    };

    public String getName_airline() {
        return name_airline;
    }

    public void setName_airline(String name_airline) {
        this.name_airline = name_airline;
    }

    public String getName_start_airport() {
        return name_start_airport;
    }

    public void setName_start_airport(String name_start_airport) {
        this.name_start_airport = name_start_airport;
    }

    public String getStart_date_airline() {
        return start_date_airline;
    }

    public void setStart_date_airline(String start_date_airline) {
        this.start_date_airline = start_date_airline;
    }

    public String getStart_time_airline() {
        return start_time_airline;
    }

    public void setStart_time_airline(String start_time_airline) {
        this.start_time_airline = start_time_airline;
    }

    public String getName_end_airport() {
        return name_end_airport;
    }

    public void setName_end_airport(String name_end_airport) {
        this.name_end_airport = name_end_airport;
    }

    public String getEnd_date_airline() {
        return end_date_airline;
    }

    public void setEnd_date_airline(String end_date_airline) {
        this.end_date_airline = end_date_airline;
    }

    public String getEnd_time_airport() {
        return end_time_airport;
    }

    public void setEnd_time_airport(String end_time_airport) {
        this.end_time_airport = end_time_airport;
    }





    public AIRLINE() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name_airline);
        dest.writeString(name_start_airport);
        dest.writeString(start_date_airline);
        dest.writeString(start_time_airline);
        if (lat_start_airport == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat_start_airport);
        }
        if (lon_start_airport == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon_start_airport);
        }
        dest.writeString(name_end_airport);
        dest.writeString(end_date_airline);
        dest.writeString(end_time_airport);
        if (lat_end_airport == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat_end_airport);
        }
        if (lon_end_airport == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon_end_airport);
        }
    }
}
