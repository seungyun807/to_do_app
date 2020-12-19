package org.techtown.memo;

public class Memo {

    int seq;

    String id;
    String maintext; //메모
    String title; // 내
    String subtext; // 날짜
    int isdone; // 완료여부

    public Memo(int seq, String title, String maintext, String subtext, int isdone) {
        this.seq = seq;
        //this.num = num;용
        this.title = title;
        this.maintext = maintext;
        this.subtext = subtext;
        this.isdone = isdone;
    }

    public Memo(String title, String maintext, String subtext, int isdone) {
        this.maintext = maintext;
        this.title = title;
        this.subtext = subtext;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return String.valueOf(getSeq());
    }

    public void setId(String subtext) {
        this.id = id;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", maintext='" + maintext + '\'' +
                ", content='" + subtext + '\'' +
                ", nDate='" + isdone + '\'' +
                '}';
    }

}
