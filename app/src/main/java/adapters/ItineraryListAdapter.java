package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olivine.cholodesh.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Itenerary;

/**
 * Created by Olivine on 5/10/2017.
 */

public class ItineraryListAdapter extends RecyclerView.Adapter<ItinerayViewHolder> {
    private Context mContext;
    private ArrayList<Itenerary> iteneraryList;

    public ItineraryListAdapter(Context mContext, ArrayList<Itenerary> iteneraryList) {
        this.mContext = mContext;
        this.iteneraryList = iteneraryList;
    }
    
    @Override
    public ItinerayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_itinerary_list,parent,false);
        ItinerayViewHolder holder=new ItinerayViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItinerayViewHolder holder, int position) {
        Itenerary itenerary=iteneraryList.get(position);
        holder.dayplan.setText(itenerary.getDayplan());
        holder.placeName.setText(itenerary.getPlaceName());
        holder.costPerperson.setText(itenerary.getPerPersonCost()+"৳");
        holder.time.setText(itenerary.getTime());
    }

    @Override
    public int getItemCount() {
        return iteneraryList.size();
    }
}
class ItinerayViewHolder extends RecyclerView.ViewHolder{
    TextView dayplan;
    TextView placeName;
    TextView costPerperson;
    TextView time;


    public ItinerayViewHolder(View itemView) {
        super(itemView);
        dayplan= (TextView) itemView.findViewById(R.id.dayplan);
        placeName= (TextView) itemView.findViewById(R.id.placeName);
        costPerperson= (TextView) itemView.findViewById(R.id.costPerperson);
        time= (TextView) itemView.findViewById(R.id.time);
    }
}