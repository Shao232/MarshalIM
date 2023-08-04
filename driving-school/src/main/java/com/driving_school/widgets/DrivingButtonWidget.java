package com.driving_school.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.driving_school.R;

public class DrivingButtonWidget extends LinearLayout {

    private View rootView;
    private LinearLayout lvnTimeSubject;
    private LinearLayout lvnCollectionSubject;
    private LinearLayout lvnTestCorrectSubject;
    private LinearLayout lvnTestErrorSubject;
    private TextView tvQuestionCount;
    private TextView tvSubjectTimeDriving;
    private ImageView ivCollectionSubject;
    private TextView tvYesSubject;
    private TextView tvNoSubject;

//    private boolean collectionVisibility = false;
//    private boolean questionCountVisibility = false;


    public DrivingButtonWidget(Context context) {
        super(context);
        initView(context);
    }

    public DrivingButtonWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DrivingButtonWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_driving_button_widget, this, true);
        lvnTimeSubject = findViewById(R.id.lvn_time_subject);
        lvnCollectionSubject = findViewById(R.id.lvn_collection_subject);
        lvnTestCorrectSubject = findViewById(R.id.lvn_test_correct_subject);
        lvnTestErrorSubject = findViewById(R.id.lvn_test_error_subject);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        tvSubjectTimeDriving = findViewById(R.id.tv_subject_time_driving);
        ivCollectionSubject = findViewById(R.id.iv_collection_subject);
        tvYesSubject = findViewById(R.id.tv_yes_subject);
        tvNoSubject = findViewById(R.id.tv_no_subject);


    }


    public void setTimeSubjectVisibility(boolean visibility) {
        if (lvnTimeSubject != null) {
            lvnTimeSubject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 展示收藏
     *
     * @param visibility
     */
    public void setCollectionVisibility(boolean visibility) {
        if(lvnCollectionSubject!=null){
            lvnCollectionSubject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setCollectionStatus(boolean isCollection){
        if(ivCollectionSubject!=null) {
            if (isCollection) {
                ivCollectionSubject.setImageResource(R.drawable.collectionsed_img);
            } else {
                ivCollectionSubject.setImageResource(R.drawable.my_collections_img);
            }
        }
    }

    public void setTestCorrectVisibility(boolean visibility) {
        if (lvnTestCorrectSubject != null) {
            lvnTestCorrectSubject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setTestErrorVisibility(boolean visibility) {
        if (lvnTestErrorSubject != null) {
            lvnTestErrorSubject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setQuestionCountVisibility(boolean visibility) {
        if(tvQuestionCount!=null){
            tvQuestionCount.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setQuestionCountContent(String content){
        if(tvQuestionCount!=null){
            tvQuestionCount.setText(content);
        }
    }


}
