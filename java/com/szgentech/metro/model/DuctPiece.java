package com.szgentech.metro.model;

/**
 *质量（整合管片系统的）
 *
 *@Author: xuxiaoming
 *
 *@date: 2017/11/29
 */
public class DuctPiece {
    private String time;  //上报时间
    private String name;  //上报人员
    private String content;   //上报内容
    private String ringNumber;   //环号
    private String layout;   //衬砌块
    private String position;   //缺陷点位
    private int type;    //缺陷类型 (1:环缝错台,2:纵缝错台,3:碎裂,4：渗漏，5：横向裂纹，6：纵向裂纹，7：其他类型)
    private String comment;  //备注
    private String uuid;
    private String flag;
    private String insertTime;
    private String picName;     //图片信息(保存路径)
    private String isDeleted;    //是否被删除
    private String isUpdated;    //是否被更新
    private String needUpload;  //是否需要上传
    private String kingType;  //k点位置(01,02,03,04,05,06,07,08,09,10)
    private String x;
    private String y;
    private String section;   //区间
    private String line;     //线路（1：左线，0：右线）
    private String length;    //缺陷长度(mm)
    private String hType;
    private String hGap;      //盾尾间隙(上/下/左/右)
    private String rgType;    //管片类型（10/16/big/other）

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRingNumber() {
        return ringNumber;
    }

    public void setRingNumber(String ringNumber) {
        this.ringNumber = ringNumber;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getNeedUpload() {
        return needUpload;
    }

    public void setNeedUpload(String needUpload) {
        this.needUpload = needUpload;
    }

    public String getKingType() {
        return kingType;
    }

    public void setKingType(String kingType) {
        this.kingType = kingType;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String gethType() {
        return hType;
    }

    public void sethType(String hType) {
        this.hType = hType;
    }

    public String gethGap() {
        return hGap;
    }

    public void sethGap(String hGap) {
        this.hGap = hGap;
    }

    public String getRgType() {
        return rgType;
    }

    public void setRgType(String rgType) {
        this.rgType = rgType;
    }

    @Override
    public String toString() {
        return "POJO [time=" + time + ", name=" + name + ", content=" + content + ", ringNumber=" + ringNumber
                + ", layout=" + layout + ", position=" + position + ", type=" + type + ", comment=" + comment
                + ", uuid=" + uuid + ", flag=" + flag + ", insertTime=" + insertTime + ", picName=" + picName
                + ", isDeleted=" + isDeleted + ", isUpdated=" + isUpdated + ", needUpload=" + needUpload + ", kingType="
                + kingType + ", x=" + x + ", y=" + y + ", section=" + section + ", line=" + line + ", length=" + length
                + ", hType=" + hType + ", hGap=" + hGap + ", rgType=" + rgType + "]";
    }


}
