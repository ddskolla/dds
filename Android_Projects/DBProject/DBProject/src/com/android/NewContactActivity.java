package com.android;

import com.android.db.util.SQLiteOpenDBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContactActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_contact);
		
		Button addContactButton = (Button) findViewById(R.id.add_contact_button);
		addContactButton.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		

		EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
		
		String name = nameEditText.getText().toString();
		
		SQLiteOpenDBHelper sqLiteOpenDBHelper = new SQLiteOpenDBHelper(this);
		
		SQLiteDatabase db = sqLiteOpenDBHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(SQLiteOpenDBHelper.CONTACT_NAME, name);
		
		db.beginTransaction();
		
		db.insert(SQLiteOpenDBHelper.CONTACT_TABLE, null, values);
		
		db.setTransactionSuccessful();
		
		db.endTransaction();
		db.close();
		
		Toast.makeText(this, "Contact successfully added", Toast.LENGTH_LONG).show();
		
	}
	
}
