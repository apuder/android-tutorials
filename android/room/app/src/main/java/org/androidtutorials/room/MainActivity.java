package org.androidtutorials.room;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import org.androidtutorials.room.room.R;

public class MainActivity extends AppCompatActivity {

    private MessageViewModel mMessageViewModel;
    private LifecycleOwner lifecycleOwner;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MessageListAdapter adapter = new MessageListAdapter(this);
        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mMessageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> messages) {
                // Update the cached copy of the messages in the adapter.
                adapter.setMessages(messages);
            }
        });

//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

        private final LayoutInflater mInflater;
        private List<Message> mMessages; // Cached copy of messages

        MessageListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        class MessageViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title) TextView tvTitle;
            private Message message;
            private int position;

            private MessageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(int position, Message item) {
                message = item;
                tvTitle.setText(message.getTitle());
                this.position = position;

            }

        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new MessageListAdapter.MessageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {

            if (mMessages != null) {
                Message current = mMessages.get(position);
                if (holder.tvTitle != null) {
                    holder.tvTitle.setText(current.getTitle());
                }

                holder.bind(position, current);
            } else {
                // Covers the case of data not being ready yet.
                holder.tvTitle.setText("No Message");
            }
        }

        void setMessages(List<Message> messages){
            mMessages = messages;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mMessages has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mMessages != null)
                return mMessages.size();
            else return 0;
        }
    }
}
