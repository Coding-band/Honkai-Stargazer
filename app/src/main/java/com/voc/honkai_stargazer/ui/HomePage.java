/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HomeAdapter;
import com.voc.honkai_stargazer.util.ThemeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomePage {

    Context context;
    Activity activity;
    View rootView;

    //https://www.jianshu.com/p/fecd87ec655f

    RecyclerView homeRecycleView;
    HomeAdapter homeAdapter;
    SharedPreferences sharedPreferences;

    ArrayList<Integer> homeList = new ArrayList<>();

    public HomePage(Context context, Activity activity, View rootView) {
        this.context = context;
        this.activity = activity;
        this.rootView = rootView;
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);

        homeRecycleView = rootView.findViewById(R.id.homeListView);

        homeList.add(HomeAdapter.TYPE_DAILYMEMO_STAMINA);
        homeList.add(HomeAdapter.TYPE_DAILYMEMO_ASSIGNMENT);
        homeList.add(HomeAdapter.TYPE_DAILYMEMO_ECHO_OF_WAR);
        //homeList.add(HomeAdapter.TYPE_DAILYMEMO_AUTO_CHECK_IN);
        homeList.add(HomeAdapter.TYPE_FAVOURITE);
        //homeList.add(HomeAdapter.TYPE_CALCULATOR);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        homeRecycleView.setLayoutManager(staggeredGridLayoutManager);
        homeAdapter = new HomeAdapter(context,activity, sharedPreferences);
        homeAdapter.filterList(homeList);
        homeRecycleView.setAdapter(homeAdapter);

    }

    public void homeEditor(){
        final Dialog dialog = new Dialog(context,R.style.PageDialogStyle_P);
        View view = View.inflate(context, R.layout.fragment_home_editor, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ThemeUtil themeUtil = new ThemeUtil(context,activity);
        themeUtil.navigationSetup(dialogWindow);
        themeUtil.themeTint(
                view.findViewById(R.id.rootView_home_editor)
        );

        RecyclerView recyclerView = view.findViewById(R.id.homeListView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        HomeAdapter adapter = new HomeAdapter(context,activity, sharedPreferences);
        adapter.filterList(homeList);
        recyclerView.setAdapter(adapter);
        homeList = itemTouchHelper(view, recyclerView, homeList, adapter);

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        if (!dialog.isShowing() && dialog != null){
            dialog.show();
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                homeAdapter.filterList(homeList);
            }
        });
    }

    //https://www.cnblogs.com/Fndroid/p/5657342.html
    //https://stackoverflow.com/questions/47192381/how-do-i-perform-a-drag-and-delete-item-functionality-in-a-recyclerview
    /*
    接着就是重写接口Callback的方法了，这里需要重写的有几个，分别是：
        isItemViewSwipeEnable : Item是否可以滑动
        isLongPressDragEnable ：Item是否可以长按
        getMovementFlags ： 获取移动标志
        onMove ： 当一个Item被另外的Item替代时回调，也就是数据集的内容顺序改变
        onMoved ： 当onMove返回true的时候回调
        onSwiped ： 当某个Item被滑动离开屏幕之后回调
        setSelectedChange ： 某个Item被长按选中会被回调，当某个被长按移动的Item被释放时也调用
     */
    private ArrayList<Integer> itemTouchHelper(View frameView, RecyclerView recyclerView, ArrayList<Integer> baseList, HomeAdapter mAdapter) {
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;
            private boolean isDeleteCard = false;

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                // 滑动的标记，这里允许左右滑动
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*
                这个方法会在某个Item被拖动和移动的时候回调，这里我们用来播放动画，当viewHolder不为空时为选中状态
                否则为释放状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                isDeleteCard = false;

                frameView.findViewById(R.id.home_delete).setVisibility(View.INVISIBLE);

                if (viewHolder != null) {
                    vh = viewHolder;
                    pickUpAnimation(viewHolder.itemView);
                    LinearLayout rootView = vh.itemView.findViewById(R.id.rootView);
                    if (rootView != null){
                        rootView.setForeground(context.getDrawable(R.drawable.bg_card_item_selected_kwang));
                    }

                } else {
                    if (vh != null) {
                        putDownAnimation(vh.itemView);
                        LinearLayout rootView = vh.itemView.findViewById(R.id.rootView);
                        if (rootView != null){
                            rootView.setForeground(null);
                        }
                        if (isViewOverlapping(vh.itemView, frameView.findViewById(R.id.home_delete))) {
                            frameView.findViewById(R.id.home_delete).setVisibility(View.VISIBLE);
                            isDeleteCard = true;
                            // 将数据集中的数据移除
                            Animation ani = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                            ani.setDuration(250);
                            ani.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    vh.itemView.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            vh.itemView.startAnimation(ani);
                            baseList.remove(vh.getAdapterPosition());
                            System.out.println(baseList.size());
                            // 刷新列表
                            mAdapter.notifyItemRemoved(vh.getAdapterPosition());
                        }

                    }
                }
            }

            private boolean isViewOverlapping(View firstView, View secondView) {
                int[] firstPosition = new int[2];
                int[] secondPosition = new int[2];

                firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                firstView.getLocationOnScreen(firstPosition);
                secondView.getLocationOnScreen(secondPosition);
                int r = firstView.getMeasuredHeight() + firstPosition[1];
                int l = secondPosition[1];
                return r >= l && (r != 0 && l != 0);
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // 移动时更改列表中对应的位置并返回true
                if(!isDeleteCard){
                    Collections.swap(baseList, viewHolder.getAdapterPosition(), target
                            .getAdapterPosition());
                }
                return true;
            }

            /*
                当onMove返回true时调用
             */
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                baseList.remove(viewHolder.getAdapterPosition());
                // 刷新列表
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        return mAdapter.getList();
    }

    private void pickUpAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 1f, 10f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }
    private void putDownAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 10f, 1f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }


}
