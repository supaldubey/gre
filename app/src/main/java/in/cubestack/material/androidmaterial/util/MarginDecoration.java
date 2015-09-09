package in.cubestack.material.androidmaterial.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.cubestack.material.androidmaterial.R;

/**
 * Created by arunk on 7/6/2015.
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_item_margin);
    }

    public MarginDecoration(Context context, int spacing) {
        margin = context.getResources().getDimensionPixelSize(spacing);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}