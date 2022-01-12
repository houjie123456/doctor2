package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class FileCollectInfoBean {
    private String id;//
    private String relattype;//
    private String relatid;//
    private String filename;//
    private String filesuffix;//
    private String fileurl;//（文件相对路径）、
    private String createtime;//、
    private String filetype;//

    private String fileType;
    private String picUrl;


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelattype() {
        return relattype;
    }

    public void setRelattype(String relattype) {
        this.relattype = relattype;
    }

    public String getRelatid() {
        return relatid;
    }

    public void setRelatid(String relatid) {
        this.relatid = relatid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesuffix() {
        return filesuffix;
    }

    public void setFilesuffix(String filesuffix) {
        this.filesuffix = filesuffix;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
