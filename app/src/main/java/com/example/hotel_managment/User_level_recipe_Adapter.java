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

public class User_level_recipe_Adapter extends RecyclerView.Adapter<User_level_recipe_Adapter.MyviewHolder>{
    public static  onItemClickListener ItemClickListener;
    private Context context;
    private List<RecipeModel> User_recipeModelList;

    public User_level_recipe_Adapter(Context context, List<RecipeModel> recipeModelList) {
        this.context = context;
        this.User_recipeModelList = recipeModelList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.recipe_show,viewGroup,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
        RecipeModel itemPosition=User_recipeModelList.get(position);
        myviewHolder.textViewName.setText("Name:"+itemPosition.getRecipe_Name());
        myviewHolder.textView_price.setText("price:"+itemPosition.getRecipe_price());
        myviewHolder.textView_Deatils.setText("Deatils :"+itemPosition.getRecipe_Details());

        Picasso.get().load(itemPosition.getRecipe_image()).into(myviewHolder.imageView_recipe);
    }

    @Override
    public int getItemCount() {
        return User_recipeModelList.size();
    }

    public void filterdList(List<RecipeModel> filterList) {
        User_recipeModelList=filterList;
        notifyDataSetChanged();
    }

    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView_recipe;
        private TextView textViewName,textView_Deatils,textView_quantity,textView_date,textView_price;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_recipe=itemView.findViewById(R.id.recip_showImage);

            textViewName=itemView.findViewById(R.id.recipe_show_name);
            textView_Deatils=itemView.findViewById(R.id.recipe_show_deatils);
            textView_price=itemView.findViewById(R.id.recipe_showprice);

            itemView.setOnClickListener(this);

           }

        @Override
        public void onClick(View view) {
            int position =getAdapterPosition();
            ItemClickListener.onItemClick(position);
        }
    }
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    void setOnItemClickListener(onItemClickListener clickListener){
        this.ItemClickListener=clickListener;
    }
}
