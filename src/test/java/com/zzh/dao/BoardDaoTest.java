package com.zzh.dao;


import com.zzh.domain.Board;
import com.zzh.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

/**
 * Created by zhao on 2017/8/17.
 */
@SpringApplicationContext({"spring-dao.xml"})
public class BoardDaoTest extends UnitilsTestNG{

    @SpringBean("boardDao")
    private BoardDao boardDao;

    //创建一个新的论坛版块，并更新

    @Test
    @DataSet(value = "SaveBoards.xls")
    @ExpectedDataSet("ExpectedBoards.xls")
    public void addNewBoard() throws Exception {
        List<Board> boards = XlsDataSetBeanFactory.createBeans(BoardDaoTest.class, "SaveBoards.xls", "t_board", Board.class);
        for (Board board : boards) {
            boardDao.update(board);
        }
    }

    @Test
    @DataSet(value = "Boards.xls")
    @ExpectedDataSet(value = "ExpectedBoards.xls")
    public void removeBoard() {
        Board board = boardDao.get(7);
        boardDao.remove(board);
    }

    @Test
    @DataSet("Boards.xls")
    public void getBoard() {
        Board board = boardDao.load(1);
        Assert.assertNotNull(board);
        Assert.assertEquals(board.getBoardName(), "SpringMVC");
    }
}