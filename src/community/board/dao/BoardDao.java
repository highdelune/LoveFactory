package community.board.dao;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.config.Config;
import koonisoft.jas.db.Database;
import koonisoft.jas.db.QueryHandler;
import koonisoft.jas.db.ResultRow;
import community.board.Board;
import community.common.dao.AbstractDao;

public class BoardDao extends AbstractDao {

   public BoardDao(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public ArrayList<Board> getBoardList() throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      Database db = null;
      ResultRow rs = null;
      
      ArrayList<Board> boardList = new ArrayList<Board>();
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "community.board.all.list.select");
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            Board board = new Board();
            
            board.setBoardId(rs.getInteger("BOARD_ID"));
            board.setBoardName(rs.getString("BOARD_NAME"));
            board.setAccessLevel(rs.getInteger("ACCESS_LEVEL"));
            
            boardList.add(board);
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      
      return boardList;
   }

   public ArrayList<Board> getContentList() throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      Database db = null;
      ResultRow rs = null;
      
      ArrayList<Board> boardList = new ArrayList<Board>();
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "community.board.list.select");
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            Board board = new Board();
            
            board.setBoardId(rs.getInteger("BOARD_ID"));
            board.setBoardName(rs.getString("BOARD_NAME"));
            board.setArticleId(rs.getInteger("ARTICLE_ID"));
            board.setArticleName(rs.getString("ARTICLE_NAME"));
            board.setNoticeYn(rs.getString("NOTICE_YN"));
            board.setAccessLevel(rs.getInteger("ACCESS_LEVEL"));
            
            boardList.add(board);
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      
      return boardList;
   }

   public String updateBoard(Board board) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      String retVal = "F";

      Database db = null;

      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "community.board.update");
         
         qry.replaceValue("BOARD_ID",           board.getBoardId());
         qry.replaceValue("BOARD_NAME",         board.getBoardName());
         qry.replaceValue("BOARD_ACCESS_LEVEL", board.getAccessLevel());
         
         db.close();
         db = null;
         retVal = "S";
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
      
      return retVal;
   }

   public void saveContent(Board board) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();

      Database db = null;

      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.master"));
         
         QueryHandler qry = null;
         if( board.getArticleId() > 0 ) {
            qry = QueryHandler.createInstance(runtimeProp, "community.board.detail.update");
            qry.replaceValue("ARTICLE_ID", board.getArticleId());
         } else {
            qry = QueryHandler.createInstance(runtimeProp, "community.board.detail.insert");
         }
         
         qry.replaceValue("BOARD_ID",             board.getBoardId());
         qry.replaceValue("ARTICLE_NAME",         board.getArticleName());
         qry.replaceValue("CONTENTS",             board.getContents());
         qry.replaceValue("NOTICE_YN",            board.getNoticeYn());
         
         db.insert(qry);
         
         db.close();
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
      }
   }

   public ArrayList<Board> getContentList(int bbsNo) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      Database db = null;
      ResultRow rs = null;
      
      ArrayList<Board> boardList = new ArrayList<Board>();
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "community.board.list.select");

         if( bbsNo != 0 ){
            qry.replace("WHERE_CONDITION", "WHERE LB.BOARD_ID = [BOARD_ID]");
            qry.replaceValue("BOARD_ID", bbsNo);
         } else {
            qry.replace("WHERE_CONDITION", "");
         }
         
         rs = db.query(qry);
         while( rs.nextRow() ) {
            Board board = new Board();
            
            board.setBoardId(rs.getInteger("BOARD_ID"));
            board.setBoardName(rs.getString("BOARD_NAME"));
            board.setArticleId(rs.getInteger("ARTICLE_ID"));
            board.setArticleName(rs.getString("ARTICLE_NAME"));
            board.setReadCount(rs.getInteger("READ_COUNT"));
            board.setWriter(rs.getString("USER_NICKNAME"));
            board.setNoticeYn(rs.getString("NOTICE_YN"));
            board.setLikeCount(rs.getInteger("LIKE_COUNT"));
            board.setAccessLevel(rs.getInteger("ACCESS_LEVEL"));
            
            boardList.add(board);
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      
      return boardList;
   }

   public Board getContent(Board board) throws Exception {
      JasRuntimeProperties runtimeProp = getRuntimeProp();
      
      Database db = null;
      ResultRow rs = null;
      
      ArrayList<Board> boardList = new ArrayList<Board>();
      try {
         db = Database.getDatabase(runtimeProp, Config.getString("community.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "community.board.content.select");
         qry.replaceValue("ARTICLE_ID", board.getArticleId());
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            
            board.setBoardId(rs.getInteger("BOARD_ID"));
            board.setBoardName(rs.getString("BOARD_NAME"));
            board.setArticleId(rs.getInteger("ARTICLE_ID"));
            board.setArticleName(rs.getString("ARTICLE_NAME"));
            board.setContents(rs.getString("CONTENTS"));
            board.setReadCount(rs.getInteger("READ_COUNT"));
            board.setWriter(rs.getString("USER_NICKNAME"));
            board.setNoticeYn(rs.getString("NOTICE_YN"));
            board.setLikeCount(rs.getInteger("LIKE_COUNT"));
            board.setAccessLevel(rs.getInteger("ACCESS_LEVEL"));
            board.setWriteTime(rs.getDate("UPDATE_TIME"));
         }

         rs.close();
         db.close();
         rs = null;
         db = null;
      } finally {
         if( null != db ) { db.close(); db = null; }
         if( null != rs ) { rs.close(); rs = null; }
      }
      return board;
   }

}
