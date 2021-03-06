package com.choicemmed.ichoice.profile.presenter.impl;

import android.content.Context;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.callback.StringDialogCallback;
import com.choicemmed.ichoice.framework.http.HttpUtils;
import com.choicemmed.ichoice.framework.presenter.BasePresenterImpl;
import com.choicemmed.ichoice.framework.utils.Debugger;
import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.profile.presenter.AvatarPresenter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/2 15:31
 * 修改人：114100
 * 修改时间：2019/4/2 15:31
 * 修改备注：
 */
public class AvatarPresenterImpl extends BasePresenterImpl<IBaseView> implements AvatarPresenter {
    private Context context;

    public AvatarPresenterImpl(Context context,IBaseView iBaseView) {
        this.context = context;
        attachView(iBaseView);
    }

    @Override
    public void sendAvatarRequest(String token, File file) {
        StringDialogCallback callback=new StringDialogCallback() {
            @Override
            public void onSuccess(JSONObject object) {
                LogUtils.d("gxz","上传头像返回数据****"+object);
                try {
                    JSONObject json=object.getJSONObject("Data");
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.HEADIMGURL,json.getString("PhotoExtension"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.HEADIMGURL100x100,json.getString("Photo100x100"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.HEADIMGURL200x200,json.getString("Photo200x200"));
                    PreferenceUtil.getInstance().putPreferences(ApiConfig.HEADIMGURL500x400,json.getString("Photo500x400"));
                    IchoiceApplication.getAppData().user.refresh();
                    mView.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMessage(String message) {
                mView.onError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.sendAvatarRequest(context,token,file,callback);
    }
}
