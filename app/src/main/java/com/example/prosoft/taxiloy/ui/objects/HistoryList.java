package com.example.prosoft.taxiloy.ui.objects;

/**
 * Created by Manh on 1/19/2016.
 */
public class HistoryList {
    private String historyId;
    private String historyName;
    private String historyDate;

    public HistoryList(){
    }

    public HistoryList(String historyId, String historyName, String historyDate){
        this.historyId = historyId;
        this.historyName = historyName;
        this.historyDate = historyDate;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }
}
