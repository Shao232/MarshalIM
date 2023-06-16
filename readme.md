## MarshalIm
>项目用于练手开发，主要想做IM通讯聊天，音视频直播


## 主要业务功能
>1.聊天页面
>2.蓝牙和udp
>3.通过读取excel文件，查看上海开放大学的本科第一学期的复习资料


## 项目用到的技术点
>1.使用组件化的方式将不同的业务进行拆分 这一步使用ARouter
>2.使用viewbinding简化绑定view的方式
>3.数据持久化缓存使用MMKV
>4.学习下Room对Sqlite数据库的操作


## 遇到的问题
>1.ScrollView滚动布局嵌套RecyclerView布局时，内容不确定，recyclerview布局未设置固定大小，导致卡顿
>2.由于 Android P(版本27以上) 限制了明文流量的网络请求，非加密的流量请求都会被系统禁止掉。如果当前应用的请求是 htttp 请求，
   而非 https ,这样就会导系统禁止当前应用进行该请求。 也就是Android9.0以上都要https，不能http了，不然拒绝访问。
   解决方法 在AndroidManifest.xml的文件的application节点中增加： android:usesCleartextTraffic="true"