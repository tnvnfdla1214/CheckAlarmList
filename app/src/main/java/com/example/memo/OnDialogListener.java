package com.example.memo;

public  interface OnDialogListener {
    void onFinish(int position, MemoDatalist memoDatalist);
}
/*
알람(왼쪽)
activity_main.xml - Fragment1.java - MemoAdapter.java - ItemTouchHelperCallback.java - AddDataActivity.java - CustomDialog.java ┐
                                                                                                                                └───┐
메모(오른쪽)                                                                                                                         ├    ㅡ Main.java - main.xml
activity_memo.xml - Fragment2.java - PerMemoAdapter.java - ItemTouchHelperCallback2.java - AddDataActivity.java - CustomDialog.java ┘
 */
