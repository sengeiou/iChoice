package com.choicemmed.ichoice.profile.activity;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.JudgeUtils;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.profile.presenter.impl.PasswordPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：iChoice
 * 类描述：修改密码
 * 创建人：114100
 * 创建时间：2019/4/3 18:55
 * 修改人：114100
 * 修改时间：2019/4/3 18:55
 * 修改备注：
 */
public class PasswordActivity extends BaseActivty implements IBaseView {
    @BindView(R.id.input_password_current_pwd)
    TextInputLayout inputCurrentPwd;
    @BindView(R.id.input_password_new_pwd)
    TextInputLayout inputNewPwd;
    @BindView(R.id.input_password_confirm_pwd)
    TextInputLayout inputConfirmPwd;
    private PasswordPresenterImpl passwordPresenter;
    @Override
    protected int contentViewID() {
        return R.layout.activity_password;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.password), true);
        setLeftBtnFinish();
        passwordPresenter = new PasswordPresenterImpl(this,this);
    }
    @OnClick(R.id.btn_pwd_commit)
    public void onClick(){
        String oldPwd=inputCurrentPwd.getEditText().getText().toString();
        String newPwd=inputNewPwd.getEditText().getText().toString();
        String confirmPwd = inputConfirmPwd.getEditText().getText().toString();
        if (!PermissionsUtils.isNetworkConnected(this)) {
            MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
            return;
        }
        if (!TextUtils.isEmpty(inputCurrentPwd.getEditText().getText()) && !TextUtils.isEmpty(inputNewPwd.getEditText().getText()) && !TextUtils.isEmpty(inputConfirmPwd.getEditText().getText())){
            if (newPwd.equals(confirmPwd)){
                if (JudgeUtils.isPassword(confirmPwd)){
                    passwordPresenter.sendUploadPassword(IchoiceApplication.getAppData().user.getToken(), oldPwd, newPwd);
                }else {
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_password_not));
                }
            }else {
                MethodsUtils.showErrorTip(this,getString(R.string.inconsistent_password));
            }
        }else {
            MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
        }
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onError(String msg) {
        if (msg.contains("is incorrect")) {
            msg = getString(R.string.old_password_is_incorrect);
        }
        MethodsUtils.showErrorTip(this,msg);
    }
}
