package krasnov.bookcrossing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShelfAdapter extends ArrayAdapter<Book> {

    private LayoutInflater inflater;
    private int layout;
    private List<Book> books;

    public ShelfAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.books = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView flagView = (ImageView) view.findViewById(R.id.iconBook);
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView capitalView = (TextView) view.findViewById(R.id.shortInfo);

        Book one = books.get(position);

        flagView.setImageResource(one.getIconResource());
        author.setText(one.getAuthorToString());
        title.setText(one.getTitle());
        capitalView.setText(one.getDescription());

        return view;
    }
}
