package com.j1j2.jposmvvm.common.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by alienzxh on 16-9-19.
 */
public class DelayChangeEditText extends EditText {
    private String TAG = "DelayChangeEditText";
    private static final int MSGCODE = 0x12121212;

    private mTextWatcher textWatcher = new mTextWatcher();
    private String text;

    private int msgCount = 0;

    public DelayChangeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        this.addTextChangedListener(textWatcher);
    }

    private onTextChangerListener listener = null;

    public void setOnTextChangerListener(onTextChangerListener listener) {
        this.listener = listener;
    }

    public interface onTextChangerListener {
        public void onTextChanger(String text);
    }

    private class mTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            text = s.toString();
            Log.e(TAG, "text = " + text);

            msgCount++;

            Message msg = new Message();
            msg.what = MSGCODE;
            mHandler.sendMessageDelayed(msg, 800);
        }

    }

    /**
     * 防止调用setText时重新触发TextWatcher事件
     *
     * @param text
     */
    public void MySetText(String text) {
        removeTextChangedListener(textWatcher);
        setText(text);
        addTextChangedListener(textWatcher);
    }

    Handler mDelayedHandler = new Handler();

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == MSGCODE) {
                if (msgCount == 1) {
                    if (listener != null) {
                        listener.onTextChanger(text);
                    }
                    msgCount = 0;
                } else {
                    msgCount--;
                }

            }
        }
    };
}
