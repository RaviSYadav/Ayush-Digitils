package com.payment.aeps.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.payment.aeps.R;

public class ModuleUtil {
    public static boolean isAllValueAvailable(String userId, String appToken, Context context) {
        boolean flag = true;
        if (userId == null || userId.length() == 0) {
            flag = false;
            Toast.makeText(context, "userId is not correct", Toast.LENGTH_SHORT).show();
        }

        if (appToken == null || appToken.length() == 0) {
            flag = false;
            Toast.makeText(context, "appToken is not correct", Toast.LENGTH_SHORT).show();
        }

        return flag;
    }


    public static void loadImage(final Context context, ImageView imageView, String url) {
        if (context == null || ((Activity) context).isDestroyed()) return;
        ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(1000);
                fadeAnim.start();
            }
        };

        RequestOptions options = new RequestOptions()
                .placeholder(context.getResources().getDrawable(R.drawable.ic_loader))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .timeout(60000)
                .error(context.getResources().getDrawable(R.drawable.ic_loader));

        Glide.with(context) //passing context
                .load(url) //passing your url to load image.
                .transition(GenericTransitionOptions.with(animationObject))
                .apply(options)
                .into(imageView);
    }

    public static boolean isNotNull(String s) {
        boolean flag = true;
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("null")) {
            flag = false;
        }
        return flag;
    }

    public static String capitalise(String status) {
        StringBuilder sb = new StringBuilder(status);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static boolean isOnline(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(context.CONNECTIVITY_SERVICE);
            isConnected = cm.getActiveNetworkInfo().isConnected();
        } catch (Exception ex) {
            isConnected = false;
        }
        return isConnected;
    }

    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException var3) {
            return false;
        }
    }

    public static String getPreferredPackage(Context context, String deviceId) {
        String data = "";

        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
            String temp = resolveInfo.activityInfo.packageName;
            if (!temp.equalsIgnoreCase("android") && !temp.equalsIgnoreCase(deviceId)) {
                data = temp;
            } else {
                data = "";
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return data;
    }
}
