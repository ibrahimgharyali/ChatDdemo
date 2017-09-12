package com.example.ibrahim.chatddemo.listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.ibrahim.chatddemo.utils.DebugUtils;


public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    @NonNull
    private final RecyclerView.LayoutManager mLayoutManager;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // True if we are still waiting for the last set of data to load.
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean isUseLinearLayoutManager;
    private boolean isUseGridLayoutManager;
    private boolean isUseStaggeredGridLayoutManager;
/*
    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }
*/

    protected EndlessRecyclerViewScrollListener(@NonNull GridLayoutManager layoutManager, int threshold) {
        this.mLayoutManager = layoutManager;
        isUseGridLayoutManager = true;
        visibleThreshold = threshold;
//        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold);
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
//        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold + " layoutManager.getSpanCount()=" + layoutManager.getSpanCount());
    }

    protected EndlessRecyclerViewScrollListener(@NonNull StaggeredGridLayoutManager layoutManager, int threshold) {
        this.mLayoutManager = layoutManager;
        isUseStaggeredGridLayoutManager = true;
        visibleThreshold = threshold;
        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold);
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold + " layoutManager.getSpanCount()=" + layoutManager.getSpanCount());
    }

    protected EndlessRecyclerViewScrollListener(@NonNull LinearLayoutManager layoutManager, int threshold) {
        this.mLayoutManager = layoutManager;
        isUseLinearLayoutManager = true;
        if (threshold > 1)
            visibleThreshold = threshold;
        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold);
    }

/*
    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }
*/

    private int getLastVisibleItem(@NonNull int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

/*        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {

        }*/

        if (isUseGridLayoutManager && mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
        } else if (isUseStaggeredGridLayoutManager && mLayoutManager instanceof StaggeredGridLayoutManager) {
            lastVisibleItemPosition = getLastVisibleItem(((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null));
        } else if (isUseLinearLayoutManager && mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = 0;
            this.previousTotalItemCount = totalItemCount;
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if ((totalItemCount > previousTotalItemCount)) {
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too


        DebugUtils.log("CHECK : visibleThreshold=" + visibleThreshold);
        DebugUtils.log("CHECK : lastVisibleItemPosition=" + lastVisibleItemPosition);
        DebugUtils.log("CHECK : totalItemCount=" + totalItemCount);
        try {
            if ((lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
                currentPage++;
                onLoadMore(currentPage, totalItemCount);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {

        }

    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

}