package com.example.hw316;

import java.util.Date;

public class Stage {

    private String objValue;
    private int docNumber;
    private int oldValue;
    private int newValue;
    private Date date;

    public Stage() {

    }

    public void setObjValue(String objValue) {
        this.objValue = objValue;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOldValue(int oldValue) {
        this.oldValue = oldValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public String getObjValue() {
        return objValue;
    }

    public String getColor() {

        return "";
    }

    public int getDocNumber() {
        return docNumber;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Stage{"
                + "objValue='" + objValue + '\''
                + ", docNumber=" + docNumber
                + ", oldValue=" + oldValue
                + ", newValue=" + newValue
                + ", Date=" + date
                + '}';
    }
}
