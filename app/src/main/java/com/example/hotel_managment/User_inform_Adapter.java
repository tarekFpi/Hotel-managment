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

public class User_inform_Adapter extends RecyclerView.Adapter<User_inform_Adapter.MyviewHolder> {
    private onItemClickItem clickItem;
    private Context context;
   private  List<Sign_upModel>user_informati;

    public User_inform_Adapter(Context context, List<Sign_upModel> user_informati) {
        this.context = context;
        this.user_informati = user_informati;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.user_information_layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
        Sign_upModel item_position=user_informati.get(position);
         myviewHolder.textView_userName.setText("User Naem:"+item_position.getUser_name());
         myviewHolder.textView_email.setText("Email:"+item_position.getEmail());
         myviewHolder.textView_phon.setText("phon:"+item_position.getPhon());
         myviewHolder.textView_password.setText("password:"+item_position.getPassword());
         myviewHolder.textView_gander.setText("Gender:"+item_position.getGender());
    }

    @Override
    public int getItemCount() {
        return user_informati.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener
    , MenuItem.OnMenuItemClickListener {
  private TextView textView_userName,textView_email,textView_password,textView_phon,textView_gander;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_userName=itemView.findViewById(R.id.text_user_name);
            textView_email=itemView.findViewById(R.id.text_emgil_id);
            textView_password=itemView.findViewById(R.id.text_pass);
            textView_phon=itemView.findViewById(R.id.text_phon);
            textView_gander=itemView.findViewById(R.id.text_gander);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
         int position =getAdapterPosition();
         clickItem.onClick(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
           MenuItem menuItem_onDelete=menu.add(Menu.NONE,1,1,"Delete");
           //MenuItem menuItem_onUpdate=menu.add(Menu.NONE,2,2,"Update");

         menuItem_onDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int item_position=getAdapterPosition();
            switch (item.getItemId()){
                case 1:
                    clickItem.onDelete(item_position);
                    break;
            }
            return true;
        }
    }
   public interface onItemClickItem{
        void onClick(int position);
       void onDelete(int position);
    }
    void setOnItemClickListener(onItemClickItem clickItem){
     this.clickItem=clickItem;
    }
}
