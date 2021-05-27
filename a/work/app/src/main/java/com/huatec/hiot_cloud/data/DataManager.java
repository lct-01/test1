package com.huatec.hiot_cloud.data;

import com.huatec.hiot_cloud.test.networktest.LoginResultDTO;
import com.huatec.hiot_cloud.test.networktest.ResultBase;
import com.huatec.hiot_cloud.test.networktest.UserBean;
import com.huatec.hiot_cloud.utils.Constanst;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 网络请求封装
 */

public class DataManager {

    private NetworkService service;

    SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    public DataManager(NetworkService service, SharedPreferencesHelper sharedPreferencesHelper){
        this.service = service;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    public Observable<ResultBase<LoginResultDTO>> login(String userName, String password){
       return service.login(userName,password, Constanst.LOGIN_CODE_APP)
               .doOnNext(new Consumer<ResultBase<LoginResultDTO>>() {
                   @Override
                   public void accept(ResultBase<LoginResultDTO> resultBase) throws Exception {
                       if (resultBase.getStatus() == Constanst.MSG_STATUS_SUCCESS) {
                           if (resultBase != null && resultBase.getData() != null) {
                               sharedPreferencesHelper.setUserToken(resultBase.getData().getTokenValue());
                           }
                       }
                   }
               });

        }


    /**
     * 获取用户信息
     * @param authorization
     * @return
     */

    public Observable<ResultBase<UserBean>> getUserInfo(String authorization){
        return service.getUserInfo(authorization);

    }

    /**
     * 修改邮箱
     * @param authorization
     * @param email
     * @return
     */

    public Observable<ResultBase<String>> updateEmail(String authorization, String email){
        return service.updateEmail(authorization,email);

    }

    /**
     * 注册
     * @param userName 用户名
     * @param password 密码
     * @param email   邮箱地址
     * @return
     */
    public Observable<ResultBase<UserBean>> register(String userName, String password, String email){
        UserBean userBean = new UserBean();
        userBean.setUsername(userName);
        userBean.setPassword(password);
        userBean.setEmail(email);
        userBean.setUserType(Constanst.REGISTER_TYPE_NORMAL);
        return service.register(userBean);

    }
}
