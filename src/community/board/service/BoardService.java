package lovefactory.board.service;

import java.util.ArrayList;

import koonisoft.jas.JasRuntimeProperties;
import lovefactory.board.Board;
import lovefactory.board.dao.BoardDao;
import lovefactory.common.service.AbstractService;

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

}
