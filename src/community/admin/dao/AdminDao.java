package lovefactory.admin.dao;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.config.Config;
import koonisoft.jas.db.Database;
import koonisoft.jas.db.QueryHandler;
import koonisoft.jas.db.ResultRow;
import lovefactory.common.dao.AbstractDao;
import lovefactory.user.User;

public class AdminDao extends AbstractDao {

   public AdminDao(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public String userBan(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";

      Database db = null;

      try {
         db = Database.getDatabase(runtimeProp, Config.getString("lovefactory.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.admin.user.delete");
         
         qry.replaceValue("USER_ID", user.getUserID());
         
         db.close();
         db = null;
         retVal = "S";
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

   public ArrayList<User> getUserList() throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      ArrayList<User> userList = new ArrayList<User>();
      Database db = null;
      ResultRow rs = null;
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("lovefactory.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.user.list.select");
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            User user = new User();
            user.setUserID( rs.getString("USER_ID") );
            user.setUserName( rs.getString("USER_NAME") );
            user.setUserLevel( rs.getInteger("USER_LEVEL") );
            user.setUserPhone( rs.getString("USER_PHONE") );
            user.setUserNick( rs.getString("USER_NICK") );
            user.setUserType( rs.getString("USER_TYPE") );
            
            userList.add(user);
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      
      return userList;
   }

   public String userGradeUpdate(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";

      Database db = null;

      try {
         db = Database.getDatabase(runtimeProp, Config.getString("lovefactory.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.admin.user.grade.update");
         
         qry.replaceValue("USER_ID",    user.getUserID());
         qry.replaceValue("USER_LEVEL", user.getUserLevel());
         
         db.close();
         db = null;
         retVal = "S";
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

}
