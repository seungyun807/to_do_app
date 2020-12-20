package org.techtown.memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SQLiteHelper {


    private static final String dbName = "myMemotest";
    private static final String table1 = "MemoTable";
    private static final int dbVersion = 1;

    private OpenHelper opener;
    private SQLiteDatabase db;

    private Context context;

    public SQLiteHelper(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create = "CREATE TABLE "+ table1 + "(" +
                    "'seq' integer PRIMARY KEY AUTOINCREMENT, "+
                    //"integer seq," +
                    "'title' title," +
                    "'address' text," +
                    "'maintext' text," +
                    "'subtext' text," +
                    "'isdone' integer)";
            sqLiteDatabase.execSQL(create);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+table1);
            onCreate(sqLiteDatabase);
        }
    }
    // INSERT INTO MemoTable VALUES(NULL, 'MAINTEXT', 'SUBTEXT', 0);
    public void insertMemo(Memo memo){

        String sql = "INSERT INTO "+table1+" VALUES(NULL, '"+memo.title+"','"+memo.address+"','"+memo.maintext+"','"+memo.subtext+"',"
                +memo.getIsdone()+");";

        db.execSQL(sql);
        return;
    }

    public void updateMemo(Memo memo){
        String sql = "UPDATE "+table1+" SET title='"+memo.title+"', address='"+memo.address+"', maintext='"+memo.maintext+"' WHERE seq="+memo.getSeq()+";";
        db.execSQL(sql);
        return;
    }

    public int isdoneMemo(int position){

        String sql1 = "SELECT isdone FROM "+table1+" WHERE seq="+position+";";
        //db.execSQL(sql1);
        Cursor results = db.rawQuery(sql1, null);
        results.moveToFirst();
        int i = results.getInt(0);
        if (i == 0){
            String sql = "UPDATE "+table1+" SET isdone="+1+" WHERE seq="+position+";";
            db.execSQL(sql);
        } else {
            String sql = "UPDATE "+table1+" SET isdone="+0+" WHERE seq="+position+";";
            db.execSQL(sql);
        }
        //String sql = "UPDATE "+table1+" SET isdone="+isdone+" WHERE seq="+position+";";
        //db.execSQL(sql);
        return i;
    }

    // DELETE FROM MemoTable WHERE seq = 0;
    public void deleteMemo(int position){
        String sql = "DELETE FROM "+table1+" WHERE seq = "+position+";";
        db.execSQL(sql);

    }

    // SELECT * FROM MemoTable;

    public ArrayList<Memo> selectALL(){
        String sql = "SELECT * FROM "+table1;
        ArrayList<Memo> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();
        //int index = results.getColumnIndex(0);
        while (!results.isAfterLast()) {

            Memo memo = new Memo(results.getInt(0), results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getInt(5));
            list.add(memo);
            results.moveToNext();

        }
        //int i = results.getInt(0);
        results.close();

        return list;
    }



}
