package com.zzh.dao;

import com.zzh.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhao on 2017/6/27.
 */
@Repository
public class BoardDao extends BaseDao<Board> {

    //获取所有论坛板块板块数目
    private static final String GET_BOARD_NUM = "select count(f.boardId) from Board f";

    public long getBoardNum() {
        Iterator iter = getHibernateTemplate().iterate(GET_BOARD_NUM);
//        List list = getHibernateTemplate().find(GET_BOARD_NUM);
        return (long) iter.next();
    }

}
