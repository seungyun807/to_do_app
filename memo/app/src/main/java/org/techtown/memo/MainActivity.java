package org.techtown.memo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;



import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback{

    SQLiteHelper dbHelper;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    FloatingActionButton btnAdd;
    TextView textResult;
    List<Memo> memoList;
    int update;

   // BottomNavigationView bottomNavigationView;

    private BottomNavigationView mBottomNV;

    FragmentPage1 fragment1;
    FragmentPage2 fragment2;
    FragmentPage3 fragment3;

    DrawerLayout drawer;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


       // bottomNavigationView = findViewById(R.id.bottomNavigationView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment1 = new FragmentPage1();
        fragment2 = new FragmentPage2();
        fragment3 = new FragmentPage3();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        //i = new SQLiteHelper()
        dbHelper = new SQLiteHelper(MainActivity.this);
        memoList = dbHelper.selectALL();

        //getSupportFragmentManager().beginTransaction().replace(R.id.).commitAllowingStateLoss();

        //MapFragment mapFragment = new MapFragment();

        //getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, mapFragment).commit();


                    recyclerView = findViewById(R.id.recyclerview);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    recyclerAdapter = new RecyclerAdapter(memoList);
                    recyclerView.setAdapter(recyclerAdapter);
                    btnAdd = findViewById(R.id.btnAdd);

                    btnAdd.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            //새로운 메모 작성

                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    });
        mBottomNV = findViewById(R.id.bottom_navigation);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());


                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.tab1);







    }




    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu1) {
            Toast.makeText(this, "첫번째 메뉴 선택됨.", Toast.LENGTH_LONG).show();
            onFragmentSelected(0, null);
        } else if (id == R.id.menu2) {
            Toast.makeText(this, "두번째 메뉴 선택됨.", Toast.LENGTH_LONG).show();
            onFragmentSelected(1, null);
        } else if (id == R.id.menu3) {
            Toast.makeText(this, "세번째 메뉴 선택됨.", Toast.LENGTH_LONG).show();
            onFragmentSelected(2, null);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if (position == 0) {
            curFragment = fragment1;
            toolbar.setTitle("첫번째 화면");
        } else if (position == 1) {
            curFragment = fragment2;
            toolbar.setTitle("두번째 화면");
        } else if (position == 2) {
            curFragment = fragment3;
            toolbar.setTitle("세번째 화면");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }

    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == R.id.tab1) {
                fragment = new FragmentPage1();

            } else if (id == R.id.tab2){

                fragment = new FragmentPage2();
            }else if (id == R.id.tab3){
                fragment = new FragmentPage3();
            } else {
                //fragment = new MapsFragment();
            }

            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();


    }










    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        if(requestCode == 0){
            String strTitle = data.getStringExtra("title");
            String strMain = data.getStringExtra("main");
            String strAdr = data.getStringExtra("address"); // 여기서부터
            String strSub = data.getStringExtra("sub");
            String i = data.getStringExtra("work");
            String seq = data.getStringExtra("seq");

            Toast.makeText(MainActivity.this, strAdr + " 가 작성되었습니다.", Toast.LENGTH_SHORT).show();

            if (i.compareTo("삽입") == 0){
                Memo memo = new Memo(strTitle, strAdr, strMain,  strSub, 0);
                recyclerAdapter.addItem(memo);
                recyclerAdapter.notifyDataSetChanged();
                dbHelper.insertMemo(memo);

            } else {
                int pk = Integer.parseInt(seq);
                Memo memo = new Memo(pk, strTitle, strAdr, strMain, strSub, 0);
                //recyclerAdapter.addItem(memo);
                //recyclerAdapter.notifyDataSetChanged();
                dbHelper.updateMemo(memo);


            }


            memoList = dbHelper.selectALL(); // 목록 새로고침
            recyclerAdapter = new RecyclerAdapter(memoList);
            recyclerView.setAdapter(recyclerAdapter);;
    }


    }
    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        public List<Memo> listdata;

        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView id;
            private TextView title;
            private TextView subtext;
            private TextView maintext;
            private CheckBox todo;
            private TextView address;
            // int id2 = (int)maintext.getTag();
            // String id = Integer.toString(id2);


            public ItemViewHolder(@NonNull View itemView){
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            // 데이터 리스트로부터 아이템 데이터 참조.
                            int seq = (int)title.getTag();

                            String i = Integer.toString(seq);
                            String str = (String)maintext.getText();
                            String str2 = (String)title.getText();
                            String str3 = (String)address.getText();
                            //String str3 = (String)text.getText();
                            Intent intent = new Intent(MainActivity.this, UpdateMemo.class);
                            intent.putExtra("i", i); // 업데이트 변수 확인
                            intent.putExtra("maintext", str);
                            intent.putExtra("title", str2);
                            intent.putExtra("address", str3);
                            startActivityForResult(intent, 0);



                        }
                    }
                });

                id = itemView.findViewById(R.id.item_maintext1);
                title = itemView.findViewById(R.id.item_title);
                maintext = itemView.findViewById(R.id.item_maintext);
                address = itemView.findViewById(R.id.item_address);
                subtext = itemView.findViewById(R.id.item_subtext);
                todo = itemView.findViewById(R.id.todo);
                //img = itemView.findViewById(R.id.item_image);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            builder.setTitle("삭제").setMessage("삭제하시겠습니까?");

                            builder.setPositiveButton("취소", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    //Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.setNegativeButton("삭제", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    int position = getAdapterPosition();
                                     int seq = (int)title.getTag();

                                    if(position != RecyclerView.NO_POSITION){
                                        dbHelper.deleteMemo(seq);
                                        removeItem(position);
                                        notifyDataSetChanged();
                                    }

                                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });



                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();


                        return false;
                    }
                });

                todo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        int seq = (int)title.getTag();
                        if(position != RecyclerView.NO_POSITION){

                            int i = dbHelper.isdoneMemo(seq);
                            Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();

                            //todo.setChecked(true);
/*
                            if(todo.isChecked() != true) {
                                dbHelper.isdoneMemo(1, seq);
                                todo.setChecked(false);

                            } else {
                                dbHelper.isdoneMemo(0, seq);
                                todo.setChecked(true);
                                Toast.makeText(getApplicationContext(), "2번.", Toast.LENGTH_SHORT).show();
                            }*/
                            //notifyDataSetChanged();
                        }

                        //Toast.makeText(getApplicationContext(), "클릭됌.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        public RecyclerAdapter(List<Memo> listdata){
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            Memo memo = listdata.get(i);


            itemViewHolder.id.setText("no. "+memo.getId());
            itemViewHolder.title.setTag(memo.getSeq());
            itemViewHolder.title.setText(memo.getTitle());
            itemViewHolder.maintext.setText(memo.getMaintext());
            itemViewHolder.subtext.setText(memo.getSubtext());
            itemViewHolder.address.setText(memo.getAddress());

            //itemViewHolder.todo.setChecked(true);
            if(memo.getIsdone() == 0){
                itemViewHolder.todo.setChecked(false);
            } else {itemViewHolder.todo.setChecked(true);}
            //itemViewHolder.num.setText(String.valueOf(memo.getSeq()));
            //int num = memo.getSeq();
            //String num = Integer.toString(memo.getSeq());

            //itemViewHolder.num.setText(memo.getSeq());
/*
            if(memo.getIsdone() == 0){
                itemViewHolder.img.setBackgroundColor(Color.LTGRAY);


            } else {
                itemViewHolder.img.setBackgroundColor(Color.GREEN);
            }*/
        }

        void addItem(Memo memo) {
            listdata.add(memo);
        }

        void removeItem(int position){
            listdata.remove(position);

        }




    }
}