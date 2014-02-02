package com.android.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "contactsdb";
	private static final int DB_VERSION = 1;
	
	public static final String CONTACT_TABLE = "contact";
	public static final String CONTACT_NAME = "contact_name";
	
	public SQLiteOpenDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String query = "CREATE TABLE IF NOT EXISTS "+CONTACT_TABLE
		+" ( _id integer primary key autoincrement, "
		+ CONTACT_NAME +" text );" ;
		
		db.execSQL(query);
		
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

		
	}

}
