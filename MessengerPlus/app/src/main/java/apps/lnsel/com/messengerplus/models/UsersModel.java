package apps.lnsel.com.messengerplus.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import apps.lnsel.com.messengerplus.helpers.Database.DBHelper;

/**
 * Created by apps2 on 6/14/2017.
 */
public class UsersModel extends SQLiteOpenHelper {
    public static final String CONTACTS_TABLE_NAME = "users";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    private HashMap hp;

    public UsersModel(Context context) {
        super(context, DBHelper.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table users " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("users", null, contentValues);
        return true;
    }

    public boolean insertContactNotExist (String name) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        //contentValues.put("name", name);
        //db.insertWithOnConflict("users", null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        String[] args = {name}; // where 1 is the category id
        if(dubUpCheck(name) == false){
            getWritableDatabase().execSQL("INSERT OR REPLACE INTO users (name) VALUES (?)", args);
        }

        return true;
    }

    public boolean dubUpCheck(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(CONTACTS_TABLE_NAME, null, "name = ?", new String[] {name}, null, null, null, null);
        if (cur != null && cur.getCount()>0) {
            // duplicate found
            return true;
        }
        return false;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.update("users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
