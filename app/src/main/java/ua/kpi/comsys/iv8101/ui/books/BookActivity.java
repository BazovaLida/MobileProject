package ua.kpi.comsys.iv8101.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;
import java.util.Scanner;

import ua.kpi.comsys.iv8101.R;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Book");

        Book currBook = getBookFullInfo();

        TextView tw = findViewById(R.id.book_info);
        tw.setText(Html.fromHtml(currBook.getInfo()));

        ImageView imageView = findViewById(R.id.book_image);
        imageView.setImageResource(currBook.getImageID());
    }

    private Book getBookFullInfo(){

        int fileID = getIntent().getExtras().getInt("file");
        Scanner scanner = new Scanner(getResources().openRawResource(fileID));
        try {
            String data = scanner.nextLine();
            JSONObject jsonObject = new JSONObject(data);

            String title = jsonObject.getString("title");
            String subtitle = jsonObject.getString("subtitle");
            String authors = jsonObject.getString("authors");
            String publisher = jsonObject.getString("publisher");
            String pages = jsonObject.getString("pages");
            String year = jsonObject.getString("year");
            String rating = jsonObject.getString("rating");
            String desc = jsonObject.getString("desc");
            String price = jsonObject.getString("price");
            String isbn13 = jsonObject.getString("isbn13");
            String image = jsonObject.getString("image").toLowerCase();


            int formatIndex = image.lastIndexOf(".");
            if(formatIndex == -1)
                formatIndex = 0;
            String img = image.substring(0, formatIndex);

            int imageID = getResources().getIdentifier(img, "drawable", getPackageName());
            Book currBook = new Book(title, subtitle, isbn13, price, imageID);
            currBook.setInfo(authors, publisher, pages, year, rating, desc);
            return currBook;

        } catch (JSONException e) {
            Toast.makeText(this, "JSON exception!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoSuchElementException e){
            Toast.makeText(this, "Exception while scanning file!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}