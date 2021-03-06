package com.example.contactstest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView contactsView;
	ArrayAdapter<String> adapter;
	List<String> contactList = new ArrayList<String>(){};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		contactsView = (ListView) findViewById(R.id.contacts_view);
		adapter = new ArrayAdapter<String>(this, android.R.
				layout.simple_list_item_1, contactList);
		contactsView.setAdapter(adapter);
		readContacts();
	}

	private void readContacts(){
		Cursor cursor = null;
		try {
			//查询联系人数据
			cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				//获取联系人姓名
				String displayName = cursor.getString(cursor.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				//获取联系人手机号
				String number = cursor.getString(cursor.getColumnIndex
						(ContactsContract.CommonDataKinds.Phone.NUMBER));
				
				contactList.add(displayName + "\n" + number);
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}
	}
}
