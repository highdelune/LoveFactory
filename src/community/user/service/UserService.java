package community.user.service;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import community.common.service.AbstractService;
import community.user.Grade;
import community.user.User;
import community.user.dao.UserDao;

public class UserService extends AbstractService {

   public UserService(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public String addUser(User user) throws Exception {
      UserDao userDao = new UserDao(getRuntimeProp());
      String retVal = userDao.addUser(user);
      return retVal;
   }

   public User userLogin(User user)  throws Exception {
      UserDao userDao = new UserDao(getRuntimeProp());
      user = userDao.userLogin(user);
      return user;
   }

   public String delUser(User user) throws Exception {
      UserDao userDao = new UserDao(getRuntimeProp());
      return userDao.delUser(user);
   }

   public String userUpdate(User user) throws Exception {
      UserDao userDao = new UserDao(getRuntimeProp());
      return userDao.userUpdate(user);
   }

   public ArrayList<Grade> getGradeList() throws Exception {
      UserDao userDao = new UserDao(getRuntimeProp());
      return userDao.getGradeList();
   }

}
