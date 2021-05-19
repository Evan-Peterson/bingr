package com.bingr.app;

import android.content.Context;

public interface ActivityView {
    Context getContext();
    void update(Object o);
}
