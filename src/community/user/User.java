package lovefactory.user;

import java.util.Calendar;

public class User {

   private String userID = null;
   private String userName = null;
   private String loginID = null;
   private String password = null;
   private String newPassword = null;
   private String userType = null;
   private String desc = null;
   private Calendar createDate = null;
   private Calendar updateDate = null;
   private int userLevel = 99;
   private String userPhone = null;
   private String userNick = null;
   private String userState = null;
   
   public String getUserID() {
      return userID;
   }
   public void setUserID(String userID) {
      this.userID = userID;
   }
   public String getUserName() {
      return userName;
   }
   public void setUserName(String userName) {
      this.userName = userName;
   }
   public String getLoginID() {
      return loginID;
   }
   public void setLoginID(String loginID) {
      this.loginID = loginID;
   }
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   public String getNewPassword() {
      return newPassword;
   }
   public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
   }
   public String getUserType() {
      return userType;
   }
   public void setUserType(String userType) {
      this.userType = userType;
   }
   public String getDesc() {
      return desc;
   }
   public void setDesc(String desc) {
      this.desc = desc;
   }
   public Calendar getCreateDate() {
      return createDate;
   }
   public void setCreateDate(Calendar createDate) {
      this.createDate = createDate;
   }
   public Calendar getUpdateDate() {
      return updateDate;
   }
   public void setUpdateDate(Calendar updateDate) {
      this.updateDate = updateDate;
   }
   public int getUserLevel() {
      return userLevel;
   }
   public void setUserLevel(int userLevel) {
      this.userLevel = userLevel;
   }
   public String getUserPhone() {
      return userPhone;
   }
   public void setUserPhone(String userPhone) {
      this.userPhone = userPhone;
   }
   public String getUserNick() {
      return userNick;
   }
   public void setUserNick(String userNick) {
      this.userNick = userNick;
   }
   public String getUserState() {
      return userState;
   }
   public void setUserState(String userState) {
      this.userState = userState;
   }
}
