package ua.kpi.comsys.iv8101.ui.books;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.kpi.comsys.iv8101.R;


public class BooksFragment extends Fragment implements BooksAdapter.OnBookListener  {
    private static final ArrayList<Book> library = new ArrayList<>();
    public static BooksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        setUpRecyclerView(recyclerView, view);
    }

    private void setUpRecyclerView(RecyclerView recyclerView, View view) {
        Context context = requireActivity().getApplicationContext();

        adapter = new BooksAdapter(library, this);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);



        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(context, R.drawable.ic_delete);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Book deleted = adapter.getBooks().remove(position);
                library.remove(deleted);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Deleted book " + deleted.getTitle(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }



    private void onSearch(String request) {

        TextView noFoundMsg = (TextView)  requireView().findViewById(R.id.no_book_msg);
        noFoundMsg.setVisibility(View.GONE);

        // https://ybq.github.io/Android-SpinKit
        ProgressBar progressBar = (ProgressBar)requireView().findViewById(R.id.loading_books);
        progressBar.setVisibility(View.VISIBLE);

        String url = "https://api.itbook.store/1.0/search/" + request;

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray booksInJSON = response.getJSONArray("books");

                            for (int i = 0; i < booksInJSON.length(); i++) {
                                JSONObject c = booksInJSON.getJSONObject(i);
                                String title = c.getString("title");
                                String subtitle = c.getString("subtitle");
                                String isbn13 = c.getString("isbn13");
                                String price = c.getString("price");
                                String imageURL = c.getString("image");


                                library.add(new Book(title, subtitle, isbn13, price, imageURL));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "JSON exception!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        if(library.isEmpty())
                            noFoundMsg.setVisibility(View.VISIBLE);
                        adapter.changeList(library);
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getContext(), "Error while getting response", Toast.LENGTH_LONG).show();
                        // handle error
                    }
                });
    }

    public static void addBook(Book book){
        library.add(book);
        adapter.changeList(library);
    }

    @Override
    public void onBookClick(int position) {
        String isbn = adapter.getBooks().get(position).getIsbn13();

        if(!isbn.equals("")) {
            Intent intent = new Intent(requireActivity(), BookActivity.class);
            intent.putExtra("isbn", isbn);
            startActivity(intent);
        }
    }

    // calling on create option menu layout to inflate our menu file.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // inside inflater we are inflating our menu file.
        menuInflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Book> found = new ArrayList<>();
                for (Book item: library) {
                    if(!item.isCreated())
                        found.add(item);
                }
                library.removeAll(found);
                if(library.isEmpty()){
                    TextView noFoundMsg = (TextView)  requireView().findViewById(R.id.no_book_msg);
                    noFoundMsg.setVisibility(View.VISIBLE);

                    // https://ybq.github.io/Android-SpinKit
                    ProgressBar progressBar = (ProgressBar)requireView().findViewById(R.id.loading_books);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.changeList(library);

                if(newText.length() > 2)
                    onSearch(newText);

                return false;
            }
        });

        MenuItem addItem = menu.findItem(R.id.actionAddBook);
        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(requireActivity(), AddBookActivity.class);
                startActivity(intent);
                return true;
            }
        });

    }

}