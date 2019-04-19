package cn.cloud.service.impl;

import cn.cloud.dao.EditUserDao;
import cn.cloud.service.EditUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditUserServiceImpl implements EditUserService {
    private final
    EditUserDao editUserDao;

    @Autowired
    public EditUserServiceImpl(EditUserDao editUserDao) {
        this.editUserDao = editUserDao;
    }


    @Override
    public void editUser(String user_name, String user_real_name, String user_dept, String user_id, String user_class, Integer user_stu_id) {
        editUserDao.editUser(user_name,user_real_name,user_dept,user_id,user_class,user_stu_id);
    }

    @Override
    public Boolean findPass(String user_name, String user_pwd) {
        return editUserDao.findPass(user_name, user_pwd) != null;
    }

    @Override
    public Boolean findUserAndId(String user_name,String user_id) {
        if(String.valueOf(editUserDao.findUserAndId(user_name)).equals(user_id)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void reSetPwd(String user_name, String user_pwd) {
        editUserDao.reSetPwd(user_name,user_pwd);
    }

}
