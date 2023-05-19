package com.marshal.utils;

public interface RecordAudioCallBack {
    /**
     * 录制失败
     * @param msg 失败消息
     */
    void onRecordAudioErr(String msg);

    /**
     * 录制完成
     * @param path 存储路径
     */
    void onRecordComplete(String path);
}
