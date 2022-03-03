package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class TransportModel {


    @SerializedName("id") String id;
    @SerializedName("start_date") String start_date;
    @SerializedName("end_date") String end_date;
    @SerializedName("added_by") String added_by;
    @SerializedName("added_date") String added_date;
    @SerializedName("roll_number") String roll_number;
    @SerializedName("route_code") String route_code;
    @SerializedName("pickup_and_drop") String pickup_and_drop;
    @SerializedName("vechicle_number") String vechicle_number;
    @SerializedName("amount") String amount;
    @SerializedName("driver_name") String driver_name;
    @SerializedName("driver_contact_number") String driver_contact_number;

    public TransportModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getRoute_code() {
        return route_code;
    }

    public void setRoute_code(String route_code) {
        this.route_code = route_code;
    }

    public String getPickup_and_drop() {
        return pickup_and_drop;
    }

    public void setPickup_and_drop(String pickup_and_drop) {
        this.pickup_and_drop = pickup_and_drop;
    }

    public String getVechicle_number() {
        return vechicle_number;
    }

    public void setVechicle_number(String vechicle_number) {
        this.vechicle_number = vechicle_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_contact_number() {
        return driver_contact_number;
    }

    public void setDriver_contact_number(String driver_contact_number) {
        this.driver_contact_number = driver_contact_number;
    }
}
