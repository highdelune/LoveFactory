package community.common.controller;

import community.user.User;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.http.iSession;
import koonisoft.jas.log.Logger;

public class MainController extends AbstractServletController {

   public void mainView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/main.html");
      rep.out(repBinder);
   }

   public void mainFrameView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/mainFrame.html");
      rep.out(repBinder);
   }

   public void topView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/top.html");
      User loginUser = getLoginUser(req);
      
      boolean adminFlag = loginUser.getUserType().equals("A") ? true : false;
      repBinder.replace("IS_ADMIN", adminFlag);
      rep.out(repBinder);
   }
   
}
