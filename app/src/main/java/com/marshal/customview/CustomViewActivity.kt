package com.marshal.customview

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityCustomViewBinding


@Route(path = AppRouterPath.APP_CUSTOM_VIEW_PAGE)
class CustomViewActivity : BaseViewActivity<ActivityCustomViewBinding>() {

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("自定义View")
        }

        binding?.btnStartAnim?.setOnClickListener {
//            val animationUtils = AnimationUtils.loadAnimation(this, R.anim.scale_01)
//            binding?.tvAnimView?.startAnimation(animationUtils)

        }

        /*
           RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200, 200);
        lp.setMargins(50, 50, 0, 0);
        dragRectView.setLayoutParams(lp);
        dragRectView.setClickable(true);
        dragRectView.setSelected(true);
        dragRectView.setMyTouchListener(new DragRectView.OnMyTouchListener() {
            @Override
            public void onClick() {
                dragRectView.setSelected(true);
            }
        });

        drawLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragRectView.setSelected(false);
            }
        });
         */
//        val lp = RelativeLayout.LayoutParams(500,500)
//        lp.setMargins(50, 50, 0, 0);
//        binding?.dragRectView?.layoutParams = lp
//        binding?.dragRectView?.setClickable(true);
//        binding?.dragRectView?.setSelected(true);
//        binding?.dragRectView?.setMyTouchListener { binding?.dragRectView?.isSelected = true; };
//
//        binding?.dragRectView?.setOnClickListener{
//            binding?.dragRectView?.setSelected(false);
//        }


    }
}