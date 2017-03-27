package community.user;

public class Grade {
   private int gradeCode = 0;
   private String gradeName = null;
   private String gradeDesc = null;
   
   public int getGradeCode() {
      return gradeCode;
   }
   public void setGradeCode(int gradeCode) {
      this.gradeCode = gradeCode;
   }
   public String getGradeName() {
      return gradeName;
   }
   public void setGradeName(String gradeName) {
      this.gradeName = gradeName;
   }
   public String getGradeDesc() {
      return gradeDesc;
   }
   public void setGradeDesc(String gradeDesc) {
      this.gradeDesc = gradeDesc;
   }
}
