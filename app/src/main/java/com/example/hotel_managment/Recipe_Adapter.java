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

import com.squareup.picasso.Picasso;

import java.util.List;

public class Recipe_Adapter  extends RecyclerView.Adapter<Recipe_Adapter.MyviewHolder> {
   public static   onItemClickListener ItemClickListener;
    private Context context;
    private List<RecipeModel>recipeModelList;

    public Recipe_Adapter(Context context, List<RecipeModel> recipeModelList) {
        this.context = context;
        this.recipeModelList = recipeModelList;
    }


    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.recipe_show,viewGroup,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
    RecipeModel itemPosition=recipeModelList.get(position);
    /// myviewHolder.textView_recipeId.setText("Item No:"+itemPosition.getRecipe_id());
     myviewHolder.textViewName.setText("Name:"+itemPosition.getRecipe_Name());
     myviewHolder.textView_price.setText("price:"+itemPosition.getRecipe_price());
     myviewHolder.textView_quantity.setText("Quantity :"+itemPosition.getRecipe_quantity());
     myviewHolder.textView_Deatils.setText("Deatils :"+itemPosition.getRecipe_Details());
     //myviewHolder.textView_date.setText("Date:"+itemPosition.getRecipe_date());

   Picasso.get().load(itemPosition.getRecipe_image()).into(myviewHolder.imageView_recipe);
    }

    @Override
    public int getItemCount() {
  return recipeModelList.size();
    }

    public void filterdList(List<RecipeModel> filterList) {
        recipeModelList=filterList;
        notifyDataSetChanged();

    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
       private ImageView imageView_recipe;
       private TextView  textViewName,textView_Deatils,textView_quantity,textView_date,textView_price;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_recipe=itemView.findViewById(R.id.recip_showImage);
          //  textView_recipeId=itemView.findViewById(R.id.recipe_show_id);
            textViewName=itemView.findViewById(R.id.recipe_show_name);
            textView_Deatils=itemView.findViewById(R.id.recipe_show_deatils);
           textView_quantity=itemView.findViewById(R.id.recipe_quantity);
            textView_price=itemView.findViewById(R.id.recipe_showprice);
           // textView_date=itemView.findViewById(R.id.recipe_showDate);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
          ItemClickListener.onItemClick(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do your Action");
         MenuItem menu_Update=menu.add(Menu.NONE,1,1,"Update");
         MenuItem menuDelete=menu.add(Menu.NONE,2,2,"Delete");

         menu_Update.setOnMenuItemClickListener(this);
         menuDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
          int item_position=getAdapterPosition();
          switch (item.getItemId()){
              case 1:
                  ItemClickListener.onUpdate(item_position);
                  break;
              case 2:
                  ItemClickListener.onDelete(item_position);
                  break;
          }
            return true;
        }
    }
    public interface onItemClickListener{
        void onItemClick(int position);
        void onUpdate(int position);
        void onDelete(int position);
    }
    void setOnItemClickListener(onItemClickListener clickListener){
       this.ItemClickListener=clickListener;
    }
}
