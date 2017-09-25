package br.com.aio.utils;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

/**
 * Created by elton on 25/09/2017.
 */

public class ViewUtils {

    public static Drawable drawableSetTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static Drawable scaleDrawable(Drawable drawable, int width, int height) {

        int wi = drawable.getIntrinsicWidth();
        int hi = drawable.getIntrinsicHeight();
        int dimDiff = Math.abs(wi - width) - Math.abs(hi - height);
        float scale = (dimDiff > 0) ? width / (float)wi : height /
                (float)hi;
        Rect bounds = new Rect(0, 0, (int)(scale * wi), (int)(scale * hi));
        drawable.setBounds(bounds);
        return drawable;
    }

    public static String getTextSpam(Drawable drawable, String texto, int[] state, int alinhamento, int spanned) {
        spanned = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        //alinhamento = ImageSpan.ALIGN_BOTTOM;
        SpannableStringBuilder textspan = new SpannableStringBuilder(texto);
        drawable.setState(state);  // match the background state
        textspan.setSpan(new ImageSpan(drawable, alinhamento), 0, 1, spanned);
        return textspan.toString();
    }
}
