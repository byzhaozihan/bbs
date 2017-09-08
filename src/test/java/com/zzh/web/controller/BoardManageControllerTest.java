package com.zzh.web.controller;

import com.zzh.cons.CommonConstant;
import com.zzh.dao.Page;
import com.zzh.domain.Board;
import com.zzh.domain.Post;
import com.zzh.domain.Topic;
import com.zzh.domain.User;
import com.zzh.service.ForumService;
import com.zzh.service.UserService;
import com.zzh.web.BoardManagerController;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by zhao on 2017/8/27.
 */
public class BoardManageControllerTest extends BaseWebTest {

    @SpringBeanByType
    private ForumService forumService;

    @SpringBeanByType//不要使用Autowired，被坑到过，userService.getUserByName报空指针异常
    private UserService userService;


    @SpringBeanByType
    private BoardManagerController controller;

    @Test
    public void listBoardTopics() throws Exception {
        request.setRequestURI("/board/listBoardTopics-1");
        request.addParameter("boardId","1");
        request.setMethod("GET");

        ModelAndView mav = controller.listBoardTopics(1, 1);
        Board board = (Board) mav.getModelMap().get("board");
        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/listBoardTopics");
        assertNotNull(board);
        assertEquals(board.getBoardName(), "育儿");
    }

    @Test
    public void addTopicPage() throws Exception {
        request.setRequestURI("/board/addTopicPage-1");
        request.setMethod("GET");
        ModelAndView mav = controller.addTopicPage(1);
        assertNotNull(mav);
        assertEquals(mav.getViewName(),"/addTopic");
    }

    @Test
    public void addTopic() throws Exception {
        request.setRequestURI("/board/addTopic");
        request.setMethod("POST");

        User user = userService.getUserByUserName("tom");

//        User user = new User();
//        user.setUserId(1);
//        user.setUserName("tom");
//        user.setPassword("123456");
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT,user);
        session.setAttribute(CommonConstant.USER_CONTEXT,user);

        Topic topic = new Topic();
        topic.setTopicTitle("SpringMVC深入理解");
        topic.setBoardId(2);
        topic.getMainPost().setPostTitle("SpringMVC深入理解");
        topic.getMainPost().setPostText("SpringMVC深入理解内容");

        String mav = controller.addTopic(request, topic);
        assertNotNull(mav);
    }

    @Test
    public void listTopicPosts() throws Exception {
        request.setRequestURI("/board/listTopicPosts-2");
        request.addParameter("topicId","2");
        request.setMethod("GET");
        ModelAndView mav = controller.listTopicPosts(2, 1);

        Topic topic = (Topic) mav.getModelMap().get("topic");
        Page pagedPost = (Page) mav.getModelMap().get("pagedPost");

        assertNotNull(topic);
        assertNotNull(pagedPost);
        assertEquals(pagedPost.getPageSize()>0,true);
        assertNotNull(mav);
        assertEquals(mav.getViewName(),"/listTopicPosts");
    }

    @Test
    public void addPost() throws Exception {
        request.setRequestURI("/board/addPost");
        request.addParameter("topicId", "2");
        Post post = new Post();
        post.setBoardId(2);
        post.setPostTitle("SpringMVC集成回帖4");
        post.setPostText("SpringMVC集成回帖内容4");

        request.setMethod("POST");

          User user = userService.getUserByUserName("tom");
//        User user = new User();
//        user.setUserId(1);
//        user.setUserName("tom");
//        user.setPassword("123456");
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
        session.setAttribute(CommonConstant.USER_CONTEXT,user);

        assertNotNull(user,"user为NULL");
        controller.addPost(request, post);
    }

}
