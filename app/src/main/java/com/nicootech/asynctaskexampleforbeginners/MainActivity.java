package com.nicootech.asynctaskexampleforbeginners;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyTask myTask = new MyTask(MainActivity.this,textView,button);
                myTask.execute();
                button.setEnabled(false);


            }
        });
    }

    public  class MyTask extends AsyncTask<String,Integer,String>
    {
        Context context;
        TextView textView;
        Button button;

        ProgressDialog progressDialog;

        MyTask(Context context, TextView textView, Button button)
        {
            this.context=context;
            this.textView=textView;
            this.button=button;
        }


        @Override
        protected String doInBackground(String... strings) {

            int i = 0;
            synchronized (this)
            {


                while (i<10)
                {

                    try {
                        wait(500);
                        i++;
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            return "Download Complete...";
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Downloda in Progress...");
            progressDialog.setMax(10);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
            button.setEnabled(true);
            progressDialog.hide();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            progressDialog.setProgress(progress);
            textView.setText("Download  In Progress....");
        }
    }
}
