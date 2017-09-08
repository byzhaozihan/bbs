package com.zzh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhao on 2017/6/25.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//每个类层次一张表
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("1")//辨别该类的值,也就是如果我的t_post表中的数据的post_type为1则类型为Post，如果post_type类型为2则是MainPost
public class Post extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "board_id")
    private int boardId;

    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="topic_id")
    private Topic topic;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
