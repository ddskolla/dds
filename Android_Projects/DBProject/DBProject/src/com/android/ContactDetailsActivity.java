package com.android;

import com.android.db.util.SQLiteOpenDBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactDetailsActivity extends Activity implements OnClickListener {

	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details);

		Bundle data = getIntent().getExtras();
		id = data.getInt("id");

		String contactName = data.getString("contactName");

		EditText contactNameEditText = (EditText) findViewById(R.id.update_name_edit_text);
		contactNameEditText.setText(contactName);

		// Button initialize, setClick
		Button saveButton = (Button) findViewById(R.id.update_save_button);
		Button deleteButton = (Button) findViewById(R.id.update_delete_button);

		saveButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.update_save_button) {
			EditText contactNameEditText = (EditText) findViewById(R.id.update_name_edit_text);
			String name = contactNameEditText.getText().toString();

			if (name != null && name.trim().length() > 0) {

				SQLiteOpenDBHelper sqLiteOpenDBHelper = new SQLiteOpenDBHelper(
						this);
				SQLiteDatabase db = sqLiteOpenDBHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(SQLiteOpenDBHelper.CONTACT_NAME, name);
				db.beginTransaction();
				String[] whereArgs = new String[1];
				whereArgs[0] = Integer.toString(id);
				db.update(SQLiteOpenDBHelper.CONTACT_TABLE, values, "id=?",
						whereArgs);
				db.setTransactionSuccessful();
				db.endTransaction();
				db.close();
			}

		} else { // Delete
			SQLiteOpenDBHelper sqLiteOpenDBHelper = new SQLiteOpenDBHelper(this);
			SQLiteDatabase db = sqLiteOpenDBHelper.getWritableDatabase();
			db.beginTransaction();

			String[] whereArgs = new String[1];
			whereArgs[0] = Integer.toString(id);
			db.delete(SQLiteOpenDBHelper.CONTACT_TABLE, "id=?", whereArgs);
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();

		}

	}

	// onClick, edit save, delete, both uses SQLDbHelper
}
