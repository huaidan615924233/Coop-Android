package com.coop.android.model;


/**
 * Created by MR-Z on 2018/12/18.
 */
public class ProjectDetailResponseBean {
    private ProjectBean project;

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "ProjectDetailResponseBean{" +
                "project=" + project +
                '}';
    }
}
