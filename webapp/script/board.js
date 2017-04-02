$(document).ready(function (){
   $("#bbs_write").click(function(){
      document.location.href="./write.svc";
   });
   $("#bbs_mod").click(function(){
      var contentId = $("#article_id").val();
      document.location.href="./write.svc?article_id=" + contentId;
   });
});