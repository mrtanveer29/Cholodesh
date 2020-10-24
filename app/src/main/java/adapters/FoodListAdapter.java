/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olivine.cholodesh.R;
import com.squareup.picasso.Picasso;

import helpers.BaseURL;
import model.Food;

/**
 * Created by Olivine on 6/11/2017.
 */

public class FoodListAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    private Context mContext;
    private Food[] foods;

    public FoodListAdapter(Context mContext, Food[] foods) {
        this.mContext = mContext;
        this.foods = foods;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_food_list,parent,false);
        FoodViewHolder foodViewHolder=new FoodViewHolder(view);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food=foods[position];
        holder.foodProviderName.setText(food.getDestinationFoodServiceProviderName());
        holder.foodProviderType.setText(food.getFoodCategoryName());
        holder.foodProviderAddress.setText(food.getDestinationFoodServiceProviderAddress());
        holder.foodProviderPrice.setText(food.getDestinationFoodServiceProviderPriceRange()+"৳");
        Picasso.with(mContext).load(BaseURL.FOOD_IMAGE_BASE_URL+food.getDestinationFoodServiceProviderImage()).into(holder.foodProviderImage);

    }

    @Override
    public int getItemCount() {
        return foods.length;
    }
}
class FoodViewHolder extends RecyclerView.ViewHolder{
    ImageView foodProviderImage;
    TextView foodProviderName;
    TextView foodProviderType;
    TextView foodProviderAddress;
    TextView foodProviderPrice;
    public FoodViewHolder(View itemView) {
        super(itemView);
        foodProviderImage= (ImageView) itemView.findViewById(R.id.foodProviderImage);
        foodProviderName= (TextView) itemView.findViewById(R.id.foodProviderName);
        foodProviderType= (TextView) itemView.findViewById(R.id.foodProviderType);
        foodProviderAddress= (TextView) itemView.findViewById(R.id.foodProviderAddress);
        foodProviderPrice= (TextView) itemView.findViewById(R.id.foodProviderPrice);
    }
}
