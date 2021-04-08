package ua.kpi.comsys.iv8101.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import ua.kpi.comsys.iv8101.R;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Book");

        getBookFullInfo();

    }

    private void getBookFullInfo(){
        String isbn = getIntent().getExtras().getString("isbn");
        String url = "https://api.itbook.store/1.0/books/" + isbn.substring(1);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_info);
        TextView tv = findViewById(R.id.book_info);

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String title = response.getString("title");
                            String subtitle = response.getString("subtitle");
                            String authors = response.getString("authors");
                            String publisher = response.getString("publisher");
                            String pages = response.getString("pages");
                            String year = response.getString("year");
                            String rating = response.getString("rating");
                            String desc = response.getString("desc");
                            String price = response.getString("price");
                            String isbn13 = response.getString("isbn13");
                            String image = response.getString("image");


                            Book currBook = new Book(title, subtitle, isbn13, price, image);
                            currBook.setInfo(authors, publisher, pages, year, rating, desc);

                            ImageView imageView = findViewById(R.id.book_image);

                            tv.setText(Html.fromHtml(currBook.getInfo()));
                            Ion.with(imageView).load(currBook.getImageSRC());
                            progressBar.setVisibility(View.GONE);
                        }
                        catch (JSONException e) {
                            tv.setText(Html.fromHtml("JSONException!"));
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        tv.setText(Html.fromHtml("Get request error."));
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}