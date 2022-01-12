package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class OutHospitalBean {

    private String ksmc;// : 科室名称
    private String zyysxm;// : 住院医师姓名
    private String cyzd;//: 出院诊断（base64）
    private String ryzztz;// : 主要症状及体征（base64）
    private String zyid;// : 住院ID
    private String zzysxm;// : 王国防 ,
    private String cyqk;// : 出院情况（base64）
    private String ryzd;// : 入院诊断（base64）
    private String id;// : 出院小结ID
    private String kh;// : 患者唯一标识
    private String zlgc;// : 治疗过程（base64）
    private String jzlsh;// : 就诊流水号
    private String ch;// : 床号,
    private String cyyz;// : 出院医嘱（base64）
    private String rysjStr;//入院时间 yyyy-MM-dd HH:mm（String）
    private String cysjStr;//出院时间 yyyy-MM-dd HH:mm（String）


    public String getKsmc() {
        return ksmc;
    }

    public void setKsmc(String ksmc) {
        this.ksmc = ksmc;
    }

    public String getZyysxm() {
        return zyysxm;
    }

    public void setZyysxm(String zyysxm) {
        this.zyysxm = zyysxm;
    }

    public String getCyzd() {
        return cyzd;
    }

    public void setCyzd(String cyzd) {
        this.cyzd = cyzd;
    }

    public String getRyzztz() {
        return ryzztz;
    }

    public void setRyzztz(String ryzztz) {
        this.ryzztz = ryzztz;
    }

    public String getZyid() {
        return zyid;
    }

    public void setZyid(String zyid) {
        this.zyid = zyid;
    }

    public String getZzysxm() {
        return zzysxm;
    }

    public void setZzysxm(String zzysxm) {
        this.zzysxm = zzysxm;
    }

    public String getCyqk() {
        return cyqk;
    }

    public void setCyqk(String cyqk) {
        this.cyqk = cyqk;
    }

    public String getRyzd() {
        return ryzd;
    }

    public void setRyzd(String ryzd) {
        this.ryzd = ryzd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getZlgc() {
        return zlgc;
    }

    public void setZlgc(String zlgc) {
        this.zlgc = zlgc;
    }

    public String getJzlsh() {
        return jzlsh;
    }

    public void setJzlsh(String jzlsh) {
        this.jzlsh = jzlsh;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getCyyz() {
        return cyyz;
    }

    public void setCyyz(String cyyz) {
        this.cyyz = cyyz;
    }

    public String getRysjStr() {
        return rysjStr;
    }

    public void setRysjStr(String rysjStr) {
        this.rysjStr = rysjStr;
    }

    public String getCysjStr() {
        return cysjStr;
    }

    public void setCysjStr(String cysjStr) {
        this.cysjStr = cysjStr;
    }
}
