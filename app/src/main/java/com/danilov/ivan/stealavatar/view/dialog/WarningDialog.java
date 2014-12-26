package com.danilov.ivan.stealavatar.view.dialog;

import android.view.View;
import android.widget.Button;

import com.danilov.ivan.stealavatar.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ivan on 11/18/14.
 */
public class WarningDialog extends AbstractDialogFragment {

    @InjectView(R.id.btn_check_network)
    Button btnSettings;

    @Override
    protected View getContentView() {
        return getActivity().getLayoutInflater().inflate(R.layout.warning_dialog, null);
    }

    @OnClick(R.id.btn_check_network)
    void onDone() {
        dismissListener.onOk();
        dismiss();
    }
}
