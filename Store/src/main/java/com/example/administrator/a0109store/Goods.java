package com.example.administrator.a0109store;

import java.io.Serializable;

/**
 * Created by Administrator
 * on 2017/1/12.
 */

public class Goods implements Serializable {
    private int _id;
    private String name;
    private String num;
    private String inprice;
    private String outprice;
    private String quantity;
    private String imagesrc;
    private String remark;
    private String codebar;


    public Goods() {
    }

    public Goods(int _id, String name, String num, String inprice, String outprice, String quantity, String imagesrc, String remark, String codebar) {
        this._id = _id;
        this.name = name;
        this.num = num;
        this.inprice = inprice;
        this.outprice = outprice;
        this.quantity = quantity;
        this.imagesrc = imagesrc;
        this.remark = remark;
        this.codebar = codebar;
    }

    public Goods(String name, String num, String inprice, String outprice, String quantity, String imagesrc, String remark, String codebar) {
        this.name = name;
        this.num = num;
        this.inprice = inprice;
        this.outprice = outprice;
        this.quantity = quantity;
        this.imagesrc = imagesrc;
        this.remark = remark;
        this.codebar = codebar;
    }

    public Goods(String name, String outprice) {
        this.name = name;
        this.outprice = outprice;
    }

    public Goods(String name, String outprice, String num) {

        this.name = name;
        this.outprice = outprice;
        this.num = num;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInprice() {
        return inprice;
    }

    public void setInprice(String inprice) {
        this.inprice = inprice;
    }

    public String getOutprice() {
        return outprice;
    }

    public void setOutprice(String outprice) {
        this.outprice = outprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    public String getCodebar() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar = codebar;
    }
}
