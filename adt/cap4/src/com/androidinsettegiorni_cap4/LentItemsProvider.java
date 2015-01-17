package com.androidinsettegiorni_cap4;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.androidinsettegiorni_cap4.LentItemsContract.Items;

/**
 * The actual provider class for the lentitems provider. Clients do not use it
 * directly. Nor do they see it.
 */
public class LentItemsProvider extends ContentProvider {

	// Costanti
	private static final int ITEM_LIST = 1;
	private static final int ITEM_ID = 2;

	private static final UriMatcher URI_MATCHER;

	// setup UriMatcher
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(LentItemsContract.AUTHORITY, "items", ITEM_LIST);
		URI_MATCHER.addURI(LentItemsContract.AUTHORITY, "items/#", ITEM_ID);
	}

	private LentItemsOpenHelper mHelper = null;

	/**
	 * Initialize your provider. The Android system calls this method
	 * immediately after it creates your provider. Notice that your provider is
	 * not created until a ContentResolver object tries to access it.
	 */
	@Override
	public boolean onCreate() {
		mHelper = new LentItemsOpenHelper(getContext());
		return false;
	}

	/**
	 * Retrieve data from your provider. Use the arguments to select the table
	 * to query, the rows and columns to return, and the sort order of the
	 * result. Return the data as a Cursor object.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = mHelper.getReadableDatabase();
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		switch (URI_MATCHER.match(uri)) {
		case ITEM_LIST:
			builder.setTables(DbSchema.TBL_ITEMS);
			if (TextUtils.isEmpty(sortOrder)) {
				sortOrder = Items.SORT_ORDER_DEFAULT;
			}
			break;
		case ITEM_ID:
			builder.setTables(DbSchema.TBL_ITEMS);
			// limite la query all'elemento passato come parametro
			builder.appendWhere(Items._ID + " = " + uri.getLastPathSegment());
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		// notifica i listener per ogni possibile cambiamento
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	/**
	 * Insert a new row into your provider. Use the arguments to select the
	 * destination table and to get the column values to use. Return a content
	 * URI for the newly-inserted row.
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = mHelper.getWritableDatabase();
		if (URI_MATCHER.match(uri) == ITEM_LIST) {
			long id = db.insert(DbSchema.TBL_ITEMS, null, values);

			Uri retUri = ContentUris.withAppendedId(uri, id);

			// Notifica eventuali cambiamenti
			getContext().getContentResolver().notifyChange(retUri, null);

			return retUri;
		}

		throw new SQLException("Problema inserimento -> uri: " + uri);
	}

	/**
	 * Update existing rows in your provider. Use the arguments to select the
	 * table and rows to update and to get the updated column values. Return the
	 * number of rows updated.
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mHelper.getWritableDatabase();
		int updateCount = 0;

		switch (URI_MATCHER.match(uri)) {
		case ITEM_LIST:
			updateCount = db.update(DbSchema.TBL_ITEMS, values, selection, selectionArgs);
			break;
		case ITEM_ID:
			String idStr = uri.getLastPathSegment();
			String where = Items._ID + " = " + idStr;
			if (!TextUtils.isEmpty(selection)) {
				where += " AND " + selection;
			}
			updateCount = db.update(DbSchema.TBL_ITEMS, values, where, selectionArgs);
			break;
		default:
			// Operazione non supportata
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		// Notifica eventuali cambiamenti
		if (updateCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return updateCount;

	}

	/**
	 * Delete rows from your provider. Use the arguments to select the table and
	 * the rows to delete. Return the number of rows deleted.
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mHelper.getWritableDatabase();
		int delCount = 0;

		switch (URI_MATCHER.match(uri)) {
		case ITEM_LIST:
			delCount = db.delete(DbSchema.TBL_ITEMS, selection, selectionArgs);
			break;
		case ITEM_ID:
			String idStr = uri.getLastPathSegment();
			String where = Items._ID + " = " + idStr;
			if (!TextUtils.isEmpty(selection)) {
				where += " AND " + selection;
			}
			delCount = db.delete(DbSchema.TBL_ITEMS, where, selectionArgs);
			break;
		default:
			// Operazione non supportata
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		// Notifica eventuali cambiamenti
		if (delCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return delCount;
	}

	/**
	 * Return the MIME type corresponding to a content URI.
	 */
	@Override
	public String getType(Uri uri) {

		switch (URI_MATCHER.match(uri)) {
		case ITEM_LIST:
			return Items.CONTENT_TYPE;
		case ITEM_ID:
			return Items.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

	}
}
