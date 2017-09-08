package com.zzh.dao;

import com.zzh.domain.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * Created by zhao on 2017/6/28.
 */
@Repository
public class LoginLogDao extends BaseDao<LoginLog> {
    public void save(LoginLog loginLog) {
        this.getHibernateTemplate().save(loginLog);
    }

}
