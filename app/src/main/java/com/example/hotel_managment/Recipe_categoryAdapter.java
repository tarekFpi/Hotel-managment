package com.example.hotel_managment;

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

import java.util.List;

public class Recipe_categoryAdapter extends RecyclerView.Adapter<Recipe_categoryAdapter.MyViewHolder> {
     private static ItemClickListener clickListener;
    private Context context;
    private List<Recipe_categoryModel>recipe_categoryModelList;

    public Recipe_categoryAdapter(Context context, List<Recipe_categoryModel> recipe_categoryModelList) {
        this.context = context;
        this.recipe_categoryModelList = recipe_categoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
     View view=  layoutInflater.inflate(R.layout.recipe_category_item,parent,false);
     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
       Recipe_categoryModel myItem_position= recipe_categoryModelList.get(position);

    myViewHolder.recipe_id.setText(myItem_position.getRecipe_id());
   myViewHolder.textView_roomId.setText(""+myItem_position.getUser_room_id());
    myViewHolder.textView_reicpeName.setText(myItem_position.getRecipe_Name());
    myViewHolder.textView_totalPrice.setText("Total:"+myItem_position.getRecipe_totalPrice());
    myViewHolder.textView_expDate.setText(myItem_position.getRecipe_expDate());
    myViewHolder.textView_recipe_quantity.setText("Quantity:"+myItem_position.getRecipe_quantity());

    }

    @Override
    public int getItemCount() {
   return recipe_categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    private TextView recipe_id,textView_reicpeName,textView_recipe_quantity,
            textView_expDate,textView_totalPrice,textView_roomId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_id=itemView.findViewById(R.id.recipe_cate_id);
            textView_reicpeName=itemView.findViewById(R.id.recipe_cate_name);
            textView_recipe_quantity=itemView.findViewById(R.id.recipe_cate_quantity);
            textView_expDate=itemView.findViewById(R.id.recipe_cate_date);
            textView_totalPrice=itemView.findViewById(R.id.recipe_cate_total);
            textView_roomId=itemView.findViewById(R.id.user_room_id);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position=getAdapterPosition();
           // clickListener.OnDelete(position);
            clickListener.OnItemClickListener(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("Do your Any Action..");
          MenuItem anyAction=menu.add(Menu.NONE,1,1,"Any Action");
          MenuItem Delete_data=menu.add(Menu.NONE,2,2,"Delete");

          anyAction.setOnMenuItemClickListener(this);
          Delete_data.setOnMenuItemClickListener(this);
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
    }
    public interface ItemClickListener{
     void OnItemClickListener(int Position);
     void OnDelete(int position);
    }
    void setOnItemClickListener(ItemClickListener clickListener){
      this.clickListener=clickListener;
    }
}
