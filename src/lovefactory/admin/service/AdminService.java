package lovefactory.admin.service;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import lovefactory.admin.dao.AdminDao;
import lovefactory.common.service.AbstractService;
import lovefactory.user.User;

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
