package community.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.binder.TextBinder;
import koonisoft.jas.binder.iData;
import koonisoft.jas.binder.iLoop;
import koonisoft.jas.config.Config;
import koonisoft.jas.http.HttpRequest;
import koonisoft.jas.http.HttpResponse;
import koonisoft.jas.log.Logger;
import community.board.Board;
import community.board.service.BoardService;
import community.common.controller.AbstractServletController;

public class BoardController extends AbstractServletController {
   public void communityView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);
      
      int bbsNo = req.getInteger("bbsNo", 0);
      
      BoardService boardService = new BoardService(runtimeProp);
      
      ArrayList<Board> boardList = boardService.getBoardList();
      ArrayList<Board> contentList = boardService.getContentList(bbsNo);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/communityView.html");
      
      iLoop boardLoop = repBinder.createLoop("BOARD_LIST");
      iData boardLoopData = null;
      
      iLoop contentLoop = repBinder.createLoop("CONTENT_LIST");
      iData contentLoopData = null;
      
      for( int i = 0; i < boardList.size(); i++ ) {
         Board board = (Board) boardList.get(i);
         
         boardLoopData = boardLoop.createData();
         boardLoopData.replace("BOARD_ID", board.getBoardId());
         boardLoopData.replace("BOARD_NAME", board.getBoardName());
         boardLoopData.replace("ACCESS_LEVEL", board.getAccessLevel());
      }
      
      for( int i = 0; i < contentList.size(); i++ ) {
         Board board = (Board) contentList.get(i);
         
         contentLoopData = contentLoop.createData();
         contentLoopData.replace("BOARD_ID", board.getBoardId());
         contentLoopData.replace("BOARD_NAME", board.getBoardName());
         contentLoopData.replace("CONTENT_NUM", board.getArticleId());
         contentLoopData.replace("CONTENT_TITLE", board.getArticleName());
         contentLoopData.replace("CONTENT_READ", board.getReadCount());
         contentLoopData.replace("CONTENT_WRITER", board.getWriter());
         contentLoopData.replace("NOTICE_YN", board.getNoticeYn());
         contentLoopData.replace("LIKE_COUNT", board.getLikeCount());
         contentLoopData.replace("ACCESS_LEVEL", board.getAccessLevel());
      }
      
      rep.out(repBinder);
   }
   public void boardWriteView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);
      
      BoardService boardService = new BoardService(runtimeProp);
      
      ArrayList<Board> boardList = boardService.getBoardList();

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/boardWriteView.html");
      
      iLoop boardLoop = repBinder.createLoop("BOARD_LIST");
      iData boardLoopData = null;
      
      for( int i = 0; i < boardList.size(); i++ ) {
         Board board = (Board) boardList.get(i);
         
         boardLoopData = boardLoop.createData();
         boardLoopData.replace("BOARD_ID", board.getBoardId());
         boardLoopData.replace("BOARD_NAME", board.getBoardName());
         boardLoopData.replace("ACCESS_LEVEL", board.getAccessLevel());
      }
      
      Board board = new Board();
      if( req.getInteger("article_id") > 0 ) {
         board.setArticleId(req.getInteger("article_id"));
         board = boardService.getContent(board);
         repBinder.replace("ARTICLE_ID", board.getArticleId());
         repBinder.replace("ARTICLE_NAME", board.getArticleName());
         repBinder.replace("CONTENTS", board.getContents());
      }
      
      rep.out(repBinder);
   }
   public void saveContent(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);
      
      Board board = new Board();
      board.setBoardId(req.getInteger("bbs_id", 0));
      board.setArticleId(req.getInteger("article_id", 0));
      board.setArticleName(req.getString("subject", ""));
      board.setContents(req.getString("write_field", ""));
      
      BoardService boardService = new BoardService(runtimeProp);
      boardService.saveContent(board);
      
      communityView(req, rep);
   }
   public void contentview(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      sessionCheck(req, rep);

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/contentview.html");
      
      Board board = new Board();
      board.setArticleId(req.getInteger("content", 0));
      
      BoardService boardService = new BoardService(runtimeProp);
      ArrayList<Board> boardList = boardService.getBoardList();

      iLoop boardLoop = repBinder.createLoop("BOARD_LIST");
      iData boardLoopData = null;
      
      for( int i = 0; i < boardList.size(); i++ ) {
         Board boardData = (Board) boardList.get(i);
         
         boardLoopData = boardLoop.createData();
         boardLoopData.replace("BOARD_ID", boardData.getBoardId());
         boardLoopData.replace("BOARD_NAME", boardData.getBoardName());
         boardLoopData.replace("ACCESS_LEVEL", boardData.getAccessLevel());
      }
      
      board = boardService.getContent(board);
      
      repBinder.replace("BOARD_NAME",   board.getBoardName());
      repBinder.replace("ARTICLE_ID",   board.getArticleId());
      repBinder.replace("ARTICLE_NAME", board.getArticleName());
      repBinder.replace("CONTENTS",     board.getContents());
      repBinder.replace("LIKECOUNT",    board.getLikeCount());
      repBinder.replace("READCOUNT",    board.getReadCount());
      repBinder.replace("WRITER",       board.getWriter());
      repBinder.replace("WRITE_TIME",   board.getWriteTime());
      
      rep.out(repBinder);
   }
   public void loadSmartEditorSkin(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      BoardService boardService = new BoardService(runtimeProp);
      

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/SmartEditor2Skin.html");
      
      
      rep.out(repBinder);
   }
   public void loadInputArea(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      BoardService boardService = new BoardService(runtimeProp);
      

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/smart_editor2_inputarea.html");
      
      
      rep.out(repBinder);
   }
   public void loadInputArea8(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      BoardService boardService = new BoardService(runtimeProp);
      

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/smart_editor2_inputarea_ie8.html");
      
      
      rep.out(repBinder);
   }
   public void photoUploadView(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      BoardService boardService = new BoardService(runtimeProp);
      

      TextBinder repBinder = TextBinder.createInstance(runtimeProp,"/board/photo_uploader.html");
      
      
      rep.out(repBinder);
   }
   public void photoUpload(HttpRequest req, HttpResponse rep) throws Exception {
      JasRuntimeProperties runtimeProp = req.getRuntimeProperties();
      Logger logger = getLogger(req);
      
      BoardService boardService = new BoardService(runtimeProp);
      try {
         //파일정보
         String sFileInfo = "";
         //파일명을 받는다 - 일반 원본파일명
         String filename = req.getHeaderValue("file-name");
         //파일 확장자
         String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
         //확장자를소문자로 변경
         filename_ext = filename_ext.toLowerCase();
         //파일 기본경로
         String dftFilePath = Config.getString("jas.webapp.community.home");
         //파일 기본경로 _ 상세경로
         String filePath = dftFilePath + File.separator + "resource" + File.separator + "photo_upload" + File.separator;
         //파일 사이즈
         int size = 1024 * 1024 * 5;
         
         File file = new File(filePath);
         if(!file.exists()) {
            file.mkdirs();
         }
         String realFileNm = "";
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
         String today = formatter.format(new java.util.Date());
         realFileNm = today + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
         String rlFileNm = filePath + realFileNm;
         ///////////////// 서버에 파일쓰기 ///////////////// 
         InputStream is = req.getInputStream();
         OutputStream os = new FileOutputStream(rlFileNm);
         int numRead;
         byte b[] = new byte[Integer.parseInt(req.getHeaderValue("file-size"))];
         while((numRead = is.read(b, 0, b.length)) != -1){
            os.write(b, 0, numRead);
         }
         if(is != null) {
            is.close();
         }
         os.flush();
         os.close();
         ///////////////// 서버에 파일쓰기 /////////////////
         // 정보 출력
         sFileInfo += "&bNewLine=true";
         // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
         sFileInfo += "&sFileName="+ filename;
         sFileInfo += "&sFileURL=" + Config.getString("jas.system.context.path") + "/resource/photo_upload/" + realFileNm;
         
         rep.out(sFileInfo);
    } catch (Exception e) {
        e.printStackTrace();
    }
   }
}
