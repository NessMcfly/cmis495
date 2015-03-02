package com.test.getcallhist;

import java.sql.Date;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv = (TextView) findViewById(R.id.listAllTxt);
		/*
		    We create a cursor (a temporary database
		 						row where we store
		 						1 row's values at a
		 						time, to be processed)
		 	We assign the read values to variables
		 */
		StringBuffer sb = new StringBuffer();// What will be eventually printed to the TextView
		Cursor cur = managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
		int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
		int date = cur.getColumnIndex(CallLog.Calls.DATE);
		int type = cur.getColumnIndex(CallLog.Calls.TYPE);
		int name = cur.getColumnIndex(CallLog.Calls.CACHED_NAME);
		
		sb.append("We will load this into an Array and use it with another array to assign sounds!");
		
		/* Use a while loop to load each row, otherwise we'll
		   only load 1. This is where we will modify the code
		   to load the values into our array
		 */
		int count = 0;
		while (cur.moveToNext() && count < 5){
			String phoneNumber = cur.getString(number);
			Date callDate = new Date(Long.valueOf(cur.getString(date)));
			String callType = cur.getString(type);
			String callerName = cur.getString(name);
			
			/*  Here we create conditions based on call
			 	type, such as ignoring outgoing calls 
			 	or assigning special noises for missed
			 	calls
			 */
			String dir = null;
			int dircode = Integer.parseInt(callType);
			
			switch(dircode){
			case CallLog.Calls.OUTGOING_TYPE:
				dir = "Outgoing";
				break;
			case CallLog.Calls.INCOMING_TYPE:
				dir = "Incoming";
				break;
			case CallLog.Calls.MISSED_TYPE:
				dir = "Missed";
				break;
			}
			
			sb.append("\nName: " + callerName + "\n#: " + phoneNumber + "\nDate: " + callDate + "\nType: " + dir);
			sb.append("\n_____________");
			count++;
		}
		
		cur.close();// We always have to close a cursor
		tv.setText(sb);//Don't forget to update the TextView!
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
