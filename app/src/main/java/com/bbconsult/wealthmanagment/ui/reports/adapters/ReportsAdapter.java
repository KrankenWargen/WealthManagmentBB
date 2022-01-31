package com.bbconsult.wealthmanagment.ui.reports.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbconsult.wealthmanagment.R;
import com.bbconsult.wealthmanagment.domain.models.PackageDirectory;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final ArrayList<String> reportPackage2 = new ArrayList<>();
    private final String parent;
    private final FragmentManager fragmentManager;
    ArrayList<PackageDirectory> listViewParentItems;

    public ReportsAdapter(Context context, ArrayList<PackageDirectory> listViewParentItems, FragmentManager fragmentManager, String parent) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.listViewParentItems = listViewParentItems;
        this.fragmentManager = fragmentManager;
        this.parent = parent;
    }

    private int getReportIcon(PackageDirectory packageDirectory) {

        String firstComponent = packageDirectory.getFirstComponent();
        if (firstComponent.equals("table")) {
            return R.drawable.table_dashboard;
        } else if (firstComponent.equals("chart"))
            return R.drawable.chart_dashboard;
        else if (firstComponent.equals("text"))
            return R.drawable.text_dashboard;
        else
            return R.drawable.folder;
    }

    private int getReportarrow(String firstComponent) {

        if (firstComponent != null) {
            if (firstComponent.equals("item"))
                return R.drawable.arrow_bottom;
            else if (firstComponent.equals("category"))
                return R.drawable.arrow_right;
        }

        return R.drawable.ic_dashboard_selected_24dp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workflow_listview_cell_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textLabel.setText(listViewParentItems.get(holder.getAdapterPosition()).getName());
        if (listViewParentItems.get(holder.getAdapterPosition()).getType().equals("category"))
            holder.textLabel.setTypeface(null, Typeface.BOLD);
        holder.textLabel.setTextColor(Color.parseColor("black"));

        holder.menuIcon.setImageResource(getReportIcon(listViewParentItems.get(holder.getAdapterPosition())));
        holder.arrow.setImageResource(getReportarrow(listViewParentItems.get(holder.getAdapterPosition()).getType()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listViewParentItems.get(holder.getAdapterPosition()).getType().equals("item")) {
                    String reportName = parent.substring(1) + "/" + listViewParentItems.get(holder.getAdapterPosition()).getName();
//                    EventBus.getDefault().post(new ReportToLoadEvent(true, reportName));
                } else {
//                    ReportsMenuRecycleViewFragment reportsMenuRecycleViewFragment = new ReportsMenuRecycleViewFragment(parent + "/" + listViewParentItems.get(holder.getAdapterPosition()).getName(),
//                            listViewParentItems.get(holder.getAdapterPosition()).getChildItemList());

//                    EventBus.getDefault().post(new ReportsMenuNewRecycleViewEvent(reportsMenuRecycleViewFragment));
//                    EventBus.getDefault().post(new PackageDirectoryPressedEvent(listViewParentItems.get(holder.getAdapterPosition()).getName(), parent));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return listViewParentItems.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        TextView textLabel;
        ImageView menuIcon;
        ImageView arrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.textView);
            menuIcon = itemView.findViewById(R.id.menuIcon);
            arrow = itemView.findViewById(R.id.arrow);
        }
    }
}
