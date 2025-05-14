package com.marshal.android.home

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.marshal.android.home.adapter.WanHomeAdapter
import com.marshal.android.pojo.BannerBean
import com.marshal.android.pojo.WenDaBean
import com.marshal.android.webview.WanAndroidWebViewActivity
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.module.wan.android.R
import com.marshal.module.wan.android.databinding.FragmentWanHomeBinding
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
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
    private var wanHomeAdapter:WanHomeAdapter? = WanHomeAdapter()


    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("首页")
        }

        wanHomeViewModel.loadBanners()
        wanHomeViewModel.loadWenDa()
        wanHomeViewModel.bannersMutableLiveData.observe(this) {
            bannerList = it as ArrayList<BannerBean>
            binding?.bannerHomeWan?.setDatas(bannerList)
        }
        wanHomeViewModel.wendaMutableLiveData.observe(this){
            wanHomeAdapter?.addListAll(it as ArrayList<WenDaBean>)
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

        binding?.smartRefreshLayoutHomeWan?.setRefreshHeader(ClassicsHeader(mContext))
        binding?.smartRefreshLayoutHomeWan?.setRefreshFooter(ClassicsFooter(mContext))
        binding?.smartRefreshLayoutHomeWan?.setOnRefreshListener {
            it.finishRefresh(2000)
        }

        binding?.smartRefreshLayoutHomeWan?.setOnLoadMoreListener {
            it.finishLoadMore(2000)
        }

        binding?.recycleViewHomeWan?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false)
        binding?.recycleViewHomeWan?.adapter = wanHomeAdapter
        wanHomeAdapter?.setAdapterItemOnClickListener(object : AdapterItemOnClickListener<WenDaBean>{
            override fun onClick(view: View, bean: WenDaBean) {
                super.onClick(view, bean)
                val intent = Intent(mContext, WanAndroidWebViewActivity::class.java)
                intent.putExtra("web_data_key",bean.desc)
                startActivity(intent)
            }
        })


    }



}