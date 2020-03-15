package com.shenl.project.bean;

import java.util.List;

public class MessageCore {
    List<SelectCore> activities;
    List<Info>infos;

    public List<SelectCore> getActivities() {
        return activities;
    }

    public void setActivities(List<SelectCore> activities) {
        this.activities = activities;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }
}
