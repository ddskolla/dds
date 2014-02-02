package com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.db.entity.Contact;
import com.android.db.util.SQLiteOpenDBHelper;

public class DBActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ListView contactsListView;
	private ListAdapter adapter;

	private ArrayList<Contact> contacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		contactsListView = (ListView) findViewById(R.id.contacts_list_view);
		contactsListView.setOnItemClickListener(this);
		
		contacts = new ArrayList<Contact>();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getDataForTheListView());
		contactsListView.setAdapter(adapter);

		Button newContactButton = (Button) findViewById(R.id.new_contact_button);
		newContactButton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getDataForTheListView());
		contactsListView.setAdapter(adapter);

	}

	private ArrayList<String> getDataForTheListView() {

		ArrayList<String> data = new ArrayList<String>();

		SQLiteOpenDBHelper sqLiteOpenDBHelper = new SQLiteOpenDBHelper(this);

		SQLiteDatabase db = sqLiteOpenDBHelper.getReadableDatabase();
			
		String selectionArgs[] = new String[1];
		selectionArgs[0] = "A";
		
		String sql = "SELECT * FROM contact WHERE contact_name=?";
		
		Cursor cursor = db.query(SQLiteOpenDBHelper.CONTACT_TABLE, null, null,
				null, null, null, null);
		
	//	Cursor cursor = db.rawQuery(sql, selectionArgs);

		startManagingCursor(cursor);
		
		contacts.clear();

		while (cursor.moveToNext()) {

			int id = cursor.getInt(0);
			String name = cursor.getString(1);

			Contact c = new Contact();
			c.setId(id);
			c.setContactName(name);
			contacts.add(c);
			
			data.add(name);
		}

		//db.close();

		return data;

	}

	public void onClick(View v) {

		Intent intent = new Intent(this, NewContactActivity.class);
		startActivity(intent);

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int selectedIndex, long arg3) {
		
		
		Contact selectedContact = contacts.get(selectedIndex);

		Intent intent = new Intent(this, ContactDetailsActivity.class);
		
		Bundle data = new Bundle();
		data.putInt("id", selectedContact.getId());
		data.putString("contactName", selectedContact.getContactName());
		
		intent.putExtras(data);
		
		startActivity(intent);
		
		
		
		
	}

}