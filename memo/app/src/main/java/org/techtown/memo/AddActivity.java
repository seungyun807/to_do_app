package org.techtown.memo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText edtText;
    EditText edtTitle;
    EditText edtAddress;
    SQLiteHelper dbHelper;
    Memo memoList;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtText = findViewById(R.id.edtMemo);
        edtTitle = findViewById(R.id.edtTitle);
        edtAddress = findViewById(R.id.item_address);
        findViewById(R.id.btnmap).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AddActivity.this, MapsFragment.class);
                //intent.putExtra("insert", edtAddress.getText());
                startActivityForResult(intent, 0);

                Toast.makeText(AddActivity.this, edtAddress.getText() + " 넘", Toast.LENGTH_SHORT).show();

            }
        });



        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maintext = edtText.getText().toString();
                String title = edtTitle.getText().toString();
                String address2 = edtAddress.getText().toString();
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
                    intent.putExtra("address", address2);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        address = data.getStringExtra("address");
        //Intent intent2 = getIntent();
        //address = intent2.getStringExtra("address");
        edtAddress.setText(address);
        Toast.makeText(AddActivity.this, address + "가져옴.", Toast.LENGTH_SHORT).show();
    }


}