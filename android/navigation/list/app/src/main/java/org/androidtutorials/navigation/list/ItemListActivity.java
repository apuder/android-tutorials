package org.androidtutorials.navigation.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This application is an example of how to use a RecyclerView. A RecyclerView
 * requires an adapter (ItemAdapter) that manages the underlying data model and
 * a layout manager (LinearLayoutManager) that defines how the RecyclerView will
 * arrange its content. The demo uses a floating action button to insert new
 * items. Clicking on an arbitrary item will delete it. Each item is rendered
 * using a custom layout.
 */
public class ItemListActivity extends AppCompatActivity implements
        ItemAdapter.ListItemClickListener {

    private List<String>         itemList;
    private int                  nextItemNumber;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * The data model is a simple list of strings. Each string will be
         * rendered in a separate item of the RecyclerView.
         */
        itemList = new ArrayList<>();
        nextItemNumber = 0;
        setContentView(R.layout.main_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /*
         * If it is known in advance that the physical size of all the items in
         * the RecyclerView are the same, setting this flag will improve
         * performance.
         */
        recyclerView.setHasFixedSize(true);

        /*
         * A LinearLayoutManager arranges the items of the RecyclerView
         * vertically.
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         * The ItemAdpater manages the data model (i.e., list of strings) for
         * the RecyclerView. See comments in class ItemAdapter for further
         * explanations.
         */
        adapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Whenever the user presses the floating action button, this
                 * callback will be invoked. With each click one more item will
                 * be added to the list. First the underlying data model will be
                 * changed and then the adapter is told what has changed. In
                 * this example items are always added at the top of the list to
                 * visualize the "insertion animation" (no animation happens
                 * when items are appended at the end).
                 */
                itemList.add(0, "Item #" + nextItemNumber++);
                adapter.notifyItemInserted(0);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        /*
         * The ItemAdapter will call this method whenever the user clicks an
         * item. The item that was clicked will get deleted (in this example
         * without further user confirmation). Removing is analog to insertion:
         * first the item is removed from the underlying data model, then the
         * adapter is told how the data has changed.
         */
        itemList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}