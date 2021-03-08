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
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
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

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View library = inflater.inflate(R.layout.item_book, parent, false);

        // Return a new holder instance
        return new ViewHolder(library, onBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Book book = books.get(position);

        // Set item views based on your views and data model
        holder.title.setText(book.getTitle());

        holder.subtitle.setText(book.getSubtitle());

        holder.price.setText(book.getPrice());

        holder.imageView.setImageResource(book.getImageID());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnBookListener {
        void onBookClick(int position);
    }

    // method for filtering our recyclerview items.
    public void changeList(ArrayList<Book> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        books = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}