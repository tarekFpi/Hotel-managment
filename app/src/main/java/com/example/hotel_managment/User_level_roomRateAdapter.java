package com.example.hotel_managment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class User_level_roomRateAdapter extends RecyclerView.Adapter<User_level_roomRateAdapter.MyViewHolder> {
    private static OnItemClickListener ItemClickListener;
    private Context context;
    private List<Room_addModel> Usr_roomlist;

    public User_level_roomRateAdapter(Context context, List<Room_addModel> roomlist) {
        this.context = context;
        this.Usr_roomlist = roomlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.room_rate_layout,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Room_addModel item=Usr_roomlist.get(position);
        myViewHolder.textViewbadType.setText(item.getBadType());
        myViewHolder.textViewprice.setText("Rate:"+item.getRoom_price());
        myViewHolder.textViewRoomtype.setText("Room:"+item.getRoomType().trim());
        myViewHolder.textViewCheekOut.setText("Cheeck Out");
        // myViewHolder.t.setText(item.getRoomType());
        Picasso.get().load(item.getRoom_image()).into(myViewHolder.imageView);
    }
    @Override
    public int getItemCount() {
        return Usr_roomlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {
        private ImageView imageView;
        private TextView textViewbadType,textViewRoomtype,textViewCheekOut,textViewprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView= itemView.findViewById(R.id.room_rateImage);
            textViewbadType=itemView.findViewById(R.id.text_badType);
            textViewRoomtype=itemView.findViewById(R.id.text_roomtype);
            textViewCheekOut=itemView.findViewById(R.id.text_cheeckOut);
            textViewprice=itemView.findViewById(R.id.text_room_rate);
            itemView.setOnClickListener(this);
        }
   @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            ItemClickListener.onClickListener(position);
        }
    }

    public interface OnItemClickListener{

   void onClickListener(int position);
     }
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.ItemClickListener=clickListener;
    }
}
