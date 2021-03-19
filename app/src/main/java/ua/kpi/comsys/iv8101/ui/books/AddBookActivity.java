package ua.kpi.comsys.iv8101.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ua.kpi.comsys.iv8101.R;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Book");

        addFunctionality();
    }

    private void addFunctionality(){
        final EditText titleEditText = findViewById(R.id.title_input);
        final EditText subtitleEditText = findViewById(R.id.subtitle_input);
        final EditText priceEditText = findViewById(R.id.price_input);
        final Button addButton = findViewById(R.id.add);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                    addButton.setEnabled(true);
                else
                    addButton.setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleEditText.getText().toString();
                String newSubtitle = subtitleEditText.getText().toString();
                String newPrice = "$" + priceEditText.getText().toString();
                Book newBook = new Book(newTitle, newSubtitle, "", newPrice, 0);
                BooksFragment.addBook(newBook);
                finish();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}