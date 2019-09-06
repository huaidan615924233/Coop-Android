package com.coop.android.model;


import java.util.List;

/**
 * Created by MR-Z on 2018/12/18.
 */
public class ProjectTeamListResponseBean {
    private List<EnterTeamBean> inveTeamList;
    private List<EnterTeamBean> entrTeamList;

    public List<EnterTeamBean> getInveTeamList() {
        return inveTeamList;
    }

    public void setInveTeamList(List<EnterTeamBean> inveTeamList) {
        this.inveTeamList = inveTeamList;
    }

    public List<EnterTeamBean> getEntrTeamList() {
        return entrTeamList;
    }

    public void setEntrTeamList(List<EnterTeamBean> entrTeamList) {
        this.entrTeamList = entrTeamList;
    }

    @Override
    public String toString() {
        return "ProjectTeamListResponseBean{" +
                "inveTeamList=" + inveTeamList +
                ", entrTeamList=" + entrTeamList +
                '}';
    }
}
