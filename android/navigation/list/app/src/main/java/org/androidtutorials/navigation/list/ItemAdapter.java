package org.androidtutorials.navigation.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Class ItemAdapter manages the underlying data model and populates so-called
 * ViewHolder instances.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    interface ListItemClickListener {
        void onItemClicked(int position);
    }


    private List<String>          itemList;
    private ListItemClickListener listener;


    /**
     * A ViewHolder instance keeps references to all the views for an item. In
     * this example an item consists only of one TextView. Note that ViewHolders
     * are recycled and may represent different items of the RecyclerView over
     * time.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
         * Each data item is just a string
         */
        public TextView itemNameView;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                     * When an item is clicked by the user, a listener interface
                     * is called. Since a ViewHolder can represent different
                     * items over its lifetime, method getAdapterPosition() is
                     * used to determine the current position that this
                     * ViewHolder represents.
                     */
                    listener.onItemClicked(getAdapterPosition());
                }
            });
            itemNameView = (TextView) v.findViewById(R.id.item_name);
        }
    }


    public ItemAdapter(List<String> itemList, ListItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
         * Create a new ViewHolder by inflating a layout-resource.
         */
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
}