package community.user.controller;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.http.iSession;
import koonisoft.jas.log.Logger;
import community.Constants;
import community.common.controller.AbstractServletController;
import community.user.User;
import community.user.service.UserService;
import community.util.SHAEncrypt;
import community.util.SessionUtil;

public class UserController extends AbstractServletController {
   
   public void userAddView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/user/userAddView.html");
      rep.out(repBinder);
   }

   public void userAdd(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      User user = new User();
      user.setUserID(req.getString("userId"));
      user.setPassword(SHAEncrypt.encrypt(req.getString("userPw")));
      user.setUserNick(req.getString("userNick"));
      user.setUserName(req.getString("userName"));
      user.setUserPhone(req.getString("userPhone"));
      user.setUserState("ACTIVE");
      user.setUserType("U");
      user.setUserLevel(99);
      
      UserService userService = new UserService(runtimeProp);
      String retVal = userService.addUser(user);
      
      rep.out(retVal);
      
   }
   
   public void userDelView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/user/userDelView.html");
      rep.out(repBinder);
   }

   public void userDel(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);
      
      SessionUtil.sessionCheck(req, rep);
      
      User user = new User();
      
      user.setUserID(req.getString("userId"));
      user.setPassword(req.getString("userPw"));
      
      UserService userService = new UserService(runtimeProp);
      String retVal = userService.delUser(user);
      
      rep.out(retVal);
      
   }
   
   public void userUpdateView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/user/userUpdateView.html");
      rep.out(repBinder);
   }

   public void userUpdate(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      SessionUtil.sessionCheck(req, rep);
      
      User user = new User();
      user.setPassword(SHAEncrypt.encrypt(req.getString("userPw")));
      user.setNewPassword(req.getString("userNewPw"));
      user.setUserNick(req.getString("userNick"));
      user.setUserPhone(req.getString("userPhone"));
      
      UserService userService = new UserService(runtimeProp);
      String retVal = userService.userUpdate(user);
      rep.out(retVal);
      
   }
   
   public void userLoginView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/user/userLoginView.html");
      rep.out(repBinder);
      
   }

   public void userLogin(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      User user = new User();
      user.setLoginID(req.getString("LOGINID"));
      user.setPassword(SHAEncrypt.encrypt(req.getString("PASSWD")));
      
      UserService userService = new UserService(runtimeProp);
      user = userService.userLogin(user);
      
      String retVal = "F";
      
      if( null != user.getUserID() ) {
         iSession session = req.getSession();
         session.set(Constants.USER_SESSION_KEY, user);
         
         retVal = "S";
      } else {
         retVal = "F";
      }
      
      rep.out(retVal);
      
   }
   
}
