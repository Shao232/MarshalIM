package com.marshal.android

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.module.wan.android.R
import com.marshal.module.wan.android.databinding.FragmentWanHomeBinding
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class WanHomeFragment : BaseViewFragment<FragmentWanHomeBinding>() {
    override fun getResLayoutId(): Int = R.layout.fragment_wan_home

    override fun getResLayoutBinding(): View? {
       binding = FragmentWanHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun hasToolbar(): Boolean = true

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("首页")
        }

        val mData = listOf<String>(
            "https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
            "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"
        )
        binding?.bannerHomeWan?.setAdapter(object : BannerImageAdapter<String>(mData) {
            override fun onBindView(
                holder: BannerImageHolder?, data: String?, position: Int, size: Int
            ) {
                if (holder != null) {
                    Glide.with(holder.itemView).load(data)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into((holder.imageView ?: this@WanHomeFragment) as ImageView)
                }
            }
        })?.addBannerLifecycleObserver(this)?.indicator = CircleIndicator(mContext)
    }

}