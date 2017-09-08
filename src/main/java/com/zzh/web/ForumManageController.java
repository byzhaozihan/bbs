package com.zzh.web;

import com.zzh.domain.Board;
import com.zzh.domain.User;
import com.zzh.service.ForumService;
import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 论坛管理员的操作
 * 包括：创建论坛板块，指定版块管理员，用户锁定/解锁
 * 由于进行用户锁定，指定论坛版块管理员等都需要一个具体的操作页面
 * 所以Controller的另一项工作是将请求导向一个具体的操作页面
 */
@Controller
public class ForumManageController extends BaseController{

    private ForumService forumService;

    private UserService userService;

    @Autowired
    public void setForumService(ForumService forumService) {
        this.forumService = forumService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 列出所有的论坛版块
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView listAllBoards() {
        ModelAndView view = new ModelAndView();
        List<Board> boards = forumService.getAllBoards();
        view.addObject("boards", boards);
        view.setViewName("/listAllBoards");
        return view;
    }

    /**
     * 添加一个版块页面
     */
    @RequestMapping(value = "/forum/addBoardPage",method = RequestMethod.GET)
    public String addBoardPage() {
        return "/addBoard";
    }

    /**
     * 添加一个版块成功
     * 需要将RequestMethod改为PUT
     */
    @RequestMapping(value = "/forum/addBoard",method = RequestMethod.PUT)
    public String addBoard(Board board) {
        forumService.addBoard(board);
        return "/addBoardSuccess";
    }

    /**
     * 指定版块管理员的页面
     */
    @RequestMapping(value = "/forum/setBoardManagerPage",method = RequestMethod.GET)
    public ModelAndView setBoardManagerPage() {
        ModelAndView view = new ModelAndView();
        List<Board> boards = forumService.getAllBoards();
        List<User> users = userService.getAllUsers();
        view.addObject("boards", boards);
        view.addObject("users", users);
        view.setViewName("/setBoardManager");
        return view;
    }

    /**
     * 设置版块管理
     */
    @RequestMapping(value = "/forum/setBoardManager",method = RequestMethod.POST)
    public ModelAndView setBoardManager(@RequestParam("userName") String userName,
                                        @RequestParam("boardId") String boardId) {
        ModelAndView view = new ModelAndView();
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            view.addObject("errorMsg", "用户名(" + userName + ")不存在");
            view.setViewName("/fail");
        } else {
            Board board = forumService.getBoardById(Integer.parseInt(boardId));
            user.getManBoards().add(board);
            userService.update(user);
            view.setViewName("/success");
        }
        return view;

    }

    /**
     * 用户锁定及解锁管理页面
     */
    @RequestMapping(value = "/forum/userLockManagePage",method = RequestMethod.GET)
    public ModelAndView userLockManagePage() {
        ModelAndView view = new ModelAndView();
        List<User> users = userService.getAllUsers();
        view.setViewName("/userLockManage");
        view.addObject("users", users);
        return view;
    }

    /**
     * 用户锁定及解锁设定
     */
    @RequestMapping(value = "/forum/userLockManage",method = RequestMethod.POST)
    public ModelAndView userLockManage(@RequestParam("userName") String userName, @RequestParam("locked") String locked) {
        ModelAndView view = new ModelAndView();
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            view.addObject("errorMsg", "用户名(" + userName
                    + ")不存在");
            view.setViewName("/fail");
        } else {
            user.setLocked(Integer.parseInt(locked));
            userService.update(user);
            view.setViewName("/success");
        }
        return view;
    }
}
