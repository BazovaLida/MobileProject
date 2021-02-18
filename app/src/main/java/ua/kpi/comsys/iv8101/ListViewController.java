package ua.kpi.comsys.iv8101;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListViewController extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.bookslist));
        try {
            String data = scanner.nextLine();
            JSONObject jsonObject = new JSONObject(data);
            ArrayList<Book> library = processingJsonObj(jsonObject);

            recyclerView.setAdapter(new BooksAdapter(library));
        } catch (JSONException e) {
            Toast.makeText(context, "JSON exception!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoSuchElementException e){
            Toast.makeText(context, "Exception while scanning file!", Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<Book> processingJsonObj(JSONObject jsonObject) throws JSONException {
        JSONArray booksInJSON = jsonObject.getJSONArray("books");
        ArrayList<Book> library = new ArrayList<>();
        for (int i = 0; i < booksInJSON.length(); i++) {
            JSONObject c = booksInJSON.getJSONObject(i);
            String title = c.getString("title");
            String subtitle = c.getString("subtitle");
            String isbn13 = c.getString("isbn13");
            String price = c.getString("price");
            String image = c.getString("image").toLowerCase();


            int formatIndex = image.lastIndexOf(".");
            if(formatIndex == -1)
                formatIndex = 0;
            String img = image.substring(0, formatIndex);

            int imageID = getResources().getIdentifier(img, "drawable", getContext().getPackageName());
            library.add(new Book(title, subtitle, isbn13, price, imageID));
        }
        Toast.makeText(getActivity().getApplicationContext(), "here", Toast.LENGTH_SHORT).show();
        return library;

    }
}