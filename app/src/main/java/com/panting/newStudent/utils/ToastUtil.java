package com.panting.newStudent.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.panting.newStudent.base.BaseApplication;


/**
 * Toast 工具类
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 保证是单例的toast.再上一个toast没有显示完的情况下下一个toast不会显示.
     * @param text
     */
    public static void showToast(String text){
        if (toast == null){
            toast = Toast.makeText(BaseApplication.getContext(),"", Toast.LENGTH_SHORT);
            //设置toast显示在中间位置
            toast.setGravity(Gravity.CENTER,0,0);
        }
        toast.setText(text);
        toast.show();
    }

}
