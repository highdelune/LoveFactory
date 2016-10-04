package lovefactory.common.controller;

import java.util.Calendar;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.binder.iData;
import koonisoft.jas.binder.iLoop;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.http.JasHttpServlet;
import koonisoft.jas.http.iSession;
import koonisoft.jas.log.Logger;
import koonisoft.jas.util.JasData;
import koonisoft.jas.util.page.JasPage;
import lovefactory.Constants;
import lovefactory.user.User;

public class AbstractServletController extends JasHttpServlet {

   public boolean sessionCheck(HttpRequest req, HttpResponse rep) throws Exception {
      return sessionCheck(req, rep, true);
   }
   
   public boolean sessionCheck(HttpRequest req, HttpResponse rep, boolean bFlag) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      koonisoft.jas.log.Logger logger = runtimeProp.getLogger();
      logger.setProgramName(getProgramName());
      
      iSession session = req.getSession();
      User loginUser = getLoginUser(req);
      
      if( null == loginUser && bFlag ) {
         TextBinder repBinder = koonisoft.jas.binder.TextBinder.createInstance(runtimeProp,"alert.html");
         repBinder.replace("MESSAGE", "Session Expired !!!");
         repBinder.replace("NEXT_URL", "/");
         rep.out(repBinder);
      }
      
      return null != loginUser;
   }
   
   protected void outPermissionDenied(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      TextBinder repBinder = koonisoft.jas.binder.TextBinder.createInstance(runtimeProp,"alert.html");
      repBinder.replace("MESSAGE", "Permission Denied !!!");
      repBinder.replace("NEXT_URL", "/");
      rep.out(repBinder);
   }
   
   public User getLoginUser(HttpRequest req) {
      iSession session = req.getSession();
      
      return (User) session.getObject(Constants.USER_SESSION_KEY);
   }
   
   public void setPageBinder(TextBinder repBinder, JasPage page) {
      
      iLoop pageLoop = repBinder.createLoop("PAGE_LIST");
      for( int i = page.getStartPage(); i <= page.getEndPage(); i++ ) {
         iData data = pageLoop.createData();
         data.replace("PAGE_NO", i);
      }
      
      repBinder.replace("CURRENT_PAGE_NO", page.getCurrentPage());
      repBinder.replace("HAS_PREV_RANGE", page.isValidPrev());
      repBinder.replace("PREV_PAGE", page.getStartPage() - 1);
      repBinder.replace("HAS_PREV", page.getStartPage() > 1);
      repBinder.replace("HAS_NEXT", page.getEndPage() < page.getTotalPage());
      repBinder.replace("NEXT_PAGE", page.getEndPage() + 1);
      repBinder.replace("HAS_NEXT_RANGE", page.isValidNext());
      repBinder.replace("LAST_PAGE", page.getTotalPage());
   }
   
   public void setBaseVariable(HttpRequest req, TextBinder repBinder) {
      Logger logger = req.getRuntimeProperties().getLogger();
      
      User loginUser = getLoginUser(req);
      if( null != loginUser ) {
         repBinder.replace("IS_LOGIN", true);
         repBinder.replace("LOGIN_USER_TYPE", loginUser.getUserType());
         repBinder.replace("LOGIN_USER_ID", loginUser.getUserID());
         repBinder.replace("LOGIN_USER_LOGIN_ID", loginUser.getLoginID());
         repBinder.replace("LOGIN_USER_NAME", loginUser.getUserName());
      }
      
      Calendar today = Calendar.getInstance();
      repBinder.replace("DISP_CURRENT_YEAR",    today.get(Calendar.YEAR));
      repBinder.replace("DISP_CURRENT_MONTH",   today.get(Calendar.MONTH) + 1);
      repBinder.replace("DISP_CURRENT_DAY",     today.get(Calendar.DAY_OF_MONTH));
      repBinder.replace("DISP_CURRENT_WEEKDAY", today.get(Calendar.DAY_OF_WEEK));
   }
   
   public void setCommonVariable(HttpRequest req, TextBinder repBinder) {
      Logger logger = req.getRuntimeProperties().getLogger();
      
      setBaseVariable(req, repBinder);
      
      Calendar today = Calendar.getInstance();
      
      try {
         JasData searchData = new JasData();
      } catch(Exception e) {
         logger.error(e);
      }
   }
   
   public boolean isAdmin(HttpRequest req) throws Exception {
      User loginUser = this.getLoginUser(req);
      
      boolean retFlag = false;
      if( null != loginUser ) {
         retFlag = Constants.SUPER_ADMIN_USER_TYPE.equals(loginUser.getUserType())
                   || Constants.ADMIN_USER_TYPE.equals(loginUser.getUserType());
      }
      return retFlag;
   }
}
