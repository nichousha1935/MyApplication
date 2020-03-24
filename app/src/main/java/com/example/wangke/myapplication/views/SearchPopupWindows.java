package com.example.wangke.myapplication.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangke.myapplication.R;
import com.example.wangke.myapplication.utils.DeviceUtils;
import com.example.wangke.myapplication.utils.ToastUtil;


public class SearchPopupWindows extends PopupWindow {
    private Context context;
    private LayoutInflater inflater;
    private ClickListener clickListener;
    private EditText et_search;

    public SearchPopupWindows(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        init();
    }

    public void init() {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_search_pop, null);
        setContentView(view);
        setWidth(DeviceUtils.getScreenWidth(context));
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        et_search = view.findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
//                    ((InputMethodManager) MyApplicant.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(SearchActivity.this
//                                            .getCurrentFocus().getWindowToken(),
//                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (et_search.getText().toString().isEmpty()) {
                        ToastUtil.showToast(context, "搜索栏不能为空！");
                    } else {
                        //搜索
                       clickListener.click(et_search.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }


    public interface ClickListener {
        void click(String s);
    }
}
