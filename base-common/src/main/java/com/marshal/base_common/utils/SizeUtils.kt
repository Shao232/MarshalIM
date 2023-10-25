import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


object SizeUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        val scale: Float = context?.resources?.displayMetrics?.density ?: 0f
        return ((dpValue * scale + 0.5f).toInt())
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context?, pxValue: Float): Int {
        val scale: Float = context?.resources?.displayMetrics?.density ?: 0f
        return ((pxValue / scale + 0.5f).toInt())
    }

    fun getScreenHeight(context: Context?): Int {
        val displayMetrics = DisplayMetrics()
        (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay?.getMetrics(
            displayMetrics
        )
        return displayMetrics.heightPixels
    }


}