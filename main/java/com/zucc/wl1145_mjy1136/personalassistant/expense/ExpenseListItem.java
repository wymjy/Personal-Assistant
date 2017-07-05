package com.zucc.wl1145_mjy1136.personalassistant.expense;

/**
 * Created by wanglei on 2017/7/2.
 */
public class ExpenseListItem {
    private int type;
    private long date;
    private String item_text;
    private String comment;
    private double item_mount;
    private String item_mount_state; //"in" or "out"
    private double tag_cost;
    private double tag_income;
    public final static int TAG=1;
    public final static int ITEM=2;
    public void setTag(long date,double tag_income,double tag_cost){
        this.tag_income=tag_income;
        this.tag_cost=tag_cost;
        this.date=date;
    }
    public void setItem(String item_text,String comment,double item_mount,String item_mount_sate,long date){
        this.item_text=item_text;
        this.comment=comment;
        this.item_mount=item_mount;
        this.item_mount_state=item_mount_sate;
        this.date=date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItem_text() {
        return item_text;
    }

    public String getComment() {return comment;}

    public double getItem_mount() {
        return item_mount;
    }

    public String getItem_mount_state() {return item_mount_state;}

    public long get_date() {
        return date;
    }

    public double getTag_cost() {
        return tag_cost;
    }

    public double getTag_income() { return tag_income;}
}
