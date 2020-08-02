package com.jnshu.pojo;

public class Student {
    private Long id;

    private String name;

    private String position;

    private Integer state;

    private Integer work;

    private String intro;

    private String profession;

    private Long createat;

    private String createby;

    private Long updateat;

    private String updateby;

    public Student(Long id, String name, String position, Integer state, Integer work, String intro, String profession, Long createat, String createby, Long updateat, String updateby) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.state = state;
        this.work = work;
        this.intro = intro;
        this.profession = profession;
        this.createat = createat;
        this.createby = createby;
        this.updateat = updateat;
        this.updateby = updateby;
    }

    public Student() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getWork() {
        return work;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public Long getCreateat() {
        return createat;
    }

    public void setCreateat(Long createat) {
        this.createat = createat;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Long getUpdateat() {
        return updateat;
    }

    public void setUpdateat(Long updateat) {
        this.updateat = updateat;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", state=" + state +
                ", work=" + work +
                ", intro='" + intro + '\'' +
                ", profession='" + profession + '\'' +
                ", createat=" + createat +
                ", createby='" + createby + '\'' +
                ", updateat=" + updateat +
                ", updateby='" + updateby + '\'' +
                '}';
    }
}