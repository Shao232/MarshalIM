
import android.content.Context

object SizeUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun  dip2px(context : Context?,  dpValue:Float):Int {
        val scale:Float = context?.resources?.displayMetrics?.density?:0f
        return  ((dpValue * scale + 0.5f).toInt())
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context:Context?, pxValue:Float):Int {
        val scale: Float = context?.resources?.displayMetrics?.density?:0f
        return ((pxValue / scale + 0.5f).toInt())
    }

}