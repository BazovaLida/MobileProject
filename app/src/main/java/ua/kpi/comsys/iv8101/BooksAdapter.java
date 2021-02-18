package ua.kpi.comsys.iv8101;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>  {
    private ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> bookList) {
        books = bookList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, subtitle, price;
        public final ImageView imageView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            subtitle = itemView.findViewById(R.id.book_subtitle);
            price = itemView.findViewById(R.id.book_price);
            imageView = itemView.findViewById(R.id.book_image);
        }
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View library = inflater.inflate(R.layout.item_book, parent, false);

        // Return a new holder instance
        return new ViewHolder(library);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Book book = books.get(position);

        // Set item views based on your views and data model
        TextView title = holder.title;
        title.setText(book.getTitle());

        TextView subtitle = holder.subtitle;
        subtitle.setText(book.getSubtitle());

        TextView price = holder.price;
        price.setText(book.getPrice());

        ImageView imageView = holder.imageView;
        imageView.setImageResource(book.getImageID());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
