package com.zzh.service;

import com.zzh.dao.UserDao;
import com.zzh.domain.User;
import com.zzh.exception.UserExistException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertNull;

/**
 * 测试用户管理服务类
 */
public class UserServiceTest extends BaseServiceTest{

    private UserDao userDao;
    private UserService userService;

    @BeforeClass
    public void init() {//创建Dao的模拟对象
        userDao = mock(UserDao.class);
        userService = new UserService();
        ReflectionTestUtils.setField(userService, "userDao", userDao);
        //为userService私有属性userDao赋值
        //ReflectionTestUtils是一个访问测试对象中私有属性非常好用的工具类
    }

    @Test
    public void register() throws UserExistException {
        //模拟测试数据
        User user = new User();
        user.setUserName("testwww");
        user.setPassword("1234");

        //当条件满足是，执行对应的Answer的answer方法，
        // 如果answer方法抛出异常，那么测试不通过。
        doAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock){
                Object[] args = invocationOnMock.getArguments();
                User user = (User)args[0];
                if (user != null) {
                    user.setUserId(1);
                }
                return user;
            }
        }).when(userDao).save(user);

        userService.register(user);
        assertEquals(user.getUserId(), 1);
        verify(userDao, times(1)).save(user);
    }

    /**
     * 测试根据用户名模糊查询用户列表的方式
     */
    @Test
    public void getUserByUserName() {
        User user = new User();
        user.setUserName("tom");
        user.setPassword("1234");
        user.setCredit(100);
        doReturn(user).when(userDao).getUserByUserName("tom");


        User u = userService.getUserByUserName("tom");
        assertNotNull(u);
        assertEquals(u.getUserName(), user.getUserName());
        //验证被调用了特定的次数
        verify(userDao, times(1)).getUserByUserName("tom");
    }

    /**
     * 测试锁定用户的服务方法
     */
    @Test
    public void lockUser() {
        User user = new User();
        user.setUserName("tom");
        user.setPassword("1234");
        doReturn(user).when(userDao).getUserByUserName("tom");
        doNothing().when(userDao).update(user);

        userService.lockUser("tom");
        User u = userService.getUserByUserName("tom");

        assertEquals(User.USER_LOCK, u.getLocked());
    }

    @Test
    public void unlockUser() {

        User user = new User();
        user.setUserName("tom");
        user.setPassword("1234");
        user.setLocked(User.USER_LOCK);
        doReturn(user).when(userDao).getUserByUserName("tom");
        doNothing().when(userDao).update(user);

        userService.unlockUser("tom");
        User u = userService.getUserByUserName("tom");
        assertEquals(User.USER_UNLOCK, u.getLocked());
    }


}
