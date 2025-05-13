package com.marshal.android.home

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.marshal.android.pojo.BannerBean
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

    private val wanHomeViewModel: WanHomeViewModel by viewModels()
    private var bannerList:MutableList<BannerBean>? = mutableListOf()

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("首页")
        }


        wanHomeViewModel.loadBanners()
        wanHomeViewModel.bannersMutableLiveData.observe(this) {
            bannerList = it as ArrayList<BannerBean>
            binding?.bannerHomeWan?.setDatas(bannerList)
        }


        binding?.bannerHomeWan?.setAdapter(object : BannerImageAdapter<BannerBean>(bannerList) {
            override fun onBindView(
                holder: BannerImageHolder?, data: BannerBean?, position: Int, size: Int
            ) {
                if (holder != null) {
                    Glide.with(holder.itemView).load(data?.imagePath)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into((holder.imageView ?: this@WanHomeFragment) as ImageView)
                }
            }
        })?.addBannerLifecycleObserver(this)?.indicator = CircleIndicator(mContext)
    }

}