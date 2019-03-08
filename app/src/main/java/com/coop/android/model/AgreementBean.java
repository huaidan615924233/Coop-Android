package com.coop.android.model;

/**
 * Created by MR-Z on 2019/3/6.
 */
public class AgreementBean {
    private String fileName;
    private int index;
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AgreementBean{" +
                "fileName='" + fileName + '\'' +
                ", index=" + index +
                ", url='" + url + '\'' +
                '}';
    }
}
