package com.tjj.bysjerp.bus.domain;

import java.util.List;

public class ReturnNotice {
    private int status;
    private Object msg;
    private List<ReturnData> data;


    public ReturnNotice(int status, Object msg, List<ReturnData> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ReturnNotice() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<ReturnData> getData() {
        return data;
    }

    public void setData(List<ReturnData> data) {
        this.data = data;
    }
}
