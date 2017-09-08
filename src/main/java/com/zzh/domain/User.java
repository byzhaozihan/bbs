package com.zzh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhao on 2017/6/25.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_user")
public class User extends BaseDomain {

    //锁定用户对应的状态值
    public static final int USER_LOCK = 1;

    //解锁状态值
    public static final int USER_UNLOCK = 0;

    //管理员类型
    public static final int FORUM_ADMIN = 2;

    //普通用户类型
    public static final int NORMAL_USER = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_type")
    private int userType = NORMAL_USER;

    @Column(name = "last_ip")
    private String lastIp;

    @Column(name = "last_visit")
    private Date lastVisit;

    //属性名即为列名不用标注Column注解
    private String password;

    private int locked;

    private int credit;

    //joinColumns是主操作表的中间表列，而inverseJoinColumns是副操作表的中间表列。
    //mappedBy和@JoinTable是互斥的，也就是说，@"关联关系注解"（@ManyToMany）里面写了mappedBy属性，
    //下面就不能再写@JoinTable。否则，Hibernate报异常。
    //FetchType.EAGER：急加载，加载一个实体时，定义急加载的属性manBoards会立即从数据库中加载到对象中
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_board_manager", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "board_id")})
    private Set<Board> manBoards = new HashSet<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Set<Board> getManBoards() {
        return manBoards;
    }

    public void setManBoards(Set<Board> manBoards) {
        this.manBoards = manBoards;
    }
}
