package adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olivine.cholodesh.R;

import callbacks.ProvideCallback;
import helpers.ViewAssist;
import listeners.TransportCostListener;
import model.Route;
import model.TransportProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Code Freak Tanveer on 17/02/2017.
 */

public class RouteListAdapter extends RecyclerView.Adapter<RouteViewHolder> {
    private Activity mContext;
    private TransportCostListener transportCostListener;
    private  Route[] routes;
    private ProvideCallback provideCallback;
    private int [] drawables=new int[]{R.drawable.icon_bus_white,R.drawable.icon_airoplane,R.drawable.icon_train,R.drawable.icon_ship};

    int lastPosition=-1;
    public RouteListAdapter(Activity mContext, Route[] routes){
        this.mContext=mContext;
        this.routes=routes;
        provideCallback=new ProvideCallback(mContext);
    }

    public void setTransportCostListener(TransportCostListener transportCostListener) {
        this.transportCostListener = transportCostListener;
        return ;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_route_list,parent,false);
        RouteViewHolder promotionViewHolder=new RouteViewHolder(view);
        return promotionViewHolder;
    }

    @Override
    public void onBindViewHolder(final RouteViewHolder holder, final int position) {
        setAnimation(holder.Container, position);
        final Route route=routes[position];
        holder.journeyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtView= holder.txtCost;
                ViewAssist.setDate(mContext,txtView,"Select Expected date");
            }
        });
        holder.txtRouteName.setText(route.getStartDistrictName()+" To "+route.getEndDistrictName());
        if(route.getTransportProvider()!=null){
            holder.txtTransport.setText(route.getTransportProvider().getTransportInfoOperatorName());
        }
        holder.changeTransportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provideCallback.getTransportProviders(route.getStartDistrictId(),route.getEndDistrictId()).enqueue(new Callback<TransportProvider[]>() {
                    @Override
                    public void onResponse(Call<TransportProvider[]> call, Response<TransportProvider[]> response) {
                        if(response.body().length==0){
                            Handler handler=new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "No available transport", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }

                        viewTransport(response.body(),holder,position);
                    }

                    @Override
                    public void onFailure(Call<TransportProvider[]> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return routes.length;
    }
    private void viewTransport(final TransportProvider []transportProviders, final RouteViewHolder holder, final int adapterPosition){
        TransportListAdapter transportListAdapter=new TransportListAdapter(mContext,transportProviders);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setIcon(R.drawable.icon_airoplane);
        builderSingle.setTitle("Select Transport");

        builderSingle.setAdapter(transportListAdapter, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TransportProvider transportProvider=transportProviders[i];
                routes[adapterPosition].setTransportProvider(transportProvider);
                holder.txtTransport.setText(transportProvider.getTransportInfoOperatorName());
                holder.txtCost.setText(transportProvider.getTransportInfoPrice()+"৳");
                String tranpostType=transportProvider.getTtName();
                if(tranpostType==null || tranpostType.length()<1){
                    tranpostType="NA";
                }
                switch (tranpostType.substring(0,1)){
                    case "B" :
                        holder.transportTypeView.setImageResource(drawables[0]);
                        holder.transportTypeView.setImageResource(drawables[0]);
                        break;
                    case "A":
                        holder.transportTypeView.setImageResource(drawables[1]);
                        holder.transportTypeView.setImageResource(drawables[1]);
                        break;
                    case "T":
                        holder.transportTypeView.setImageResource(drawables[2]);
                        holder.transportTypeView.setImageResource(drawables[2]);
                        break;
                    case "C":
                        holder.transportTypeView.setImageResource(drawables[3]);
                        holder.transportTypeView.setImageResource(drawables[3]);
                }
                transportCostListener.addTransportCost(transportProvider);
            }
        });
        builderSingle.show();

    }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.recyclerview_animation);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

class RouteViewHolder extends RecyclerView.ViewHolder {
    View Container;
    TextView txtRouteName;
    TextView txtTransport;
    TextView txtCost;
    ImageView transportTypeView;
//    selectable views
    TextView journeyDate;
    TextView transportOption;
    Button changeTransportButton;


    public RouteViewHolder(View itemView) {
        super(itemView);
        Container = itemView.findViewById(R.id.container);
        // Selectable veiw
        journeyDate= (TextView) itemView.findViewById(R.id.journeyDate);
        transportOption= (TextView) itemView.findViewById(R.id.transportOption);
        changeTransportButton= (Button) itemView.findViewById(R.id.changeTransportButton);
        // labels
        txtTransport= (TextView) itemView.findViewById(R.id.txtTransport);
        txtCost= (TextView) itemView.findViewById(R.id.txtCost);
        txtRouteName= (TextView) itemView.findViewById(R.id.txtRouteName);
        transportTypeView= (ImageView) itemView.findViewById(R.id.transportTypeView);

//        promotionHeadingText= (TextView) itemView.findViewById(R.id.promotionHeadingText);
//        promotionStartDate= (TextView) itemView.findViewById(R.id.promotionStartDate);
//        promotionEndDate= (TextView) itemView.findViewById(R.id.promotionEndDate);
//        promotionDetailsText= (TextView) itemView.findViewById(R.id.promotionDetailsText);
    }


}