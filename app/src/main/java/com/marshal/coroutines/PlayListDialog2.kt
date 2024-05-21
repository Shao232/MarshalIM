import com.marshal.coroutines.*;

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.marshal.R


class PlayListDialog2(context: Context) : BottomSheetDialog(context, 0){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog2)
        window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            //setWindowAnimations(R.style.BottomSheetStyle)
            attributes?.gravity = Gravity.BOTTOM
        }



    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            dismiss();
        }
        return super.dispatchTouchEvent(ev)
    }


}