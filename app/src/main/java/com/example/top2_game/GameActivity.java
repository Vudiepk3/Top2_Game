package com.example.top2_game;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.top2_game.models.QuestionModel;
import com.example.top2_game.R;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    // Danh sách câu hỏi và thời gian bài kiểm tra
    private static List<QuestionModel> questionModelList;
    private static String time;
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private int score = 0;

    private Button btn0, btn1, btn2, btn3, nextBtn, endBtn;
    private TextView questionIndicatorTextview, questionTextview, timerIndicatorTextview;
    private ProgressBar questionProgressIndicator;

    // Phương thức này sẽ được gọi từ bên ngoài để thiết lập danh sách các câu hỏi
    public static void setQuestionModelList(List<QuestionModel> questionList) {
        questionModelList = questionList;
    }

    // Phương thức này sẽ được gọi từ bên ngoài để thiết lập thời gian cho bài kiểm tra
    public static void setTime(String time) {
        GameActivity.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Khởi tạo các thành phần giao diện
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        nextBtn = findViewById(R.id.next_btn);
        endBtn = findViewById(R.id.end_btn);

        questionIndicatorTextview = findViewById(R.id.question_indicator_textview);
        questionTextview = findViewById(R.id.question_textview);
        timerIndicatorTextview = findViewById(R.id.timer_indicator_textview);
        questionProgressIndicator = findViewById(R.id.question_progress_indicator);

        // Thiết lập sự kiện lắng nghe cho các nút
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);

        loadQuestions(); // Tải câu hỏi
        startTimer(); // Bắt đầu đếm thời gian
    }

    // Phương thức này khởi động đồng hồ đếm thời gian
    private void startTimer() {
        long totalTimeInMillis = Integer.parseInt(time) * 60 * 1000L;
        new CountDownTimer(totalTimeInMillis, 1000L) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                timerIndicatorTextview.setText(format("%02d:%02d", minutes, remainingSeconds));
            }

            @Override
            public void onFinish() {
                finishQuiz();
            }
        }.start();
    }

    // Phương thức này tải câu hỏi tiếp theo
    @SuppressLint("SetTextI18n")
    private void loadQuestions() {
        selectedAnswer = "";
        if (currentQuestionIndex == questionModelList.size()) {
            finishQuiz(); // Kết thúc bài kiểm tra nếu đã trả lời hết câu hỏi
            return;
        }
        // Hiển thị câu hỏi và các tùy chọn
        questionIndicatorTextview.setText("Question " + (currentQuestionIndex + 1) + "/ " + questionModelList.size());
        questionProgressIndicator.setProgress((int) ((currentQuestionIndex / (float) questionModelList.size()) * 100));
        questionTextview.setText(questionModelList.get(currentQuestionIndex).getQuestion());
        List<String> options = questionModelList.get(currentQuestionIndex).getOptions();
        btn0.setText(options.get(0));
        btn1.setText(options.get(1));
        btn2.setText(options.get(2));
        btn3.setText(options.get(3));
    }

    @Override
    public void onClick(View view) {
        // Đặt màu nền cho tất cả các nút thành màu xám
        btn0.setBackgroundColor(getColor(R.color.gray));
        btn1.setBackgroundColor(getColor(R.color.gray));
        btn2.setBackgroundColor(getColor(R.color.gray));
        btn3.setBackgroundColor(getColor(R.color.gray));

        try {
            if (view.getId() == R.id.next_btn) {
                // Nếu nút "Next" được nhấn
                if (selectedAnswer.isEmpty()) {
                    // Kiểm tra xem người dùng đã chọn câu trả lời chưa
                    Toast.makeText(getApplicationContext(), "Làm Ơn Chọn Đáp Án Để Tiếp Tục", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedAnswer.equals(questionModelList.get(currentQuestionIndex).getCorrect())) {
                    // Nếu câu trả lời đúng, tăng điểm số
                    score++;
                    Log.i("Điểm của bài kiểm tra", valueOf(score));
                }
                currentQuestionIndex++; // Chuyển sang câu hỏi tiếp theo
                loadQuestions(); // Tải câu hỏi mới
            } else if (view.getId() == R.id.end_btn) {
                onExitButtonClick();
            } else {
                // Nếu một tùy chọn khác được chọn
                selectedAnswer = ((Button) view).getText().toString();
                view.setBackgroundColor(getColor(R.color.red)); // Đặt màu nền của nút đã chọn
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi Ứng Dụng", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức này hiển thị kết quả sau khi hoàn thành bài kiểm tra
    @SuppressLint("SetTextI18n")
    private void finishQuiz() {
        try {
            int totalQuestions = questionModelList.size();
            int percentage = (int) (((float) score / totalQuestions) * 100);

            // Tạo hộp thoại hiển thị kết quả
            View dialogView = getLayoutInflater().inflate(R.layout.item_score_dialog, null);
            ProgressBar scoreProgressIndicator = dialogView.findViewById(R.id.score_progress_indicator);
            TextView scoreProgressText = dialogView.findViewById(R.id.score_progress_text);
            TextView scoreTitle = dialogView.findViewById(R.id.score_title);
            TextView scoreSubtitle = dialogView.findViewById(R.id.score_subtitle);
            Button finishBtn = dialogView.findViewById(R.id.finish_btn);

            scoreProgressIndicator.setProgress(percentage);
            scoreProgressText.setText(percentage + " %");
            scoreTitle.setText("Chúc mừng bạn đã hoàn thành bài thi");
            scoreTitle.setTextColor(Color.RED);
            scoreSubtitle.setText(score + " trên " + totalQuestions + " chính xác");

            finishBtn.setOnClickListener(v -> finish()); // Đóng activity khi nút "Finish" được nhấn

            new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi Ứng Dụng", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức này hiển thị hộp thoại xác nhận khi người dùng muốn thoát khỏi bài kiểm tra
    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc chắn muốn thoát khỏi bài kiểm tra không?")
                .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Kết thúc Activity nếu người dùng chọn thoát
                        finishQuiz();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    // Phương thức này được gọi khi người dùng nhấn nút "Back" trên thiết bị
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Hiển thị hộp thoại xác nhận khi người dùng nhấn nút "Back" trên thiết bị
        showExitConfirmationDialog();
    }

    // Phương thức này được gọi khi người dùng nhấn nút "Thoát" trên giao diện
    public void onExitButtonClick() {
        // Hiển thị hộp thoại xác nhận khi người dùng nhấn nút "Thoát" trên giao diện
        showExitConfirmationDialog();
    }
}
