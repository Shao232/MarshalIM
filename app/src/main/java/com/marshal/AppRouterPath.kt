package com.marshal

/**
 * 界面路径
 */
object AppRouterPath {

    private const val APP_PATH = "/home"

    const val APP_LOGIN_PAGE = "${APP_PATH}/login"
    //聊天页面
    const val CHAT_PATH = "/chat/page"
    //连接蓝牙页
    const val MINE_TO_BLUE_TOOTH = "/mine/bluetooth"
    //连接upd页
    const val MINE_TO_CONNECT_UDP = "/mine/connectUdp"
    //开大题库
    const val OPEN_QUESTION_BANK = "/open/question/bank"
    //数据库页
    const val ROOM_DATA_PAGE = "/room/data/page"
    //设置页
    const val MINE_SETTING_PAGE = "/mine/setting/page"
    //学习协程页面
    const val HOME_COROUTINES="/home/coroutines"
    //保存数据
    const val STORE_DATA_PAGE = "/store/data"
    //关于app
    const val ABOUT_APP_PAGE = "/about/app"
    //金刚页
    const val APP_FUNCTION_PAGE = "${APP_PATH}/function"
    //ai聊天页
    const val APP_AI_CHAT = "/ai/xinghuo/chat"
    //自定义view界面
    const val APP_CUSTOM_VIEW_PAGE = "/custom/view/page"
    //添加日程
    const val APP_ADD_EVENT_CALENDAR = "/add/event/calendar"
    const val APP_ADD_CALENDAR_SCHEDULE = "/add/calendar/schedule"

}