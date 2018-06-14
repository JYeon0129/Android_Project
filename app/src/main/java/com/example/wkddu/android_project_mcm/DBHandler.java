package com.example.wkddu.android_project_mcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper implements Serializable{

    public static final String DATABASE_NAME = "MCMdatabase.db";
    public static final String DATABASE_TABLE_CLIPBOARD = "clipboard";
    public static final String DATABASE_TABLE_MONTH = "month_table";
    public static final String DATABASE_TABLE_DAY = "day_table";
    public static final String DATABASE_TABLE_SCH = "schedule";

    // Clipboard TABLE
    public static final String CLIP_MONTH = "clipmonth";
    public static final String CLIP_DAY = "clipday";
    public static final String CLIP_USAGE = "clipusage";
    public static final String CLIP_PAYMENT = "clippayment";

    // Month TABLE
    public static final String MONTH_YEAR = "year";
    public static final String MONTH_MONTH = "month";
    public static final String MONTH_TOTALBUDGET = "totalbudget";
    public static final String MONTH_TOTALSPEND = "totalspend";
    public static final String MONTH_TRANSFERREMAIN = "transferremain";

    // Day TABLE
    public static final String DAY_YEAR = "year";
    public static final String DAY_MONTH = "month";
    public static final String DAY_DAY = "day";
    public static final String DAY_LIMIT = "daylimit";
    public static final String DAY_SPEND = "dayspend";

    // Schedule TABLE
    public static final String SCH_YEAR = "year";
    public static final String SCH_MONTH = "month";
    public static final String SCH_DAY = "day";
    public static final String SCH_CAT = "category";
    public static final String SCH_SPEND = "spend";
    public static final String SCH_USAGE = "usage";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("Database","onCreate Database");
        //Clipboard
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_CLIPBOARD);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_CLIPBOARD);
        String CREATE_CLIPBOARD_TABLE = "create table if not exists " + DATABASE_TABLE_CLIPBOARD + "(" + CLIP_MONTH +
                " text, " + CLIP_DAY + " text, " + CLIP_USAGE + " text, " + CLIP_PAYMENT +" integer, PRIMARY KEY("+ CLIP_MONTH + ", " +
                CLIP_DAY + ", "+ CLIP_USAGE + ", " + CLIP_PAYMENT + "))";
        db.execSQL(CREATE_CLIPBOARD_TABLE);

        //Month
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_MONTH);
        String CREATE_MONTH_TABLE = "create table if not exists " + DATABASE_TABLE_MONTH + "(" + MONTH_YEAR +
                " text, " + MONTH_MONTH + " text, " + MONTH_TOTALBUDGET + " INTEGER, " + MONTH_TOTALSPEND +" INTEGER, " +
                MONTH_TRANSFERREMAIN + " INTEGER, PRIMARY KEY("+ MONTH_YEAR + ", " +
                MONTH_MONTH+ "))";
        db.execSQL(CREATE_MONTH_TABLE);

        //Day
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_DAY);
        String CREATE_DAY_TABLE = "create table if not exists " + DATABASE_TABLE_DAY + "(" + DAY_YEAR +
                " text, " + DAY_MONTH + " text, " + DAY_DAY + " text, " + DAY_LIMIT + " INTEGER, " + DAY_SPEND +
                " INTEGER, PRIMARY KEY("+ DAY_YEAR + ", " + DAY_MONTH + ", " + DAY_DAY+ "))";
        db.execSQL(CREATE_DAY_TABLE);

        //Schedule
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_SCH);
        String CREATE_SCH_TABLE = "create table if not exists " + DATABASE_TABLE_SCH + "(" + SCH_YEAR +
                " text, " + SCH_MONTH + " text, " + SCH_DAY + " text, " + SCH_CAT + " INTEGER, " + SCH_SPEND +
                " INTEGER, " + SCH_USAGE + " text, PRIMARY KEY("+ SCH_YEAR + ", " + SCH_MONTH + ", " + SCH_DAY +
                ", " + SCH_CAT + ", " + SCH_SPEND+ ", " + SCH_USAGE + "))";
        db.execSQL(CREATE_SCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    // 클립보드 테이블 메소드
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

    // Month
    public void addMonth(TABLE_MONTH month){
        ContentValues value = new ContentValues();
        value.put(MONTH_YEAR,month.getFullYear());
        value.put(MONTH_MONTH,month.getMonth());
        value.put(MONTH_TOTALBUDGET,month.getTotal_budget());
        value.put(MONTH_TOTALSPEND,month.getTotal_spend());
        value.put(MONTH_TRANSFERREMAIN,month.getTransfer_remain());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE_MONTH, null, value);
        db.close();
    }

    public void deleteMonth(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_MONTH;
        db.execSQL(query);
        db.close();
    }
    // 1개월씩 검색해야하므로 검색키는 연도와 월
    public TABLE_MONTH getMonth(String s_year, String s_month){
        String query = "SELECT * FROM "+DATABASE_TABLE_MONTH + " WHERE " + MONTH_YEAR + " = \'"+s_year +"\' and " +
                MONTH_MONTH + " = \'"+s_month +"\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        TABLE_MONTH table_month = null;
        if(cursor.moveToFirst())
        {
            String year = "";
            String month = "";
            int total_budget = 0;
            int total_spend = 0;
            int transfer_remain = 0;

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case MONTH_YEAR:
                            year= cursor.getString(i);
                            break;
                        case MONTH_MONTH:
                            month = cursor.getString(i);
                            break;
                        case MONTH_TOTALBUDGET:
                            total_budget= cursor.getInt(i);
                            break;
                        case MONTH_TOTALSPEND:
                            total_spend = cursor.getInt(i);
                            break;
                        case MONTH_TRANSFERREMAIN:
                            transfer_remain = cursor.getInt(i);
                            break;
                    }
                }
                table_month = new TABLE_MONTH(year,month,total_budget,total_spend,transfer_remain);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return table_month;
    }

    // Day
    public void addDay(TABLE_DAY day){
        ContentValues value = new ContentValues();
        value.put(DAY_YEAR,day.getFullYear());
        value.put(DAY_MONTH,day.getMonth());
        value.put(DAY_DAY,day.getDay());
        value.put(DAY_LIMIT,day.getDay_limit());
        value.put(DAY_SPEND,day.getDay_spend());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE_DAY, null, value);
        db.close();
    }

    public void deleteDay(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_DAY;
        db.execSQL(query);
        db.close();
    }
    // 1일을 검색하므로 검색 키는 연도 월 일
    public TABLE_DAY getDay(String s_year, String s_month, String s_day){
        String query = "SELECT * FROM "+DATABASE_TABLE_DAY + " WHERE " + DAY_YEAR + " = \'"+s_year +"\' and " +
                DAY_MONTH + " = \'"+s_month +"\' and " + DAY_DAY + " = \'"+s_day +"\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        TABLE_DAY table_day = null;
        if(cursor.moveToFirst())
        {
            String year = "";
            String month = "";
            String day = "";
            int day_limit = 0;
            int day_spend = 0;

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case DAY_YEAR:
                            year= cursor.getString(i);
                            break;
                        case DAY_MONTH:
                            month = cursor.getString(i);
                            break;
                        case DAY_DAY:
                            day= cursor.getString(i);
                            break;
                        case DAY_LIMIT:
                            day_limit = cursor.getInt(i);
                            break;
                        case DAY_SPEND:
                            day_spend = cursor.getInt(i);
                            break;
                    }
                }
                table_day = new TABLE_DAY(year,month,day,day_limit, day_spend);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return table_day;
    }

    //Schedule
    public void addSch(TABLE_SCH sch){
        ContentValues value = new ContentValues();
        value.put(SCH_YEAR,sch.getFullYear());
        value.put(SCH_MONTH,sch.getMonth());
        value.put(SCH_DAY,sch.getDay());
        value.put(SCH_CAT,sch.getCategory());
        value.put(SCH_SPEND,sch.getSpend());
        value.put(SCH_USAGE,sch.getUsage());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE_SCH, null, value);
        db.close();
    }

    public void deleteSch(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_SCH;
        db.execSQL(query);
        db.close();
    }
    // 1일을 검색하므로 검색 키는 연도 월 일
    public TABLE_SCH getSch(String s_year, String s_month, String s_day, int s_cat, int s_spend, String s_usage){
        String query = "SELECT * FROM "+DATABASE_TABLE_SCH + " WHERE " + SCH_YEAR + " = \'"+s_year +"\' and " +
                SCH_MONTH + " = \'"+s_month +"\' and " + SCH_DAY + " = \'"+s_day +"\' and " + SCH_CAT + " = \'"+s_cat +"\' and " +
                SCH_SPEND + " = \'"+s_spend +"\' and " + SCH_USAGE + " = \'"+s_usage +"\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        TABLE_SCH table_sch = null;
        if(cursor.moveToFirst())
        {
            String year = "";
            String month = "";
            String day = "";
            int sch_cat = 0;
            int sch_spend = 0;
            String sch_usage ="";

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case SCH_YEAR:
                            year= cursor.getString(i);
                            break;
                        case SCH_MONTH:
                            month = cursor.getString(i);
                            break;
                        case SCH_DAY:
                            day= cursor.getString(i);
                            break;
                        case SCH_CAT:
                            sch_cat = cursor.getInt(i);
                            break;
                        case SCH_SPEND:
                            sch_spend = cursor.getInt(i);
                            break;
                        case SCH_USAGE:
                            sch_usage = cursor.getString(i);
                            break;
                    }
                }
                table_sch = new TABLE_SCH(year,month,day,sch_cat, sch_spend, sch_usage);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return table_sch;
    }

    public ArrayList<TABLE_SCH> getSchAll(){
        Log.v("getSchAll","call");
        String query = "SELECT * FROM "+ DATABASE_TABLE_SCH;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        ArrayList sch_all = new ArrayList();
        TABLE_SCH table_sch = null;
        if(cursor.moveToFirst())
        {
            String year = "";
            String month = "";
            String day = "";
            int sch_cat = 0;
            int sch_spend = 0;
            String sch_usage ="";

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case SCH_YEAR:
                            year= cursor.getString(i);
                            break;
                        case SCH_MONTH:
                            month = cursor.getString(i);
                            break;
                        case SCH_DAY:
                            day= cursor.getString(i);
                            break;
                        case SCH_CAT:
                            sch_cat = cursor.getInt(i);
                            break;
                        case SCH_SPEND:
                            sch_spend = cursor.getInt(i);
                            break;
                        case SCH_USAGE:
                            sch_usage = cursor.getString(i);
                            break;
                    }
                }
                table_sch = new TABLE_SCH(year,month,day,sch_cat, sch_spend, sch_usage);
                Log.v("getSCHall" ,year+"."+month+"."+day+" : "+sch_cat + " " + sch_usage + " " + sch_spend);
                sch_all.add(table_sch);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return sch_all;
    }

    public ArrayList<TABLE_SCH> getSchSub(String s_year, String s_month, String s_day){
        String query = "SELECT * FROM "+DATABASE_TABLE_SCH + " WHERE " + SCH_YEAR + " = \'"+s_year +"\' and " +
                SCH_MONTH + " = \'"+s_month +"\' and " + SCH_DAY + " = \'"+s_day +"\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        ArrayList sch_all = new ArrayList();
        TABLE_SCH table_sch = null;
        if(cursor.moveToFirst())
        {
            String year = "";
            String month = "";
            String day = "";
            int sch_cat = 0;
            int sch_spend = 0;
            String sch_usage ="";

            while(!cursor.isAfterLast())
            {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    switch (cursor.getColumnName(i))
                    {
                        case SCH_YEAR:
                            year= cursor.getString(i);
                            break;
                        case SCH_MONTH:
                            month = cursor.getString(i);
                            break;
                        case SCH_DAY:
                            day= cursor.getString(i);
                            break;
                        case SCH_CAT:
                            sch_cat = cursor.getInt(i);
                            break;
                        case SCH_SPEND:
                            sch_spend = cursor.getInt(i);
                            break;
                        case SCH_USAGE:
                            sch_usage = cursor.getString(i);
                            break;
                    }
                }
                table_sch = new TABLE_SCH(year,month,day,sch_cat, sch_spend, sch_usage);
                sch_all.add(table_sch);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return sch_all;
    }
}