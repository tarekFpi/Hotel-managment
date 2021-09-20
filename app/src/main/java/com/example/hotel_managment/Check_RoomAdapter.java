package com.example.hotel_managment;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Check_RoomAdapter extends RecyclerView.Adapter<Check_RoomAdapter.MyViewHolder> {
    private static onItemClickListener ItemclickListener;
  private Context context;
  private List<Reservation_customarInfo>modleList;

    public Check_RoomAdapter(Context context, List<Reservation_customarInfo> modleList) {
        this.context = context;
        this.modleList = modleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.check_in_roomdetails,viewGroup,false);

     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Reservation_customarInfo item=modleList.get(position);
     myViewHolder.textViewRoom_Id.setText(item.getRes_RoomNo());
     myViewHolder.textViewRoom_type.setText(item.getRes_RoomType());
     myViewHolder.textViewBad_type.setText(item.getRes_BadType());
     myViewHolder.textView_Price.setText("Room Price:"+item.getRes_RoomPrice());
     myViewHolder.textViewCust_Name.setText("Name:"+item.getCut_name());
     myViewHolder.textViewcust_details.setText("Details:"+item.getCut_address());
     myViewHolder.textView_nadCard.setText("NID CARD:"+item.getCut_Nad());
     myViewHolder.textView_Phon.setText("phon Number:"+item.getCut_phon());

     myViewHolder.textView_Date.setText("Date:"+item.getDate());
     myViewHolder.textViewCheck_In.setText(item.getRoomCheekIn());

   Picasso.get().load(item.getRes_RoomImage()).fit().into(myViewHolder.imageView_checkIn);
    }

    @Override
    public int getItemCount() {
    return modleList.size();
    }

    public void filterList(List<Reservation_customarInfo> filterDataList) {
        modleList=filterDataList;
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
     private ImageView imageView_checkIn;
     private TextView textViewRoom_Id,textViewRoom_type,textViewBad_type,textView_Price,
             textViewCust_Name,textViewcust_details,textView_nadCard,textView_Phon,textView_Date,textViewCheck_In;

        public MyViewHolder(@NonNull View itemView) {  //ch_room_Image
            super(itemView);

            imageView_checkIn=itemView.findViewById(R.id.chin_roomImage);
            textViewRoom_Id=itemView.findViewById(R.id.check_inRoom_NO);
            textViewRoom_type=itemView.findViewById(R.id.Check_inRoomtype);
            textViewBad_type =itemView.findViewById(R.id.Check_inBadtype);
            textView_Price =itemView.findViewById(R.id.Check_in_price);
            textViewCust_Name =itemView.findViewById(R.id.Cust_name);
            textViewcust_details =itemView.findViewById(R.id.Cust_Deatils);

            textView_nadCard =itemView.findViewById(R.id.cust_NadCard);
            textView_Phon =itemView.findViewById(R.id.cust_phon);
            textView_Date =itemView.findViewById(R.id.cust_date);
            textViewCheck_In =itemView.findViewById(R.id.check_inId);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
           int position=getAdapterPosition();
            ItemclickListener.onItemClickListener(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
         MenuItem menu_Delete=menu.add(Menu.NONE,1,1,"Delete");
         MenuItem menu_Update=menu.add(Menu.NONE,2,2,"Update");
         menu_Delete.setOnMenuItemClickListener(this);
         menu_Update.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position=getAdapterPosition();
            switch (menuItem.getItemId()){
                case 1:
                    ItemclickListener.onDelete(position);
                    break;
                case 2:
                    ItemclickListener.onUpdate(position);
                    break;
            }
           return true;
        }
    }
    public interface onItemClickListener {
        void onItemClickListener(int position);
        void onUpdate(int position);
       void onDelete(int position);
    }
    void setOnItemClickListenter(onItemClickListener clickListenter ){
      this.ItemclickListener=clickListenter;
    }
}
