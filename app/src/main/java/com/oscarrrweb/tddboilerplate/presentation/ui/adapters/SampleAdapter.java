package com.oscarrrweb.tddboilerplate.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.presentation.model.sample.GizmoUiModel;
import com.oscarrrweb.tddboilerplate.presentation.model.sample.WidgetUiModel;
import com.oscarrrweb.tddboilerplate.presentation.ui.components.WidgetItemView;
import com.oscarrrweb.tddboilerplate.presentation.ui.listeners.RecyclerViewClickListener;
import com.oscarrrweb.tddboilerplate.presentation.ui.utils.ViewUtils;
import com.oscarrrweb.tddboilerplate.presentation.views.MainView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements RecyclerViewClickListener {

    private List<GizmoUiModel> mModelList;
    private Context mContext;
    private MainView mView;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_gizmo_name)
        TextView mNameView;

        @BindView(R.id.tv_gizmo_description)
        TextView mDescriptionView;

        @BindView(R.id.widget_items)
        LinearLayout mWidgetItems;

        @BindView(R.id.btn_gizmo_menu)
        ImageButton mButton;

        private RecyclerViewClickListener mListener;

        public ViewHolder(View view, final RecyclerViewClickListener listener) {
            super(view);
            mListener = listener;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void setup(GizmoUiModel gizmo) {
            Context context = mNameView.getContext();
            mNameView.setText(gizmo.getName());
            mDescriptionView.setText(gizmo.getDescription());
            mWidgetItems.setVisibility(View.INVISIBLE);

            List<WidgetUiModel> widgets = gizmo.getWidgets();
            if (widgets != null) {
                for (WidgetUiModel item: widgets) {
                    WidgetItemView view = new WidgetItemView(context);
                    view.setup(item);
                    mWidgetItems.addView(view);
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (mWidgetItems.getVisibility() == View.VISIBLE) {
                mButton.setVisibility(View.INVISIBLE);
            } else {
                mButton.setVisibility(View.VISIBLE);
            }
            ViewUtils.toggleShow(mWidgetItems, 500);
            mListener.onClickView(getAdapterPosition());
        }
    }

    public SampleAdapter(MainView view, Context context) {
        mView = view;
        mContext = context;
        mModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_gizmo, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        GizmoUiModel item = mModelList.get(position);
        ((ViewHolder) viewHolder).setup(item);
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    @Override
    public void onClickView(int position) {
        notifyItemChanged(position);
    }

    public void addItems(@NonNull List<GizmoUiModel> modelList) {
        mModelList.clear();
        mModelList = modelList;
        notifyDataSetChanged();
    }
}
