package com.huatec.hiot_cloud.ui.login;

import android.text.TextUtils;

import androidx.appcompat.view.menu.BaseMenuPresenter;

import com.huatec.hiot_cloud.data.DataManager;
import com.huatec.hiot_cloud.test.networktest.LoginResultDTO;
import com.huatec.hiot_cloud.test.networktest.ResultBase;
import com.huatec.hiot_cloud.ui.base.BasePresenter;
import com.huatec.hiot_cloud.utils.Constanst;

import javax.inject.Inject;

import butterknife.internal.Constants;

/**
 * 登录模块presenter类
 */
class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    DataManager dataManager;

    @Inject
    public LoginPresenter(){
    }

    /**
     * 登录
     * @param email
     * @param password
     */
    public void login(String email, String password) {
        subscribe(dataManager.login(email, password), new RequestCallback<ResultBase<LoginResultDTO>>() {
            @Override
            public void onNext(ResultBase<LoginResultDTO> resultBase) {
                if (resultBase.getStatus() == Constanst.MSG_STATUS_SUCCESS) {
                    //如给登录身份正确
                    if (resultBase != null && resultBase.getData() != null) {
                        //弹出登录成功
                        getView().showMessage("登陆成功");

                        //跳转到主页面
                        getView().loginSucc();
                    }
                }else {
                    //如果登录身份错误，弹出服务端返回的错误信息
                    if (resultBase != null && !TextUtils.isEmpty(resultBase.getMsg())) {
                        getView().showMessage(resultBase.getMsg());
                    }
                }
            }

            @Override
            public void onError(Throwable e){
                super.onError(e);
                getView().showMessage(Constanst.TOAST_MSG_NETWORK_FALL);
            }
        });
    }
}
