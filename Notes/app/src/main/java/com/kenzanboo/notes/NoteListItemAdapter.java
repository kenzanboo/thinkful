package com.kenzanboo.notes;

/**
 * Created by kenzanboo on 6/9/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {
    static final int MIN_DISTANCE = 150;
    private float swipex1,swipex2;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<NoteListItem> mNoteListItems;
    private NoteDAO dao;

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        dao = new NoteDAO(context);
        mNoteListItems = dao.list();
    }
    public NoteListItemAdapter updateNoteList() {
        mNoteListItems = dao.list();
        return this;
    }

    @Override
    public NoteListItemAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                NoteListItem noteListItem = mNoteListItems.get(mRecyclerView.getChildPosition(v));
                removeItem(mRecyclerView.getChildPosition(v));
                Intent intent = new Intent(mContext, EditNoteActivity.class);
                intent.putExtra("Note", noteListItem);

                ((Activity)mContext).startActivityForResult(intent, 1);

                return true;
            }
        });
//        v.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent motionEvent) {
//                if (NoteListItemAdapter.this.detectSwipeRight(motionEvent)) {
//                    NoteListItemAdapter.this.removeItem(viewGroup.indexOfChild(v));
//                }
//                return true;
//            }
//        });

        return new ViewHolder(v);
    }
    /*
    public void editItem(NoteListItem note) {
        Intent intent = new Intent(mContext, EditNoteActivity.class);
        intent.putExtra("Note", note);
        Activity activity = (Activity) mContext;
        activity.startActivity(intent);
    }
    */
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.hasExtra("Note")) {
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        }
    }
    */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.setText(noteListItem.getText());
    }

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    }

    public void addItem(NoteListItem item) {
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
    }
    public void removeItem(int position) {
        mNoteListItems.remove(position);
        notifyItemRemoved(position);
    }

    /*
     action_up should be gauranteed to be prefaced by an action up
     */
    private boolean detectSwipeRight(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                swipex1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                swipex2 = event.getX();
                float deltaX = swipex2 - swipex1;
                return (Math.abs(deltaX) > MIN_DISTANCE);
        }
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }
}