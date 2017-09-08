package com.zzh.dao;

import com.zzh.domain.User;
import com.zzh.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by zhao on 2017/8/18.
 */
public class UserDaoTest extends BaseDaoTest{

    @SpringBean("userDao")
    private UserDao userDao;

    @Test
    @DataSet("Users.xls")
    public void findUserByUserName() {
        User user = userDao.getUserByUserName("tony");
        assertNull(user,"不存在用户名为tony的用户");
        user = userDao.getUserByUserName("jan");
        assertNotNull(user,"jan用户存在");
        assertEquals("jan", user.getUserName());
        assertEquals("123456",user.getPassword());
        assertEquals(10,user.getCredit());

    }

    /**
     * 下面两个方法要注意，两个方法中不同的excel中有相同名称的t_user
     * 如果将两个test注解打开，就会失败，原因是串表
     * 此时将其中一个test注释，然后在类上运行如果在方法上直接运行还是出错，测试数据依然存在
     *
     */


    //验证数据库保存的正确性
    //@Test
    //@DataSet("SaveUser.xls")
    //@ExpectedDataSet("ExpectedSaveUser.xls")
    public void saveUser()throws Exception  {
        User u  = XlsDataSetBeanFactory.createBean(UserDaoTest.class,"SaveUser.xls", "t_user", User.class);
        userDao.update(u);  //执行用户信息更新操作
    }

    @Test
    @ExpectedDataSet("ExpectedSaveUsers.xls")
    public void saveUsers()throws Exception  {
        List<User> users  = XlsDataSetBeanFactory.createBeans(UserDaoTest.class,"SaveUsers.xls", "t_user", User.class);
        for(User u:users){
            userDao.save(u);
        }
    }
}