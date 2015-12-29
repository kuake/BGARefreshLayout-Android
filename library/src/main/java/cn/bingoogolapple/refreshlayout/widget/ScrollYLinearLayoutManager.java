package cn.bingoogolapple.refreshlayout.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zl on 15/12/29.
 */
public class ScrollYLinearLayoutManager extends LinearLayoutManager{
    private RecyclerView.Recycler mRecycler;

    public ScrollYLinearLayoutManager(Context context){
        super(context);
    }

    public ScrollYLinearLayoutManager(Context context, int orientation, boolean reverseLayout){
        super(context, orientation, reverseLayout);
    }

    public ScrollYLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        mRecycler = recycler;
    }

    public int getScrollY() {
        int scrollY = getPaddingTop();
        int firstVisibleItemPosition = findFirstVisibleItemPosition();

        if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < getItemCount()) {
            for (int i = 0; i < firstVisibleItemPosition; i++) {
                View view = mRecycler.getViewForPosition(i);
                if (view == null) {
                    continue;
                }
                if (view.getMeasuredHeight() <= 0) {
                    measureChildWithMargins(view, 0, 0);
                }
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                scrollY += lp.topMargin;
                scrollY += getDecoratedMeasuredHeight(view);
                scrollY += lp.bottomMargin;
                mRecycler.recycleView(view);
            }

            View firstVisibleItem = findViewByPosition(firstVisibleItemPosition);
            RecyclerView.LayoutParams firstVisibleItemLayoutParams = (RecyclerView.LayoutParams) firstVisibleItem.getLayoutParams();
            scrollY += firstVisibleItemLayoutParams.topMargin;
            scrollY -= getDecoratedTop(firstVisibleItem);
        }

        return scrollY;
    }
}
