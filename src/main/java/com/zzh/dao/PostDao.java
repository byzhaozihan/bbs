package com.zzh.dao;

import com.zzh.domain.Post;
import org.springframework.stereotype.Repository;

/**
 * Created by zhao on 2017/6/28.
 */
@Repository
public class PostDao extends BaseDao<Post> {

    private static final String GET_PAGED_POSTS = "from Post where topic.topicId =? order by createTime desc";

    private static final String DELETE_TOPIC_POSTS = "delete from Post where topic.topicId=?";

    public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
        return pagedQuery(GET_PAGED_POSTS,pageNo,pageSize,topicId);
    }

    /**
     * 删除主题下的所有帖子
     * @param topicId 主题ID
     */
    public void deleteTopicPosts(int topicId) {
        getHibernateTemplate().bulkUpdate(DELETE_TOPIC_POSTS,topicId);
    }
}
