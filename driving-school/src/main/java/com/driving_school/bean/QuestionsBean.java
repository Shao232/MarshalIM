package com.driving_school.bean;


import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by 11470 on 2017/10/17.
 */

/**
 {"id":"1",
 "question":"这个标志是何含义？",
 "answer":"4",
 "item1":"小型车车道",
 "item2":"小型车专用车道",
 "item3":"多乘员车辆专用车道",
 "item4":"机动车车道",
 "explains":"此为机动车车道，比多乘员车辆专用车道少俩人。",
 "url":"https:\/\/images.juheapi.com\/jztk\/c1c2subject1\/1.jpg"}


 */

public class QuestionsBean implements Serializable{
    private String id;
    private String question;
    private String answer;//答案
    private String item1;//答案 1
    private String item2;//答案 2
    private String item3;//答案 3
    private String item4;//答案 4
    private String explains;//题目
    private String url;

    //开始动画
    private boolean startAnim = false;

    //是否回答正确
    private boolean answerCorrect;
    private int answerFrequency = 0;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setAnswerFrequency(int answerFrequency) {
        this.answerFrequency = answerFrequency;
    }

    public int getAnswerFrequency() {
        return answerFrequency;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public boolean isAnswerCorrect() {
        return answerCorrect;
    }

    public void setStartAnim(boolean startAnim) {
        this.startAnim = startAnim;
    }

    public boolean isStartAnim() {
        return startAnim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public QuestionsBean() {
    }

}
