package cn.cloud.service.impl;

import cn.cloud.dao.LoginDao;
import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;
import cn.cloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDao loginDao;

    public CloudUser login(String user_name, String user_pwd) {

        CloudUser user=loginDao.login(user_name,user_pwd);
        return user;
    }
    @Override
    public void regist(CloudUser user) {
       loginDao.regist(user);
    }
    @Override
    public CloudUser queryByUserName(String user_name) {

        CloudUser user=loginDao.queryByUserName(user_name);
        return user;
    }
    @Override
    public CloudAdmin adminLogin(String admin_name, String admin_pwd) {
        return loginDao.adminLogin(admin_name,admin_pwd);
    }
    @Override
    public Boolean findUser(String user_name)
    {
        if(loginDao.findUser(user_name)==null){
            return true;
        }else{
            return false;
        }
    }
}

