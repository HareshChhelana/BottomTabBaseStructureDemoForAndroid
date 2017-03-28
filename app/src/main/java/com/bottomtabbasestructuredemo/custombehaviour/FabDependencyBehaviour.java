package com.bottomtabbasestructuredemo.custombehaviour;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jack.m on 10/26/2016.
 */

public class FabDependencyBehaviour extends CoordinatorLayout.Behavior<View> {

    private int distance;

    public FabDependencyBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        float translationY = Math.min(0, parent.getBottom() - child.getBottom());
        child.setTranslationY(translationY);
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if(dependency.getVisibility() == View.VISIBLE){
            float translationY = Math.min(50, parent.getBottom() - child.getBottom());
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }
}
