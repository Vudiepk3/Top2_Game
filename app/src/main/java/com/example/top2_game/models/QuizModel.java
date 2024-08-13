package com.example.top2_game.models;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {
    private String id; // ID của bài kiểm tra
    private String title; // Tiêu đề    của bài kiểm tra
    private String subtitle; // Phụ đề của bài kiểm tra
    private String time; // Thời gian làm bài của bài kiểm tra (dưới dạng chuỗi)
    private List<QuestionModel> questionList; // Danh sách các câu hỏi trong bài kiểm tra

    // Constructor để tạo mới một đối tượng QuizModel
    public QuizModel(String id, String title, String subtitle, String time, List<QuestionModel> questionList) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.questionList = questionList;
    }

    // Constructor mặc định không tham số
    public QuizModel() {
        this("", "", "", "", new ArrayList<>());
    }

    // Phương thức getter cho thuộc tính id
    public String getId() {
        return id;
    }

    // Phương thức setter cho thuộc tính id
    public void setId(String id) {
        this.id = id;
    }

    // Phương thức getter cho thuộc tính title
    public String getTitle() {
        return title;
    }

    // Phương thức setter cho thuộc tính title
    public void setTitle(String title) {
        this.title = title;
    }

    // Phương thức getter cho thuộc tính subtitle
    public String getSubtitle() {
        return subtitle;
    }

    // Phương thức setter cho thuộc tính subtitle
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    // Phương thức getter cho thuộc tính time
    public String getTime() {
        return time;
    }

    // Phương thức setter cho thuộc tính time
    public void setTime(String time) {
        this.time = time;
    }

    // Phương thức getter cho thuộc tính questionList
    public List<QuestionModel> getQuestionList() {
        return questionList;
    }

    // Phương thức setter cho thuộc tính questionList
    public void setQuestionList(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }
}





