package com.heyl.magicwater.App;

import com.squareup.otto.Bus;

/**
 * Created by heyl on 2016/10/26.
 */
public class AppBus  extends Bus {

    private static AppBus bus;

    public static AppBus getInstance() {
        if (bus == null) {
            bus = new AppBus();
        }
        return bus;
    }
}
