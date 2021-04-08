package ua.kpi.comsys.iv8101.ui.books;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;

import ua.kpi.comsys.iv8101.R;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>  {
    private ArrayList<Book> books;
    private final OnBookListener onBookListener;

    public BooksAdapter(ArrayList<Book> bookList, OnBookListener onClick) {
        books = bookList;
        onBookListener = onClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView title, subtitle, price;
        public final ImageView imageView;
        public final OnBookListener onBookListener;

        public ViewHolder(View itemView, OnBookListener onBookListener) {
            super(itemView);

            title = itemView.findViewById(R.id.book_title);
            subtitle = itemView.findViewById(R.id.book_subtitle);
            price = itemView.findViewById(R.id.book_price);
            imageView = itemView.findViewById(R.id.book_icon);

            this.onBookListener = onBookListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            onBookListener.onBookClick(getAdapterPosition());
        }
    }

    public interface OnBookListener {
        void onBookClick(int position);
    }


    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View library = inflater.inflate(R.layout.item_book, parent, false);

        // Return a new holder instance
        return new ViewHolder(library, onBookListener);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Book book = books.get(position);

        // Set item views based on your views and data model
        holder.title.setText(book.getTitle());

        holder.subtitle.setText(book.getSubtitle());

        holder.price.setText(book.getPrice());

        Ion.with(holder.imageView)
                .load(book.getImageSRC());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void changeList(ArrayList<Book> filterllist) {
        books = filterllist;
        notifyDataSetChanged();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}