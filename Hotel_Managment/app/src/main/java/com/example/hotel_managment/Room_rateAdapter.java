package com.example.hotel_managment;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Room_rateAdapter extends RecyclerView.Adapter<Room_rateAdapter.MyViewHolder> {
    private static OnItemClickListener ItemClickListener;
  private Context context;
  private List<Room_addModel>roomlist;

    public Room_rateAdapter(Context context, List<Room_addModel> roomlist) {
        this.context = context;
        this.roomlist = roomlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view= layoutInflater.inflate(R.layout.room_rate_layout,viewGroup,false);
     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Room_addModel item=roomlist.get(position);
    myViewHolder.textViewbadType.setText(item.getBadType());
    myViewHolder.textViewprice.setText("Rate:"+item.getRoom_price());
    myViewHolder.textViewRoomtype.setText("Room:"+item.getRoomType().trim());
    myViewHolder.textViewCheekOut.setText("Cheeck Out");
   // myViewHolder.t.setText(item.getRoomType());
   Picasso.get().load(item.getRoom_image()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
    return roomlist.size();
    }

    public void filterdList(List<Room_addModel> filterList) {
    roomlist=filterList;
    notifyDataSetChanged();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener  {
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
           itemView.setOnCreateContextMenuListener(this);
       }

        @Override
        public void onClick(View v) {
       int position=getAdapterPosition();
    ItemClickListener.onItemClickListener(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
           menu.setHeaderTitle("Doa Any Action");
         MenuItem any_task=menu.add(menu.NONE,1,1,"Do Any Task");
         MenuItem DeleteTask=menu.add(menu.NONE,2,2,"Delete");
         MenuItem onUpdate=menu.add(menu.NONE,3,3,"Update");

         any_task.setOnMenuItemClickListener(this);
         DeleteTask.setOnMenuItemClickListener(this);
         onUpdate.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
          int item_position= getAdapterPosition();
           switch (item.getItemId()){
               case 1:
                   ItemClickListener.doAnytask(item_position);
                   break;
               case 2:
                   ItemClickListener.OnDelete(item_position);
                   break;

               case 3:
                   ItemClickListener.onUpdate(item_position);
                   break;
           }
            return true;
        }
    }

   public interface OnItemClickListener{

           void onItemClickListener(int position);
           void doAnytask(int position);
           void OnDelete(int position);
           void onUpdate(int position);

   }
   public void setOnItemClickListener(OnItemClickListener clickListener){
      this.ItemClickListener=clickListener;
   }
}
