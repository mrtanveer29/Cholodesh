/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olivine.cholodesh.PackageActivity;
import com.example.olivine.cholodesh.PackageDetailsActivity;
import com.example.olivine.cholodesh.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import constants.Travel;
import helpers.BaseURL;
import model.Package;

/**
 * Created by Olivine on 6/12/2017.
 */

public class PackageAdapter extends BaseAdapter {
    private Context mContext;
    private Package [] packages;

    public PackageAdapter(Context mContext, Package[] packages) {
        this.mContext = mContext;
        this.packages = packages;
    }

    @Override
    public int getCount() {
        return packages.length;
    }

    @Override
    public Package getItem(int i) {
        return packages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_package_list,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.packageName= (TextView) convertView.findViewById(R.id.packageName);
            viewHolder.tourPlace= (TextView) convertView.findViewById(R.id.tourPlace);
            viewHolder.operatorName= (TextView) convertView.findViewById(R.id.operatorName);
            viewHolder.destination= (TextView) convertView.findViewById(R.id.destination);
            viewHolder.dayNight= (TextView) convertView.findViewById(R.id.dayNight);
            viewHolder.packageCost= (TextView) convertView.findViewById(R.id.packageCost);
            viewHolder.packageParent= (RelativeLayout) convertView.findViewById(R.id.packageParent);
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Package tourPackage=packages[i];
        viewHolder.packageName.setText(tourPackage.getPackageName());
        viewHolder.tourPlace.setText("Dhaka"+" To "+tourPackage.getDestinationName());
        viewHolder.destination.setText("In "+tourPackage.getDestinationName());
        viewHolder.dayNight.setText(tourPackage.getPackageDay()+" Days "+tourPackage.getPackageNight()+" Night");
        viewHolder.packageCost.setText(tourPackage.getPackagePrice()+"৳");
        viewHolder.operatorName.setText(tourPackage.getProviderName());
        String url= BaseURL.PACKAGE_IMAGE_BASE_URL+tourPackage.getPackageGalleryImage();
        try {
            Bitmap bitmap=new RetriveImage().execute(url).get();
            BitmapDrawable background = new BitmapDrawable(bitmap);
        viewHolder.packageParent.setBackgroundDrawable(background);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        viewHolder.packageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent packageDetailsIntent=new Intent(mContext,PackageDetailsActivity.class);
                packageDetailsIntent.putExtra(Travel.PACKAGE_KEY,tourPackage.getPackageId());
                packageDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(packageDetailsIntent);
            }
        });
//



        return convertView;
    }
    private class RetriveImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        public Bitmap doInBackground(String... strings) {
            URL url = null;
            InputStream is=null;
            try {
                url = new URL(strings[0]);
                is=url.openConnection().getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bmImg = BitmapFactory.decodeStream(is);
            return bmImg;
        }
    }

    class ViewHolder{
        TextView packageName;
        TextView tourPlace;
        TextView operatorName;
        TextView destination;
        TextView dayNight;
        TextView packageCost;
        RelativeLayout packageParent;
    }
}
