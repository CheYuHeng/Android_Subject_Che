package bean;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import database.RecordDataBase;
import database.RecordOpenHelper;

public class RecordProvider extends ContentProvider {

    private RecordOpenHelper rdOpenHelper;

    public static final int MULTIPLE_RECORDS = 0;
    public static final int SINGLE_RECORD = 1;

    public static final String AUTHORITY = "com.example.memo.provider";

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(AUTHORITY, "myrecord", MULTIPLE_RECORDS);
        uriMatcher.addURI(AUTHORITY, "myrecord/#", SINGLE_RECORD);
    }

    public RecordProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = rdOpenHelper.getWritableDatabase();
        int count = 0;
        switch(uriMatcher.match(uri)){
            case MULTIPLE_RECORDS:
                count = db.delete(Record.Rec.TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_RECORD:
                String whereClause = Record.Rec._ID + "=" + uri.getPathSegments().get(1);
                count = db.delete(Record.Rec.TABLE_NAME, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch(uriMatcher.match(uri)){
            case MULTIPLE_RECORDS:
                return Record.Rec.MINE_TYPE_MULTIPLE;
            case SINGLE_RECORD:
                return Record.Rec.MINE_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("UNKnown Uri: " + uri);
//            throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = rdOpenHelper.getWritableDatabase();
        long id = db.insert(Record.Rec.TABLE_NAME, null, values);
        if(id > 0){
            Uri newUri = ContentUris.withAppendedId(Record.Rec.CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
//        SQLiteDatabase db = rdOpenHelper.getReadableDatabase();
        rdOpenHelper = new RecordOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = rdOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Record.Rec.TABLE_NAME);
        switch(uriMatcher.match(uri)){
            case MULTIPLE_RECORDS:
                return db.query(Record.Rec.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
            case SINGLE_RECORD:
                qb.appendWhere(Record.Rec._ID + "=" + uri.getPathSegments().get(1));
                return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = rdOpenHelper.getWritableDatabase();
        int count = 0;
        switch(uriMatcher.match(uri)){
            case MULTIPLE_RECORDS:
                count = db.update(Record.Rec.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SINGLE_RECORD:
                String segment = uri.getPathSegments().get(1);
                count = db.update(Record.Rec.TABLE_NAME, values,
                        Record.Rec._ID + "=" + segment, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
