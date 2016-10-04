package lovefactory.user.dao;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.config.Config;
import koonisoft.jas.db.Database;
import koonisoft.jas.db.QueryHandler;
import koonisoft.jas.db.ResultRow;
import lovefactory.common.dao.AbstractDao;
import lovefactory.user.User;

public class UserDao extends AbstractDao {

   public UserDao(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public String addUser(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";

      Database db = null;

      try {
         db = Database.getDatabase(runtimeProp, Config.getString("nanol.cartoonchat.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.user.insert");
         
         qry.replaceValue("USER_ID", user.getUserID());
         qry.replaceValue("USER_PW", user.getPassword());
         qry.replaceValue("USER_LEVEL", user.getUserLevel());
         qry.replaceValue("USER_NAME", user.getUserName());
         qry.replaceValue("USER_NICK", user.getUserNick());
         qry.replaceValue("USER_PHONE", user.getUserPhone());
         qry.replaceValue("USER_TYPE", user.getUserType());
         qry.replaceValue("USER_STATE", user.getUserState());
         
         db.close();
         db = null;
         retVal = "S";
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

   public User userLogin(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      Database db = null;
      ResultRow rs = null;
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("nanol.cartoonchat.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.user.one.select");
         qry.replaceValue("USER_ID", user.getLoginID());
         qry.replaceValue("USER_PW", user.getPassword());
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            user.setUserID( rs.getString("USER_ID") );
            user.setUserName( rs.getString("USER_NAME") );
            user.setUserLevel( rs.getInteger("USER_LEVEL") );
            user.setUserPhone( rs.getString("USER_PHONE") );
            user.setUserNick( rs.getString("USER_NICK") );
            user.setUserType( rs.getString("USER_TYPE") );
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      
      return user;
   }

   public String delUser(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";
      
      Database db = null;
      
      try{
         db = Database.getDatabase(runtimeProp, Config.getString("nanol.cartoonchat.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.user.delete");
         
         qry.replaceValue("USER_ID", user.getUserID());
         qry.replaceValue("USER_PW", user.getPassword());
         
         db.close();
         db = null;
         
         retVal = "S";
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

   public String userUpdate(User user) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";
      
      Database db = null;
      
      try{
         db = Database.getDatabase(runtimeProp, Config.getString("nanol.cartoonchat.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.user.update");
         
         qry.replaceValue("USER_ID",     user.getUserID());
         qry.replaceValue("USER_PW",     user.getPassword());
         qry.replaceValue("USER_NEW_PW", user.getNewPassword());
         qry.replaceValue("USER_NICK",   user.getUserNick());
         qry.replaceValue("USER_PHONE",  user.getUserPhone());
         
         db.close();
         db = null;
         
         retVal = "S";
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

}
