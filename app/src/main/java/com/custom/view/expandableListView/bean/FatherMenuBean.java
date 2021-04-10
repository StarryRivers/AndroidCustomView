package com.zjjy.buildstudyzj.model;

import java.util.ArrayList;

/**
 * 章节练习一级菜单Bean
 */
public class FatherMenuBean {
    private int id;
    private String name;
    private ArrayList<ChildMenuBean> sec;

    public FatherMenuBean(int id, String name, ArrayList<ChildMenuBean> sec) {
        this.id = id;
        this.name = name;
        this.sec = sec;
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

    public ArrayList<ChildMenuBean> getSec() {
        return sec;
    }

    public void setSec(ArrayList<ChildMenuBean> sec) {
        this.sec = sec;
    }
}
