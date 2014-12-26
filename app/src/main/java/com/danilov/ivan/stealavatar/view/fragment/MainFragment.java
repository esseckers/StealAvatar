package com.danilov.ivan.stealavatar.view.fragment;

import android.widget.Button;
import android.widget.EditText;

import com.danilov.ivan.stealavatar.R;
import com.danilov.ivan.stealavatar.annotation.Layout;

import butterknife.InjectView;
import butterknife.OnClick;

@Layout(R.layout.fragm_main)
public class MainFragment extends AbstractFragment {

    @InjectView(R.id.et_user_name)
    EditText etUserName;

    @InjectView(R.id.btn_get_collage)
    Button btnCollage;

    @OnClick(R.id.btn_get_collage)
    void onCollageGenerate() {

    }
}
