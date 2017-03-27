package community.board.service;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import community.board.Board;
import community.board.dao.BoardDao;
import community.common.service.AbstractService;

public class BoardService extends AbstractService {

   public BoardService(JasRuntimeProperties runtimeProp) {
      super(runtimeProp);
   }

   public ArrayList<Board> getBoardList() throws Exception {
      BoardDao boardDao = new BoardDao(getRuntimeProp());
      return boardDao.getBoardList();
   }

   public String updateBoard(Board board) throws Exception {
      BoardDao boardDao = new BoardDao(getRuntimeProp());
      return boardDao.updateBoard(board);
   }

   public void saveContent(Board board) throws Exception {
      BoardDao boardDao = new BoardDao(getRuntimeProp());
      boardDao.saveContent(board);
      
   }

   public ArrayList<Board> getContentList(int bbsNo) throws Exception {
      BoardDao boardDao = new BoardDao(getRuntimeProp());
      return boardDao.getContentList(bbsNo);
   }

   public Board getContent(Board board) throws Exception {
      BoardDao boardDao = new BoardDao(getRuntimeProp());
      return boardDao.getContent(board);
   }

}
