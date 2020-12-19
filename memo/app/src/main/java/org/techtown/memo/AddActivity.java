package org.techtown.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText edtText;
    EditText edtTitle;
    SQLiteHelper dbHelper;
    Memo memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtText = findViewById(R.id.edtMemo);
        edtTitle = findViewById(R.id.edtTitle);

        findViewById(R.id.btnmap).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplication(), MapsFragment.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maintext = edtText.getText().toString();
                String title = edtTitle.getText().toString();

                //String str2 = memoList.getMaintext();
                if(maintext.length() > 0){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                    String work = "삽입";
                    String substr = sdf.format(date);

                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("main", maintext);
                    intent.putExtra("sub", substr);
                    intent.putExtra("work", work);

                    setResult(0,intent);

                    finish();

                    Toast.makeText(AddActivity.this, title + " 가 작성되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);

            }
        });

    }
}