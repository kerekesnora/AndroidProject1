package sapiadvertiser.sapiadvertiser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<ModelList> feedItemList;
    private ArrayList<ModelList> listSearch;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, ArrayList<ModelList> feedItemList) {
        listSearch=new ArrayList<>();
        this.feedItemList = feedItemList;
        this.mContext = context;
        for(ModelList e : feedItemList){
            if(e.getFlag()==1) {
                listSearch.add(e);
            }
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final ModelList feedItem = listSearch.get(i);


            customViewHolder.start.setText(feedItem.getStart());
            customViewHolder.date.setText(feedItem.getDate());
            customViewHolder.message.setText(feedItem.getMessage());
            customViewHolder.finish.setText(feedItem.getFinish());
            customViewHolder.clock.setText(feedItem.getClock());
            customViewHolder.phone.setText(feedItem.getPhone());
            int color = Color.argb(255, 255, 175, 64);
            if (i % 2 == 0) {
                customViewHolder.itemView.setBackgroundColor(color);

            }

            customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MapsActivity.class);
                    intent.putExtra("advert", feedItem);
                    intent.putExtra("index", i);
                    mContext.startActivity(intent);
                }
            });





    }
    public void setStringSearch(String search){
        listSearch.clear();
        if(search.equals("")){
            for (ModelList e : feedItemList) {
                if(e.getFlag()==1) {
                    listSearch.add(e);
                }
            }

        }else {
            for (ModelList e : feedItemList) {
                if (e.getStart().startsWith(search) && e.getFlag()==1) {
                    Log.d("addap",e.getStart());
                    listSearch.add(e);
                }
            }

        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView start;
        protected TextView date;
        protected TextView message;
        protected TextView finish;
        protected TextView clock;
        protected TextView phone;


        public CustomViewHolder(View view) {
            super(view);
            this.start = (TextView) view.findViewById(R.id.start);
            this.finish = (TextView) view.findViewById(R.id.stop);
            this.date = (TextView) view.findViewById(R.id.date);
            this.message = (TextView) view.findViewById(R.id.message);
            this.clock = (TextView) view.findViewById(R.id.clock);
            this.phone = (TextView) view.findViewById(R.id.phone);

        }
    }
}