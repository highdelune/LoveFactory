package lovefactory.board.dao;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import koonisoft.jas.config.Config;
import koonisoft.jas.db.Database;
import koonisoft.jas.db.QueryHandler;
import koonisoft.jas.db.ResultRow;
import lovefactory.board.Board;
import lovefactory.common.dao.AbstractDao;

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
         db = Database.getDatabase(runtimeProp, Config.getString("lovefactory.admin.db.slave"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.board.all.list.select");
         
         rs = db.query(qry);
         if( rs.nextRow() ) {
            Board board = new Board();
            
            board.setBoardId(rs.getInteger("BOARD_ID"));
            board.setBoardName(rs.getString("BOARD_NAME"));
            board.setAccessLevel(rs.getInteger("BOARD_ACCESS_LEVEL"));
            
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
         db = Database.getDatabase(runtimeProp, Config.getString("lovefactory.admin.db.master"));
         
         QueryHandler qry = null;
         qry = QueryHandler.createInstance(runtimeProp, "lovefactory.board.update");
         
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

}
