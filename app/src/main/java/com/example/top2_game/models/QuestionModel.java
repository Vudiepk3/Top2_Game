package com.example.top2_game.models;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    private String question;
    private List<String> options;
    private String correct;

    // Constructor có tham số
    public QuestionModel(String question, List<String> options, String correct) {
        this.question = question;
        this.options = options;
        this.correct = correct;
    }

    // Constructor mặc định
    public QuestionModel() {
        this("", new ArrayList<>(), "");
    }

    // Phương thức getter cho câu hỏi
    public String getQuestion() {
        return question;
    }

    // Phương thức setter cho câu hỏi
    public void setQuestion(String question) {
        this.question = question;
    }

    // Phương thức getter cho danh sách các tùy chọn
    public List<String> getOptions() {
        return options;
    }

    // Phương thức setter cho danh sách các tùy chọn
    public void setOptions(List<String> options) {
        this.options = options;
    }

    // Phương thức getter cho câu trả lời đúng
    public String getCorrect() {
        return correct;
    }

    // Phương thức setter cho câu trả lời đúng
    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
