package com.example.abhinav.webbrowser;

/**
 * Created by ABHINAV on 02-07-2016.
 */
public class History_model {
    String url;
    String time;
    private boolean isSelected;

    public History_model() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public History_model(String url,String time,boolean isSelected) {
        this.time=time;
        this.url = url;
        this.isSelected = isSelected;
    }
}
