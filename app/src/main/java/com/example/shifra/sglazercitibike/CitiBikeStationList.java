package com.example.shifra.sglazercitibike;

public class CitiBikeStationList {
    public CitiBikeStationList(StationBeanList[] stationBeanList, String executionTime) {
        this.executionTime = executionTime;
        this.stationBeanList = stationBeanList;
    }

    private StationBeanList[] stationBeanList;
    private String executionTime;

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public StationBeanList[] getStationBeanList() {
        return stationBeanList;
    }

    public void setStationBeanList(StationBeanList[] stationBeanList) {
        this.stationBeanList = stationBeanList;
    }

}
