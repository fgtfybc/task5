package com.jnshu.pojo;

public class Position {
    private Long id;

    private String img;

    private String content;

    private String name;

    private String intro;

    private Integer threshold;

    private Integer difficulty;

    private String cycle;

    private Integer scarcity;

    private Integer payfirst;

    private Integer paysecond;

    private Integer paythirdly;

    private String basics;

    private Long createat;

    private String createby;

    private Long updateat;

    private String updateby;

    public Position(Long id, String img, String content, String name, String intro, Integer threshold, Integer difficulty, String cycle, Integer scarcity, Integer payfirst, Integer paysecond, Integer paythirdly, String basics, Long createat, String createby, Long updateat, String updateby) {
        this.id = id;
        this.img = img;
        this.content = content;
        this.name = name;
        this.intro = intro;
        this.threshold = threshold;
        this.difficulty = difficulty;
        this.cycle = cycle;
        this.scarcity = scarcity;
        this.payfirst = payfirst;
        this.paysecond = paysecond;
        this.paythirdly = paythirdly;
        this.basics = basics;
        this.createat = createat;
        this.createby = createby;
        this.updateat = updateat;
        this.updateby = updateby;
    }

    public Position() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }

    public Integer getScarcity() {
        return scarcity;
    }

    public void setScarcity(Integer scarcity) {
        this.scarcity = scarcity;
    }

    public Integer getPayfirst() {
        return payfirst;
    }

    public void setPayfirst(Integer payfirst) {
        this.payfirst = payfirst;
    }

    public Integer getPaysecond() {
        return paysecond;
    }

    public void setPaysecond(Integer paysecond) {
        this.paysecond = paysecond;
    }

    public Integer getPaythirdly() {
        return paythirdly;
    }

    public void setPaythirdly(Integer paythirdly) {
        this.paythirdly = paythirdly;
    }

    public String getBasics() {
        return basics;
    }

    public void setBasics(String basics) {
        this.basics = basics == null ? null : basics.trim();
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
}