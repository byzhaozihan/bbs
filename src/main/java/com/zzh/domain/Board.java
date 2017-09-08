package com.zzh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhao on 2017/6/24.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_board")
public class Board extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int boardId;

    @Column(name = "board_name")
    private  String boardName;

    @Column(name = "board_desc")
    private String boardDesc;

    @Column(name = "topic_num")
    private int topicNum;

    //级联新增与级联更新不包括级联删除与级联更新
    //在版块类中使用了mappedBy指向了用户类属性manBoards，这样的意思就是只能通过用户操作来做中间表（用户版块表）维护工作。
    //如果通过版块来做维护的时候，中间表是不会做任何数据修改工作的。
    //即只能通过用户的对象调用setManBoards方法来把版块加到用户中，再由用户service层来操作用户对象中间表才会有数据
    //FetchType.LAZY：延迟加载
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "manBoards", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDesc() {
        return boardDesc;
    }

    public void setBoardDesc(String boardDesc) {
        this.boardDesc = boardDesc;
    }

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
