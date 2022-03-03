package com.example.npstj.ModelClass;

public class FeeModel {


    String fee_amount, fee_discount, amount_description, discount_description,
            academic_year, Feesinfo, total;

    public FeeModel(String fee_amount, String fee_discount, String amount_description, String discount_description, String academic_year, String feesinfo, String total) {
        this.fee_amount = fee_amount;
        this.fee_discount = fee_discount;
        this.amount_description = amount_description;
        this.discount_description = discount_description;
        this.academic_year = academic_year;
        Feesinfo = feesinfo;
        this.total = total;
    }

    public FeeModel() {
    }



    public String getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(String fee_amount) {
        this.fee_amount = fee_amount;
    }

    public String getFee_discount() {
        return fee_discount;
    }

    public void setFee_discount(String fee_discount) {
        this.fee_discount = fee_discount;
    }

    public String getAmount_description() {
        return amount_description;
    }

    public void setAmount_description(String amount_description) {
        this.amount_description = amount_description;
    }

    public String getDiscount_description() {
        return discount_description;
    }

    public void setDiscount_description(String discount_description) {
        this.discount_description = discount_description;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getFeesinfo() {
        return Feesinfo;
    }

    public void setFeesinfo(String feesinfo) {
        Feesinfo = feesinfo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }



}
