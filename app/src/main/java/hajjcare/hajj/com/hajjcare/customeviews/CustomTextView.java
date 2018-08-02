package hajjcare.hajj.com.hajjcare.customeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import hajjcare.hajj.com.hajjcare.R;


/**
 * Created by eslamm on 02/02/2018.
 */
@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{
                android.R.attr.textSize, // 0
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int textSize = ta.getDimensionPixelSize(0, 0);
        if (textSize == 0) {
            textSize = (int) getResources().getDimensionPixelSize(R.dimen.def_edit_text_size);
        }
        float scale = 1f;
        setTextSize((textSize / context.getResources().getDisplayMetrics().density) * scale);
        ta.recycle();
    }
}
