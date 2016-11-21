package com.nbhero.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhenglingzhong on 2016/10/24.
 */

public class tool {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Bitmap compressImage(String path) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, opts);
        opts.inJustDecodeBounds = false;
        int w = opts.outWidth;
        int h = opts.outHeight;
        float standardW = 480f;
        float standardH = 800f;
        int zoomRatio = 1;
        if (w > h && w > standardW) {
            zoomRatio = (int) (w / standardW);
        } else if (w < h && h > standardH) {
            zoomRatio = (int) (h / standardH);
        }
        if (zoomRatio <= 0)
            zoomRatio = 1;
        opts.inSampleSize = zoomRatio;
        bmp = BitmapFactory.decodeFile(path, opts);
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

        return bitmap;
    }


    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
}
