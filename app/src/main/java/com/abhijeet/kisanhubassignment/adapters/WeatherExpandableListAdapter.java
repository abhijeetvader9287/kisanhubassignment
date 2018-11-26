package com.abhijeet.kisanhubassignment.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.abhijeet.kisanhubassignment.R;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import java.util.List;
import java.util.Map;
/**
 * The type Weather expandable list adapter.
 */
public class WeatherExpandableListAdapter extends BaseExpandableListAdapter {
    /**
     * The Expandable list title.
     */
    final List<Integer> expandableListTitle;
    /**
     * The Expandable list detail.
     */
    final Map<Integer, List<WeatherModel>> expandableListDetail;
    /**
     * The Layout inflater.
     */
    final LayoutInflater layoutInflater;
    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new Weather expandable list adapter.
     *
     * @param context              the context
     * @param expandableListTitle  the expandable list title
     * @param expandableListDetail the expandable list detail
     */
    public WeatherExpandableListAdapter(Context context, List<Integer> expandableListTitle,
                                        Map<Integer, List<WeatherModel>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandableListDetail.get(expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        //final String expandedListText =  getChild(listPosition, expandedListPosition)+"";
        String[] months = context.getResources().getStringArray(R.array.months);
        final WeatherModel weatherModel = (WeatherModel) getChild(listPosition, expandedListPosition);
        View view = layoutInflater.inflate(R.layout.child_view, null);
        TextView txtMonth = view.findViewById(R.id.txtMonth);
        TextView txtValue = view.findViewById(R.id.txtValue);
        txtMonth.setText(months[weatherModel.getMonth() - 1]);
        txtValue.setText(weatherModel.getValue() + "");
        return view;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (expandableListDetail.get(expandableListTitle.get(listPosition)) != null) {
            return expandableListDetail.get(expandableListTitle.get(listPosition))
                    .size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.header_view, null);
        TextView txtYear = view.findViewById(R.id.txtYear);
        txtYear.setText(getGroup(listPosition).toString());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
