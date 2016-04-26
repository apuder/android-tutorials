package org.androidtutorials.clicksupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> itemList;
    private int nextItemNumber;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = new ArrayList<>();
        nextItemNumber = 0;

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
        adapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
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

        // set the onclicks onRecyclerView
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }
}
