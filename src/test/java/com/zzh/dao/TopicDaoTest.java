package com.zzh.dao;

import com.zzh.domain.Topic;
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
public class TopicDaoTest extends BaseDaoTest{

    @SpringBean("topicDao")
    private TopicDao topicDao;

    @Test
    @DataSet("SaveTopics.xls")
    @ExpectedDataSet("ExpectedTopics.xls")
    public void addTopic() throws Exception {

        List<Topic> topics = XlsDataSetBeanFactory.createBeans(TopicDaoTest.class, "SaveTopics.xls", "t_topic", Topic.class);
        for (Topic topic : topics) {
            User user = new User();
            user.setUserId(1);
            topic.setUser(user);
            topicDao.save(topic);
        }
    }
}