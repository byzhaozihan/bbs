package com.zzh;

import com.zzh.dao.UserDao;
import com.zzh.domain.Board;
import com.zzh.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zhao on 2017/8/29.
 */
@ContextConfiguration({"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MainBoardTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void MainB() {

        User user = userDao.get(4);
        Set<Board> mainboards = user.getManBoards();
        Iterator<Board> iterator = mainboards.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getBoardId());
        }
    }
}
