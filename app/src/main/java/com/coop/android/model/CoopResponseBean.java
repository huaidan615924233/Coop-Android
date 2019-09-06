package com.coop.android.model;

import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class CoopResponseBean {
    private List<ProjectBean> project;

    public List<ProjectBean> getProject() {
        return project;
    }

    public void setProject(List<ProjectBean> project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "CoopResponseBean{" +
                "project=" + project +
                '}';
    }
}
