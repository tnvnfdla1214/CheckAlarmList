package com.example.memo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

enum ButtonsState{              // 버튼의 상태 정의
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback  {
    private ItemTouchHelperListener listener;
    private boolean swipeBack = false;
    private ButtonsState buttonsShowedState = ButtonsState.GONE;
    private static final float buttonWidth = 115;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currenrtItemViewHolder = null;
    public ItemTouchHelperCallback(ItemTouchHelperListener listener) { this.listener = listener; }
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int drag_flags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipe_flags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(drag_flags, swipe_flags);
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemSwipe(viewHolder.getAdapterPosition());
    }
    ////아이템을 터치하거나 스와이프 할 경우 불러오는 함수
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (buttonsShowedState != ButtonsState.GONE) {
                if (buttonsShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonsShowedState == ButtonsState.RIGHT_VISIBLE)dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if (buttonsShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currenrtItemViewHolder =viewHolder;
        drawButtons(c, currenrtItemViewHolder);
    }
    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){     // 수정, 확인 버튼을 그려주는 함수

        float buttonWidthWithOutPadding = buttonWidth - 10;
        float corners = 5;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        buttonInstance = null;

        ////왼쪽 버튼
        if(buttonsShowedState == ButtonsState.LEFT_VISIBLE){
            RectF leftButton = new RectF(itemView.getLeft() + 10, itemView.getTop() + 10, itemView.getLeft() + buttonWidthWithOutPadding, itemView.getBottom() - 10);
            p.setColor(Color.BLUE);
            c.drawRoundRect(leftButton, corners, corners, p);
            drawText("수정", c, leftButton, p);
            buttonInstance = leftButton;
        }
        ////오른쪽 버튼
        else if(buttonsShowedState == ButtonsState.RIGHT_VISIBLE) {
            RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithOutPadding, itemView.getTop() + 10, itemView.getRight() - 10, itemView.getBottom() - 10);
            p.setColor(Color.BLUE);
            c.drawRoundRect(rightButton, corners, corners, p);
            drawText("확인", c, rightButton, p);
            buttonInstance = rightButton;
        }
    }
    //버튼의 텍스트
    private void drawText(String text, Canvas c, RectF button, Paint p){
        float textSize = 25; p.setColor(Color.WHITE);
        p.setAntiAlias(true); p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth/2), button.centerY() + (textSize/2), p);
    }
    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if(swipeBack){
            swipeBack = false; return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if(swipeBack){
                    if(dX < -buttonWidth) {
                        buttonsShowedState = ButtonsState.RIGHT_VISIBLE;
                    }
                    else if(dX > buttonWidth) {
                        buttonsShowedState = ButtonsState.LEFT_VISIBLE;
                    }
                    if(buttonsShowedState != ButtonsState.GONE){
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView , final RecyclerView.ViewHolder viewHolder, final float dX, final float dY , final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView , final RecyclerView.ViewHolder viewHolder, final float dX, final float dY , final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ItemTouchHelperCallback.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

                setItemsClickable(recyclerView, true);
                swipeBack = false;
                if(listener != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())){
                    if(buttonsShowedState == ButtonsState.LEFT_VISIBLE){
                        listener.onLeftClick(viewHolder.getAdapterPosition(), viewHolder);
                    }

                    else if(buttonsShowedState == ButtonsState.RIGHT_VISIBLE){
                        listener.onRightClick(viewHolder.getAdapterPosition(), viewHolder);
                    }
                }
                buttonsShowedState = ButtonsState.GONE;
                currenrtItemViewHolder = null;
                return false;
            }
        });
    }
    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable){
        for(int i = 0; i < recyclerView.getChildCount(); i++){
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }
}