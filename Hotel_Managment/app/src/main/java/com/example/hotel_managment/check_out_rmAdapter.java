package com.example.hotel_managment;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class check_out_rmAdapter extends RecyclerView.Adapter<check_out_rmAdapter.MyViewHolder>{
   private  static onItemClickListener listener;
  private Context context;
  private List<check_out_rmModel>modelList;

    public check_out_rmAdapter(Context context, List<check_out_rmModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater=LayoutInflater.from(context);
   View view= layoutInflater.inflate(R.layout.check_out_room_deatils,parent,false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
      check_out_rmModel item_positiion=modelList.get(position);
       myViewHolder.textView_roomNo.setText(item_positiion.getRoom_id());
       myViewHolder.textView_badType.setText(item_positiion.getBadType());
       myViewHolder.textView_roomType.setText(item_positiion.getRoom_type());
       myViewHolder.textView_roomPric.setText("Room price:"+item_positiion.getRoom_price());

       myViewHolder.textView_custName.setText("Customar Name:"+item_positiion.getCustom_name());
       myViewHolder.textView_deatils.setText("Customar Address:"+item_positiion.getCustom_deatils());
       myViewHolder.textView_phon.setText("Phon:"+item_positiion.getCust_phon());
       myViewHolder.textView_nid.setText("NID Number:"+item_positiion.getNid_card());
       myViewHolder.textView_checkIn_date.setText("Check In:"+item_positiion.getCheck_InDate());
       myViewHolder.textView_check_outDate.setText("Check Out:"+item_positiion.getCheck_outDate());
       myViewHolder.textView_total_day.setText(item_positiion.getTotal_day());
       myViewHolder.textView_Total_room_Price.setText("Total Amount:"+item_positiion.getTotal_room_price());

    Picasso.get().load(item_positiion.getCustom_image()).into(myViewHolder.circleImageView);
  }

  @Override
  public int getItemCount() {
    return modelList.size();
  }

    public void filter_chingData(List<check_out_rmModel> filterList_data) {
        modelList=filterList_data;
       notifyDataSetChanged();
    }



    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
      private CircleImageView circleImageView;
  private TextView textView_roomNo,textView_roomType,textView_badType,textView_roomPric,textView_custName,textView_nid,
    textView_deatils,textView_phon,textView_checkIn_date,textView_check_outDate,textView_total_day,textView_Total_room_Price;
    public MyViewHolder(@NonNull View itemView) {
    super(itemView);

    circleImageView=itemView.findViewById(R.id.CheckOut_show_cut_Image);
    textView_roomNo=itemView.findViewById(R.id.checkOut_show_Room_no);
    textView_roomType=itemView.findViewById(R.id.checkOut_show_Roomtype);
     textView_badType=itemView.findViewById(R.id.checkOut_show_Badtype);
      textView_roomPric=itemView.findViewById(R.id.checkOut_show_price);

      textView_custName=itemView.findViewById(R.id.checkOut_show_Cust_name);
     textView_deatils=itemView.findViewById(R.id.CheckOut_show_Cust_Deatils);
      textView_nid=itemView.findViewById(R.id.checkOut_show_Nid);
      textView_phon=itemView.findViewById(R.id.checkOut_show_phon);
      textView_checkIn_date=itemView.findViewById(R.id.check_in_show_Date);

        textView_check_outDate=itemView.findViewById(R.id.check_out_show_Date);
        textView_total_day=itemView.findViewById(R.id.check_out_totalDay);
        textView_Total_room_Price=itemView.findViewById(R.id.check_outTotal_show_Price);

        itemView.setOnClickListener(this);
       itemView.setOnCreateContextMenuListener(this);
    }

      @Override
      public void onClick(View view) {
            int position=getAdapterPosition();
           listener.onItemClick(position);
      }

      @Override
      public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
          menu.setHeaderTitle("Do your Action");
          MenuItem menu_Update=menu.add(Menu.NONE,1,1,"Update");
          MenuItem menu_Delete=menu.add(Menu.NONE,2,2,"Delete");

          menu_Update.setOnMenuItemClickListener(this);
          menu_Delete.setOnMenuItemClickListener(this);
      }

      @Override
      public boolean onMenuItemClick(MenuItem item) {
          int menu_position=getAdapterPosition();

          switch (item.getItemId()){
              case 1:
                  listener.OnUpdate(menu_position);
                  break;
              case 2:
                  listener.OnDelete(menu_position);
                  break;
          }
          return true;
      }
  }

  public interface onItemClickListener{
       void onItemClick(int position);
        void OnDelete(int position);
        void OnUpdate(int position);
  }
    void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
