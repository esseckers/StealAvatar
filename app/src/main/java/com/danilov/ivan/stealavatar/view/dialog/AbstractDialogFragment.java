package com.danilov.ivan.stealavatar.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.danilov.ivan.stealavatar.utils.Utils;

import butterknife.ButterKnife;

/**
 * Created by Server on 11.08.2014.
 */
public abstract class AbstractDialogFragment<T> extends DialogFragment {
    protected IDialogOnDismissListener dismissListener;
    protected Dialog dialog;

    public void setOnDismissListener(IDialogOnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(getContentView());
        ButterKnife.inject(this, dialog.getWindow().getDecorView());
        return dialog;
    }

    protected View getContentView() {
        return null;
    }

    @Override
    public void onPause() {
        dismiss();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.getWindow().setAttributes(Utils.dialogSize(dialog, getActivity()));
    }
}
