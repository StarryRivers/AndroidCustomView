package com.zjjy.buildstudyzj.model;

/**
 * 章节练习，三级目录Bean
 */
public class GrandsonMenuBean {
    private int id;
    private String name;
    private String pid;

    public GrandsonMenuBean(int id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
