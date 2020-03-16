package com.chengzhen.foodbusiness.event;

import org.greenrobot.eventbus.EventBus;

public class EmptyEvent {

    public boolean success;

    public void post(){
        EventBus.getDefault().post(this);
    }
}
