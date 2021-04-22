package com.huatec.hiot_cloud.test.mvptest;

import com.huatec.hiot_cloud.test.mvptest.dagger2test.ThirdObj;
import com.huatec.hiot_cloud.test.mvptest.model.User;
import com.huatec.hiot_cloud.ui.base.BasePresenter;

import javax.inject.Inject;

public class TestPresenter extends BasePresenter<TestView> {

    @Inject
    ThirdObj thirdObj;

    @Inject
    public TestPresenter() {
    }


    public void login(User user){
        thirdObj.thirdAction();
        if ("zhangyuhui".equals(user.getUserName()) && "123".equals(user.getPassword())){
            getView().showMessage("登录成功");
        }else {
            getView().showMessage("登录失败");
        }
    }

}
