package com.androidinsettegiorni_cap4;

import android.os.Bundle;
import android.content.ContentValues;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import com.androidinsettegiorni_cap4.R;
import com.androidinsettegiorni_cap4.FragmentItemsList.ItemsListListener;

public class MainActivity extends FragmentActivity 
implements OnClickListener, ItemsListListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageButton btnAdd = (ImageButton)findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
				
	}

	

	@Override
	public void onClick(View v) {
		
		ContentValues values = new ContentValues();
		values.put(LentItemsContract.Items.NAME, "test item");
		getContentResolver().insert(LentItemsContract.Items.CONTENT_URI, values); //list is updated automatically, thanks to the loader
	}

	@Override
	public void onItemClick(long id) {
		
		String where = LentItemsContract.SELECTION_ID_BASED;
		String [] selectionArgs = {Long.toString(id)};
		getContentResolver().delete(LentItemsContract.Items.CONTENT_URI, where, selectionArgs); //list is updated automatically, thanks to the loader
		
		Toast.makeText(this, "deleted "+Long.toString(id), Toast.LENGTH_SHORT).show();
	}

	
}
