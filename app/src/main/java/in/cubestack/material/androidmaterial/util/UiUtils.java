package in.cubestack.material.androidmaterial.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cubestack on 9/8/2015.
 */
public class UiUtils {

    public static final String ALL = "ALL";

    public static final List<String> ALPHABETS = new ArrayList<String>();

    static {
        ALPHABETS.add("ALL");
        for(char chars ='A'; chars <= 'Z'; chars ++) {
            ALPHABETS.add(((Character) chars).toString());
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dipToPx(int dip) {
        return (int) (dip * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int spToPx(int sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().density);
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context ctx, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ctx.getResources().getDrawable(resourceId, ctx.getTheme());
        } else {
            return ctx.getResources().getDrawable(resourceId);
        }
    }
}
