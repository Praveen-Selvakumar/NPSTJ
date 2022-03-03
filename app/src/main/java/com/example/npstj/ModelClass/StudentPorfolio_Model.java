package com.example.npstj.ModelClass;

public class StudentPorfolio_Model {

    String item_name,item_number;
    int image_id;

    public StudentPorfolio_Model(String item_name, int image_id,String item_number) {
        this.item_name = item_name;
        this.image_id = image_id;
        this.item_number = item_number;
    }

    public StudentPorfolio_Model( ) {
    }

    public String getItem_number() {
        return item_number;
    }

    public void setItem_number(String item_number) {
        this.item_number = item_number;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
