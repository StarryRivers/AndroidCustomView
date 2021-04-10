package com.zjjy.buildstudyzj.model;

import java.util.ArrayList;

public class ChildMenuBean {
    private int id;
    private String name;
    private ArrayList<GrandsonMenuBean> third;

    public ChildMenuBean(int id, String name, ArrayList<GrandsonMenuBean> third) {
        this.id = id;
        this.name = name;
        this.third = third;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GrandsonMenuBean> getThird() {
        return third;
    }

    public void setThird(ArrayList<GrandsonMenuBean> third) {
        this.third = third;
    }
}
