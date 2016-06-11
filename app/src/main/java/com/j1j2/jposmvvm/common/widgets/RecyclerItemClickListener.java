/*
 * Copyright(c) 2015 Marshal Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.j1j2.jposmvvm.common.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position, int id);

//        void onItemLongClick(RecyclerView parent, View view, int position, int id);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(final RecyclerView view, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                View childView = view.findChildViewUnder(e.getX(), e.getY());
//                // 有item被选则且监听器不为空触发长按事件
//                if (childView != null && mListener != null) {
//                    mListener.onItemLongClick(view, childView, view.getChildAdapterPosition(childView), childView.getId());
//                }
//            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(view, childView, view.getChildAdapterPosition(childView), childView.getId());
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
