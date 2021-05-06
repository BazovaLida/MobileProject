package ua.kpi.comsys.iv8101.ui.books;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SQLiteDatabaseBooksHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookDB";
    private static final String TABLE_NAME = "Books";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBTITLE = "subtitle";
    private static final String KEY_ISBN = "isbn";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMAGE_SRC = "image_source";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_PAGES = "pages";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RATING = "rating";
    private static final String KEY_DESCRIPTION = "description";
    private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_SUBTITLE, KEY_ISBN, KEY_PRICE,
            KEY_IMAGE_SRC, KEY_AUTHORS, KEY_PUBLISHER, KEY_PAGES, KEY_YEAR, KEY_RATING, KEY_DESCRIPTION };

    public SQLiteDatabaseBooksHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, subtitle TEXT, isbn TEXT, "
                + "price TEXT, image_source TEXT, authors TEXT, publisher TEXT, pages TEXT, "
                + "year TEXT, rating TEXT, description TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    public ArrayList<Book> search(String request) {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                if(title.toLowerCase().contains(request.toLowerCase())) {
                    books.add(getBookFrom(cursor, false));
                }
            } while (cursor.moveToNext());
        }
        return books;
    }

    public Book getByIsbn(String isbn) {
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String isbn13 = cursor.getString(3);
                if (isbn13.equals(isbn)) {
                    String test = cursor.getString(6);
                    if(test != null)
                        return getBookFrom(cursor, true);
                    else return null;
                }
            } while (cursor.moveToNext());

        }
        return null;
    }

    private Book getBookFrom(Cursor cursor, boolean full) {
        String title = cursor.getString(1);
        String subtitle = cursor.getString(2);
        String isbn13 = cursor.getString(3);
        String price = cursor.getString(4);
        String imageSRC = cursor.getString(5);

        Book book = new Book(title, subtitle, isbn13, price, imageSRC);
        if(full) {
            String authors = cursor.getString(6);
            String publisher = cursor.getString(7);
            String pages = cursor.getString(8);
            String year = cursor.getString(9);
            String rating = cursor.getString(10);
            String desc = cursor.getString(11);

            book.setInfo(authors, publisher, pages, year, rating, desc);
        }

        return book;
    }

    public void addBook(Book book) {
        book.setId();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_SUBTITLE, book.getSubtitle());
        values.put(KEY_ISBN, book.getIsbn13());
        values.put(KEY_PRICE, book.getPrice());
        values.put(KEY_IMAGE_SRC, book.getImageSRC());
        // insert
        db.insert(TABLE_NAME,null, values);
    }

    public void update(Book book){
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_SUBTITLE, book.getSubtitle());
        values.put(KEY_ISBN, book.getIsbn13());
        values.put(KEY_PRICE, book.getPrice());
        values.put(KEY_IMAGE_SRC, book.getImageSRC());
        values.put(KEY_AUTHORS, book.getAuthors());
        values.put(KEY_PUBLISHER, book.getPublisher());
        values.put(KEY_PAGES, book.getPages());
        values.put(KEY_YEAR, book.getYear());
        values.put(KEY_RATING, book.getRating());
        values.put(KEY_DESCRIPTION, book.getDesc());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, "id = ?", new String[] { String.valueOf(book.getId()) });
    }
}
