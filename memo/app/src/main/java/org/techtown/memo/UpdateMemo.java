package org.techtown.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateMemo extends AppCompatActivity {

    EditText edtText, edtTitle, edtAddress;

    SQLiteHelper dbHelper;
    Memo memoList;
    String i, title, text, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtTitle = findViewById(R.id.edtTitle);
        edtText = findViewById(R.id.edtMemo);
        edtAddress = findViewById(R.id.edtAddress);

       // String text = memoList.getMaintext();
        //edtText.setText(Memo.);
        Intent intent = getIntent();
        i = intent.getStringExtra("i");
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("maintext");
        address = intent.getStringExtra("address");

        edtAddress.setText(address);
        edtText.setText(text);
        edtTitle.setText(title);
        //Toast.makeText(UpdateMemo.this, text, Toast.LENGTH_SHORT).show();
        //String strMain = data.getStringExtra("main");




        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String maintext = edtText.getText().toString();
                String title = edtTitle.getText().toString();
                String maintext = edtText.getText().toString();
                String address = edtAddress.getText().toString();

                if(maintext.length() > 0){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                    String work = "수정";
                    String substr = sdf.format(date);
                    String seq = i;
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("address", address);
                    intent.putExtra("main", maintext);
                    intent.putExtra("sub", substr);
                    intent.putExtra("work", work);
                    intent.putExtra("seq", seq);
                    setResult(0,intent);

                    //finish();

                    Toast.makeText(UpdateMemo.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();

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