package example.com.omgandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by frank on 03.07.14.
 */
public class JSONAdapter extends BaseAdapter {
    public static final String IMAGE_BASE_URL = "http://covers.openlibrary.org/b/id/";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context mContext, LayoutInflater mInflater) {
        this.mContext = mContext;
        this.mInflater = mInflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_book, null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.text_author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);
        if(jsonObject.has("cover_i")) {
            String imageID = jsonObject.optString("cover_i");
            String imageURL = IMAGE_BASE_URL + imageID + "-S.jpg";
            Picasso.with(mContext).load(imageURL).placeholder(R.drawable.ic_books).into(holder.thumbnailImageView);
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.ic_books);
        }

        String bookTitle = "";
        String authorName = "";
        if(jsonObject.has("title")) {
            bookTitle = jsonObject.optString("title");
        }
        if(jsonObject.has("author_name")) {
            authorName = jsonObject.optString("author_name");
        }
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);

        return convertView;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView authorTextView;
    }
}
