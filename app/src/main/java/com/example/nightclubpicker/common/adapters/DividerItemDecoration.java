package com.example.nightclubpicker.common.adapters;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public DividerItemDecoration(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // The divider stop and start points
        int dividerLeft = 50;
        int dividerRight = parent.getWidth() - 50;

        for (int i = 0; i < parent.getChildCount(); i++) {
            if (i != parent.getChildCount() - 1) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int dividerTop = child.getBottom() + params.bottomMargin;
                int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

                int position = parent.getChildAdapterPosition(child);
                Type type = Type.values()[parent.getAdapter().getItemViewType(position)];
                if (type != Type.HEADER_LIST_ITEM) {
                    mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
                    mDivider.draw(c);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // TODO: Decide if this is needed

//        if (parent.getChildAdapterPosition(view) == 0) {
//            return;
//        }
//
//        outRect.top = mDivider.getIntrinsicHeight();
    }
}
