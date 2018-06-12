package com.example.wkddu.android_project_mcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper implements Serializable{

    public static final String DATABASE_NAME = "MCMdatabase.db";
    public static final String DATABASE_TABLE_INDIVIDUAL = "individual";
    public static final String DATABASE_TABLE_CLIPBOARD = "clipboard";

    //
    public static final String ID = "id"; // 사용 내역 목록
    public static final String PROMISENAME = "promisename"; // 약속이름
    public static final String PROMISEKIND = "promisekind"; //  약속종류()<ex)식비,술/유흥,주거/통신,생활용품,의복/미용,건강/문화,교육,교통,기타>
    public static final String EXPECTEDSPEND = "expectedspend"; // 예상비용
    public static final String TOTALBUDGET = "totalbudget";  // 총예산 수입(지금까지의 예산수입+한달예산수입(+이월금(달 바뀔 때))
    public static final String MONTHINCOME = "monthincome"; // 한달예산 수입<ex)월급, 용돈>
    public static final String TOTALSPEND = "totalspend"; // 총사용금액(사용한 돈들의 합)
    public static final String GOALMONEY = "goalmoney"; // 목표금액
    public static final String SPENDSTORE = "spendstore"; // 결제 매장
    public static final String SPEND = "spend"; // 사용한 돈(자동으로도 받아와야한다.)
    public static final String TODAYPOCKET = "todaypocket"; // 오늘 하루 사용 가능한 용돈
    public static final String USEDAY = "useday"; // 돈을 사용한 날짜
    public static final String TRANSFERREMAINDER = "transferremainder"; // 이월금(달 마지막날의 잔액(자정전 마지막 사용금액을 뺀 잔액)이 이월금이 된다.)
    public static final String REMAINDER = "remainder"; // 잔액
    public static final String USEPERCENT = "usepercent"; // 사용률(총예산수입에서 얼마나 사용했는지 퍼센트(화면상단바)로 보여준다.)

    // Clipboard TABLE
    public static final String CLIP_MONTH = "clipmonth";
    public static final String CLIP_DAY = "clipday";
    public static final String CLIP_USAGE = "clipusage";
    public static final String CLIP_PAYMENT = "clippayment";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Clipboard
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_CLIPBOARD);
        String CREATE_CLIPBOARD_TABLE = "create table if not exists " + DATABASE_TABLE_CLIPBOARD + "(" + CLIP_MONTH +
                " text, " + CLIP_DAY + " text, " + CLIP_USAGE + " text, " + CLIP_PAYMENT +" integer, PRIMARY KEY("+ CLIP_MONTH + ", " +
                CLIP_DAY + ", "+ CLIP_USAGE + ", " + CLIP_PAYMENT + "))";
        db.execSQL(CREATE_CLIPBOARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addClipboard(Clipboard clipboard){
        ContentValues value = new ContentValues();
        value.put(CLIP_MONTH,clipboard.getMonth());
        value.put(CLIP_DAY,clipboard.getDay());
        value.put(CLIP_USAGE,clipboard.getUsage());
        value.put(CLIP_PAYMENT,clipboard.getPayment());

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_CLIPBOARD;
        db.execSQL(query);
        db.insert(DATABASE_TABLE_CLIPBOARD, null, value);
        db.close();
    }
    // clipboard 테이블은 1개의 row만 가지기 때문에 별도의 파라미터가 필요없음.
    public void deleteClipboard(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_CLIPBOARD;
        db.execSQL(query);
        db.close();
    }

    public Clipboard getClipboard(){
        String query = "SELECT * FROM "+DATABASE_TABLE_CLIPBOARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Clipboard clipboard = null;
        if(cursor.moveToFirst())
        {
            String month="";
            String day="";
            String usage="";
            String payment="";

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case CLIP_MONTH:
                            month= cursor.getString(i);
                            break;
                        case CLIP_DAY:
                            day = cursor.getString(i);
                            break;
                        case CLIP_USAGE:
                            usage= cursor.getString(i);
                            break;
                        case CLIP_PAYMENT:
                            payment = cursor.getString(i);
                            break;
                    }
                }

                clipboard = new Clipboard(month,day,usage,payment);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return clipboard;
    }
}
