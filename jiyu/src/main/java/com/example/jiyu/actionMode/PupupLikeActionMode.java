package com.example.jiyu.actionMode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by z on 2017/10/14.
 * O(∩_∩)~
 */

public class PupupLikeActionMode {
    private Context context;
    public PupupLikeActionMode(Context context) {
        this.context=context;
    }

    public void build(){
        LinearLayout linearLayout=new LinearLayout(context);

        View view=linearLayout;
        PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
