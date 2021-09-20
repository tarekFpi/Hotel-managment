package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recipe_payment_Adapter extends RecyclerView.Adapter<Recipe_payment_Adapter.MyviewHolder>  {
    private static  ItemClickListener clickListener;
  private Context context;

  private List<Recipe_payment_Modle> recipe_payment_modleList;

    public Recipe_payment_Adapter(Context context, List<Recipe_payment_Modle> recipe_payment_modleList) {
        this.context = context;
        this.recipe_payment_modleList = recipe_payment_modleList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.recipe_payment_deatils,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

     Recipe_payment_Modle item_position=recipe_payment_modleList.get(position);

        String m[]=item_position.getUser_allOrder().split("/");
        StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
            sb.append(j+".Order No:"+m[i]+".\n");
        }

     myviewHolder.textView_roomId.setText(item_position.getRoom_id());
     myviewHolder.textView_custName.setText("Customar Name:"+item_position.getCustom_name());
     myviewHolder.textView_custPhon.setText("Phone Number:"+item_position.getCustom_Phon());
     myviewHolder.textView_delivaryDate.setText(item_position.getRecipe_delivayDate());
     myviewHolder.textView_recipe_totalPrice.setText("alrady pay:"+item_position.getToal_price());
        myviewHolder.textView_recipe_orderItem.setText(sb.toString());
        Picasso.get().load(item_position.getCustom_image()).into(myviewHolder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return recipe_payment_modleList.size();
    }

    public void filter_setData(List<Recipe_payment_Modle> filter_list) {
        recipe_payment_modleList=filter_list;
        notifyDataSetChanged();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
     private TextView textView_roomId,textView_custName,textView_custPhon,textView_recipe_orderItem,
             textView_recipe_totalPrice,textView_delivaryDate;
     private CircleImageView circleImageView;

     public MyviewHolder(@NonNull View itemView) {
         super(itemView);

         circleImageView=itemView.findViewById(R.id.pay_deatils_custImage);
          textView_roomId=itemView.findViewById(R.id.pay_deatils_roomId);
         textView_custName=itemView.findViewById(R.id.pay_deatils_recipeCustName);
         textView_custPhon=itemView.findViewById(R.id.pay_details_Cust_phon);
        // textView_recipe_id=itemView.findViewById(R.id.pay_deatils_recipe_Id);
        // textView_recipe_name=itemView.findViewById(R.id.pay_deatils_recipe_Name);
        // textView_recipeQuantity=itemView.findViewById(R.id.pay_deatils_recipe_quantity);
         textView_recipe_totalPrice=itemView.findViewById(R.id.pay_deatils_recipe_total);
         textView_delivaryDate=itemView.findViewById(R.id.recipe_deails_delivary_date);
         textView_recipe_orderItem=itemView.findViewById(R.id.order_item);

         itemView.setOnCreateContextMenuListener(this);
         itemView.setOnClickListener(this);
     }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int item_position=getAdapterPosition();
            switch (menuItem.getItemId()){
                case 1:
                    clickListener.OnItemClickListener(item_position);
                    break;

                case 2:
                    clickListener.OnDelete(item_position);
                    break;
            }
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do your Any Action..");
            MenuItem anyAction=menu.add(Menu.NONE,1,1,"Any Action");
            MenuItem Delete_data=menu.add(Menu.NONE,2,2,"Delete");

            anyAction.setOnMenuItemClickListener(this);
            Delete_data.setOnMenuItemClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getItemCount();
            clickListener.OnItemClickListener(position);
        }
    }

    public interface ItemClickListener{
        void OnItemClickListener(int position);
        void OnDelete(int position);
    }
    void setOnItemClickListener(ItemClickListener clickListener){
        this.clickListener=clickListener;
    }
}
