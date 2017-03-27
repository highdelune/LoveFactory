package lovefactory.util;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.http.iSession;
import lovefactory.Constants;

public class SessionUtil {
   public static void sessionCheck(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      iSession session = req.getSession();
      
      if ( session.getObject(Constants.USER_SESSION_KEY) == null ) {
         TextBinder repBinder = TextBinder.createInstance(runtimeProp, "/common/alert.html");
         repBinder.replace("MESSAGE", "로그인이 해제되었습니다. 로그인 화면으로 돌아갑니다.");
         repBinder.replace("NEXT_URL", "/index.svc");
         
         rep.out(repBinder);
      }
   }
}
