package com.oscarrrweb.tddboilerplate.presentation.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.presentation.model.sample.DoodadUiModel;
import com.oscarrrweb.tddboilerplate.presentation.model.sample.WidgetUiModel;
import com.oscarrrweb.tddboilerplate.presentation.ui.utils.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class WidgetItemView extends RelativeLayout implements View.OnClickListener {

    @BindView(R.id.tv_widget_name)
    TextView mNameView;

    @BindView(R.id.tv_widget_description)
    TextView mDescriptionView;

    @BindView(R.id.doodad_items)
    LinearLayout mDoodadItems;

    @BindView(R.id.btn_widget_menu)
    ImageButton mButton;

    private Context mContext;

    private WidgetUiModel mModel;


    public WidgetItemView(Context context) {
        super(context);
        mContext = context;
    }

    public WidgetItemView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
    }

    public WidgetItemView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        mContext = context;
    }

    public void setup(WidgetUiModel model) {
        mModel = model;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            Timber.e("LayoutInflater null value");
            return;
        }

        View widgetView = inflater.inflate(R.layout.view_widget, this);
        ButterKnife.bind(this, widgetView);
        mNameView.setText(mModel.getName());
        mDescriptionView.setText(mModel.getDescription());
        mDoodadItems.setVisibility(View.INVISIBLE);

        List<DoodadUiModel> doodads = mModel.getDoodads();
        if (doodads != null) {
            for (int i = 0; i < doodads.size(); i++) {
                DoodadUiModel item = doodads.get(i);
                RelativeLayout layout = new RelativeLayout(mContext);
                View view = inflater.inflate(R.layout.view_doodad, layout);
                ((TextView)view.findViewById(R.id.tv_doodad_name)).setText(item.getName());
                ((TextView)view.findViewById(R.id.tv_doodad_description)).setText(item.getDescription());
                mDoodadItems.addView(view);
            }
        }
    }

    @Override
    public void onClick(View v) {
        List<DoodadUiModel> doodads = mModel.getDoodads();
        if (doodads == null || doodads.size() == 0) {
            return;
        }

        if (mDoodadItems.getVisibility() == View.VISIBLE) {
            mButton.setVisibility(View.INVISIBLE);
        } else {
            mButton.setVisibility(View.VISIBLE);
        }
        ViewUtils.toggleShow(mDoodadItems, 500);
    }
}
