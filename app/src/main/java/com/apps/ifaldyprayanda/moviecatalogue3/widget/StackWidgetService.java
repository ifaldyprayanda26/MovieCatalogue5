package com.apps.ifaldyprayanda.moviecatalogue3.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewFactory(this.getApplicationContext());
    }
}
