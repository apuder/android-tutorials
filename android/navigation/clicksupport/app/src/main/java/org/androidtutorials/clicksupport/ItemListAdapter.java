package org.androidtutorials.clicksupport;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Class ItemAdapter manages the underlying data model and populates so-called ViewHolder instances
 * <p>
 * The adapter does not contain any OnClickListener interface(s) neither does the GestureDetector to recognize user inputs. This
 * way the adapter stays clean and loosely coupled from the listeners which in turn adds more to faster performance while
 * scrolling and also advocates clean as well as manageable code.
 * </p>
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ListItemViewHolder> {

    private static final String TAG = "~!@#$ItemListAdptr";
    private List<String> itemList;

    public ItemListAdapter(List<String> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        /*
         * This method will be called whenever a ViewHolder should be populated
         * for a particular item. The data that the ViewHolder will show will be
         * retrieved from the underlying data model (list of strings in this
         * case).
         */
        holder.itemNameView.setText(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * ViewHolder pattern for better scrolling results
     */
    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameView;

        /**
         * Inflates new ListItemViewHolder instance with the item layout
         *
         * @param itemView
         */
        public ListItemViewHolder(View itemView) {
            super(itemView);
            itemNameView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }

}
