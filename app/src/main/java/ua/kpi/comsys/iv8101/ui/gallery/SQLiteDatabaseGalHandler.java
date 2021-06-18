package ua.kpi.comsys.iv8101.ui.gallery;

        import android.database.sqlite.SQLiteOpenHelper;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import java.util.ArrayList;

public class SQLiteDatabaseGalHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PictureDB";
    private static final String TABLE_NAME = "Pictures";
    private static final String KEY_ID = "id";
    private static final String KEY_LINK = "link";
    private static final String[] COLUMNS = { KEY_ID, KEY_LINK };

    public SQLiteDatabaseGalHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, link TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    public void addItem(Picture picture) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, picture.getLink());
        values.put(KEY_LINK, picture.getId());
        // insert
        db.insert(TABLE_NAME,null, values);
    }

    public ArrayList<Picture> allItems() {
        ArrayList<Picture> pictures = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Picture picture;

        if (cursor.moveToFirst()) {
            do {
                Integer id = Integer.parseInt(cursor.getString(0));
                String link = cursor.getString(1);

                picture = new Picture(link, id);
                pictures.add(picture);
            } while (cursor.moveToNext());
        }
        return pictures;
    }
}
