package community.board;

public class Image {
   private String imageName = null;
   private String imageUrl = null;
   private boolean newLine = false;
   
   public String getImageName() {
      return imageName;
   }
   public void setImageName(String imageName) {
      this.imageName = imageName;
   }
   public String getImageUrl() {
      return imageUrl;
   }
   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }
   public boolean isNewLine() {
      return newLine;
   }
   public void setNewLine(boolean newLine) {
      this.newLine = newLine;
   }
}
