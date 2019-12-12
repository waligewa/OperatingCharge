package com.example.operatingcharge.util;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.operatingcharge.R;

/**
 *
 * 防抖动点击
 * Author : jiangshicheng
 * Date   : 2019/7/7
 * Time   : 19:50
 * Desc   : 给Button设置名为last_click_time的tag标签并存储上一次点击的时间戳，在一定的时间间隔内只取第一次点击事件。
 *          如此， Button会随着界面的销毁而被释放，因而它的名为last_click_time的tag标签存储所占用的内存也会被释放回收。
 */
public class AntiShakeUtils {

    private final static long INTERNAL_TIME = 1000;
    private final static long INTERNAL_LONG_TIME = 10000;
    /**
     * Whether this click event is invalid.
     *
     * @param target target view
     * @return true, invalid click event.
     * @see #isInvalidClick(View, long)
     */
    public static boolean isInvalidClick(@NonNull View target) {
        return isInvalidClick(target, INTERNAL_TIME);
    }

    public static boolean isLongInvalidClick(@NonNull View target) {
        return isLongInvalidClick(target, INTERNAL_LONG_TIME);
    }

    /**
     * Whether this click event is invalid.
     *
     * @param target       target view
     * @param internalTime the internal time. The unit is millisecond.
     * @return true, invalid click event.
     */
    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long internalTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;                                           // 戳
        Object o = target.getTag(R.id.last_click_time);
        if (o == null) {
            target.setTag(R.id.last_click_time, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < internalTime;
        if (!isInvalid) target.setTag(R.id.last_click_time, curTimeStamp);
        return isInvalid;
    }

    public static boolean isLongInvalidClick(@NonNull View target, @IntRange(from = 0) long time) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;                                           // 戳
        Object o = target.getTag(R.id.last_click_time);
        if (o == null) {
            target.setTag(R.id.last_click_time, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < time;
        if (!isInvalid) target.setTag(R.id.last_click_time, curTimeStamp);
        return isInvalid;
    }
}