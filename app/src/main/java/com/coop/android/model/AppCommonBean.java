package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2019/3/6.
 */
public class AppCommonBean {
    private String min_version;
    private boolean is_update;
    private String update_description;
    private String new_version;
    private String type;
    private boolean force_update;

    public String getMin_version() {
        return min_version;
    }

    public void setMin_version(String min_version) {
        this.min_version = min_version;
    }

    public boolean isIs_update() {
        return is_update;
    }

    public void setIs_update(boolean is_update) {
        this.is_update = is_update;
    }

    public String getUpdate_description() {
        return update_description;
    }

    public void setUpdate_description(String update_description) {
        this.update_description = update_description;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isForce_update() {
        return force_update;
    }

    public void setForce_update(boolean force_update) {
        this.force_update = force_update;
    }

    @Override
    public String toString() {
        return "AppCommonBean{" +
                "min_version='" + min_version + '\'' +
                ", is_update=" + is_update +
                ", update_description='" + update_description + '\'' +
                ", new_version='" + new_version + '\'' +
                ", type='" + type + '\'' +
                ", force_update=" + force_update +
                '}';
    }
}
