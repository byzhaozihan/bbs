package com.zzh.service;

import com.zzh.dao.LoginLogDao;
import com.zzh.dao.UserDao;
import com.zzh.domain.LoginLog;
import com.zzh.domain.User;
import com.zzh.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户管理服务类，负责查询用户，注册用户，锁定用户等操作
 */
@Service
public class UserService {

    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    /**
     * 注册新用户
     */
    public void register(User user) throws UserExistException {
        User u = userDao.getUserByUserName(user.getUserName());
        if (u != null) {
            throw new UserExistException("用户名已经存在");
        } else {
            user.setCredit(100);
            user.setUserType(1);
            userDao.save(user);
        }
    }

    /**
     * 更新用户
     */
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * 根据用户名查询User对象
     */
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    /**
     * 根据userId加载User对象
     */
    public User getUserById(int userId) {
        return userDao.get(userId);
    }

    /**
     * 将用户锁定，锁定的用户不能够登录
     * @param userName 锁定目标用户的用户名
     */
    public void lockUser(String userName){
        User user = userDao.getUserByUserName(userName);
        user.setLocked(User.USER_LOCK);
        userDao.update(user);
    }

    /**
     * 解除用户的锁定
     * @param userName 解除锁定目标用户的用户名
     */
    public void unlockUser(String userName){
        User user = userDao.getUserByUserName(userName);
        user.setLocked(User.USER_UNLOCK);
        userDao.update(user);
    }

    /**
     * 根据用户名为条件，执行模糊查询操作
     * @param userName 查询用户名
     * @return 所有用户名前导匹配的userName的用户
     */
    public List<User> queryUserByUserName(String userName) {
        return userDao.queryUserByUserName(userName);
    }

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        return userDao.loadAll();
    }

    /**
     * 登陆成功
     */
    public void loginSuccess(User user) {
        user.setCredit(5 + user.getCredit());
        LoginLog loginLog =  new LoginLog();
        loginLog.setUser(user);
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(new Date());
        userDao.update(user);
        loginLogDao.save(loginLog);
    }
}
