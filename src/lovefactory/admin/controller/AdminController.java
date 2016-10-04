package lovefactory.admin.controller;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.binder.iData;
import koonisoft.jas.binder.iLoop;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.log.Logger;
import lovefactory.admin.service.AdminService;
import lovefactory.board.Board;
import lovefactory.board.service.BoardService;
import lovefactory.common.controller.AbstractServletController;
import lovefactory.user.Grade;
import lovefactory.user.User;
import lovefactory.user.service.UserService;

public class AdminController extends AbstractServletController {
   
   public void userListView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/admin/userListView.html");
      iLoop      userLoop  = repBinder.createLoop("USER_LIST");
      iData      userData  = null;
      iLoop      gradeLoop = repBinder.createLoop("GRADE_LIST");
      iData      gradeData = null;
      
      ArrayList<User> userList = new ArrayList<User>();
      AdminService adminService = new AdminService(runtimeProp);
      userList = adminService.getUserList();
      
      for ( int i = 0; null != userList && 0 < userList.size(); i++ ) {
         User user = userList.get(i);
         
         userData = userLoop.createData();
         userData.replace("USER_ID",    user.getUserID());
         userData.replace("USER_LEVEL", user.getUserLevel());
         userData.replace("USER_NAME",  user.getUserName());
         userData.replace("USER_NICK",  user.getUserNick());
         userData.replace("USER_PHONE", user.getUserPhone());
         userData.replace("USER_STATE", user.getUserState());
         userData.replace("USER_TYPE",  user.getUserType());
      }
      
      ArrayList<Grade> gradeList = new ArrayList<Grade>();
      UserService userService = new UserService(runtimeProp);
      gradeList = userService.getGradeList();
      
      for ( int i = 0; null != gradeList && 0 < gradeList.size(); i++ ) {
         Grade grade = gradeList.get(i);
         
         gradeData = gradeLoop.createData();
         gradeData.replace("GRADE_CODE", grade.getGradeCode());
         gradeData.replace("GRADE_NAME", grade.getGradeName());
         gradeData.replace("GRADE_DESC", grade.getGradeDesc());
      }
      
      rep.out(repBinder);
   }

   public void userBan(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      User user = new User();
      user.setUserID(req.getString("USER_ID"));
      
      AdminService adminService = new AdminService(runtimeProp);
      String retVal = adminService.userBan(user);
      
      rep.out(retVal);
      
   }
   
   public void userGradeUpdate(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      User user = new User();
      user.setUserID(req.getString("USER_ID"));
      user.setUserLevel(req.getInteger("USER_LEVEL"));
      
      AdminService adminService = new AdminService(runtimeProp);
      String retVal = adminService.userGradeUpdate(user);
      
      rep.out(retVal);
      
   }
   
   public void boardList(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/admin/boardListView.html");
      iLoop boardLoop = repBinder.createLoop("BOARD_LIST");
      iData boardData = null;
      
      ArrayList<Board> boardList = new ArrayList<Board>();
      BoardService boardService = new BoardService(runtimeProp);
      boardList = boardService.getBoardList();
      
      for( int i = 0; null != boardList && 0 < boardList.size(); i++ ) {
         Board board = boardList.get(i);
         
         boardData = boardLoop.createData();
         boardData.replace("BOARD_ID",           board.getBoardId());
         boardData.replace("BOARD_NAME",         board.getBoardName());
         boardData.replace("BOARD_ACCESS_LEVEL", board.getAccessLevel());
      }
      
      rep.out(repBinder);
      
   }
   
   public void boardUpdate(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      Board board = new Board();
      
      BoardService boardService = new BoardService(runtimeProp);
      String retVal = boardService.updateBoard(board);
      
      rep.out(retVal);
      
   }

}
