package community.admin.service;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import community.admin.dao.AdminDao;
import community.common.service.AbstractService;
import community.user.User;

public class AdminService extends AbstractService {

   public AdminService(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public String userBan(User user) throws Exception {
      AdminDao adminDao = new AdminDao(getRuntimeProp());
      return adminDao.userBan(user);
   }

   public ArrayList<User> getUserList() throws Exception {
      AdminDao adminDao = new AdminDao(getRuntimeProp());
      return adminDao.getUserList();
   }

   public String userGradeUpdate(User user) throws Exception {
      AdminDao adminDao = new AdminDao(getRuntimeProp());
      return adminDao.userGradeUpdate(user);
   }

}
