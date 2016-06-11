package com.j1j2.jposmvvm.common.widgets.recyclerviewchoicemode;

public interface SelectableHolder {
    void setSelectable(boolean selectable);
    boolean isSelectable();
    void setActivated(boolean activated);
    boolean isActivated();
    int getPosition();
    long getItemId();
}
