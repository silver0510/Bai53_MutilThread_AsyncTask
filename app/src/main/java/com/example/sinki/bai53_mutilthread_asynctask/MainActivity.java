package com.example.sinki.bai53_mutilthread_asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText txtSoButton;
    Button btnVe;
    ProgressBar progressBar;
    LinearLayout layoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvents();
    }

    private void addEvents() {
        btnVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyVeButtonRealTime();
            }
        });
    }

    private void xuLyVeButtonRealTime() {
        int n = Integer.parseInt(txtSoButton.getText().toString());
        ButtonTask task = new ButtonTask();
        //đây là lệnh gọi cho tiến trình chạy
        //thông số truyền vào sẽ được đưa tới hàm doInBackground
        //thông số truyền vào là parameter list nên truyên bao nhiu cũng được
        //ví dụ task.execute(n1,n2,n3,n4);
        //tuy nhiên các thông số truyền này là 1 kiểu như dưới class đã khai báo, ở đây là kiểu Integer
        task.execute(n);
    }

    private void addControl() {
        txtSoButton = (EditText) findViewById(R.id.txtSoButton);
        btnVe = (Button) findViewById(R.id.btnVe);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);

    }

    private class ButtonTask extends AsyncTask<Integer,Integer,Void>
    {
        //khi tiến trình bắt đầu vào chạy
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layoutButton.removeAllViews();
            progressBar.setProgress(0);
        }
        //khi kết thúc tiến trình
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setProgress(100);
        }
        //hàm cập nhập giao diện
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int percent = values[0];
            int value = values[1];
            progressBar.setProgress(percent);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            Button btn = new Button(MainActivity.this);
            btn.setLayoutParams(params);
            btn.setText(value+"");

            layoutButton.addView(btn);
        }
        //hàm chạy, nhất định k được cập nhập giao diện trong hàm này
        //chỉ xử lý dữ liệu ở đây
        @Override
        protected Void doInBackground(Integer... integers) {
            //biến kiểu "Integer..." đây là kiểu parameter list (overload method)
            //các biến đưa vào sẽ được đưa hết vào biến nào theo thứ
            //ví dụ như ở đây chỉ có 1 biến vào là integer nên nó lưu vào vị trí số 0
            int n = integers[0];
            Random random = new Random();
            for (int i = 0;i <n;i++)
            {
                int percent = i*100/n;
                int value = random.nextInt(500);
                //đẩy 2 biến này lên hàm onProgressUpdate
                //hàm onProgressUpdate nó cũng có biến vào là kiểu parameter list
                //nên khi đưa 2 biến này vào thì percent ở vị trí 0, value ở vị trí 1
                publishProgress(percent,value);
                SystemClock.sleep(100);
            }
            return null;
        }
    }
}
