<?xml version="1.0" encoding="UTF-8"?>
<jpdk_query>
   <query_list>
      <typeAlias alias="JasData" type="koonisoft.jas.util.JasData" />
      
      <query id="community.board.all.list.select">
         <sql><![CDATA[
         SELECT BOARD_ID, BOARD_NAME, ACCESS_LEVEL
           FROM LF_BOARD_INFO
          ]]></sql>
      </query>
      
      <query id="community.board.update">
         <sql><![CDATA[
         UPDATE LF_BOARD_INFO
            SET BOARD_NAME = [BOARD_NAME],
                ACCESS_LEVEL = [ACCESS_LEVEL]
          WHERE BOARD_ID = [BOARD_ID]
          ]]></sql>
      </query>
      
      <query id="community.board.list.select">
         <sql><![CDATA[
         SELECT LB.BOARD_ID, LBI.BOARD_NAME, LBI.ACCESS_LEVEL, LB.ARTICLE_ID, LB.ARTICLE_NAME, LU.USER_NICKNAME, LB.NOTICE_YN, LB.READ_COUNT, LB.LIKE_COUNT
           FROM LF_BOARD_INFO LBI
     INNER JOIN LF_BOARD LB
             ON LBI.BOARD_ID = LB.BOARD_ID
LEFT OUTER JOIN LF_USER LU
             ON LB.WRITER_ID = LU.USER_ID
          [WHERE_CONDITION]
       ORDER BY LB.ARTICLE_ID DESC
          ]]></sql>
      </query>
      
      <query id="community.board.content.select">
         <sql><![CDATA[
         SELECT LB.BOARD_ID, LBI.BOARD_NAME, LBI.ACCESS_LEVEL, LB.ARTICLE_ID, LB.ARTICLE_NAME, LB.CONTENTS, LU.USER_NICKNAME, LB.NOTICE_YN, LB.READ_COUNT, LB.LIKE_COUNT, LB.UPDATE_TIME
           FROM LF_BOARD_INFO LBI
     INNER JOIN LF_BOARD LB
             ON LBI.BOARD_ID = LB.BOARD_ID
LEFT OUTER JOIN LF_USER LU
             ON LB.WRITER_ID = LU.USER_ID
          WHERE ARTICLE_ID = [ARTICLE_ID]
          ]]></sql>
      </query>
      
      <query id="community.board.detail.insert">
         <sql><![CDATA[
         INSERT INTO LF_BOARD
                (BOARD_ID,
                ARTICLE_NAME,
                CONTENTS,
                READ_COUNT,
                LIKE_COUNT,
                NOTICE_YN,
                UPDATE_TIME)
                VALUES([BOARD_ID], [ARTICLE_NAME], [CONTENTS], 0, 0, [NOTICE_YN], SYSDATE())
                
         ]]></sql>
      </query>
      
      <query id="community.board.detail.update">
         <sql><![CDATA[
         UPDATE LF_BOARD
            SET ARTICLE_NAME = [ARTICLE_NAME],
                CONTENTS = [CONTENTS],
                READ_COUNT = 0,
                LIKE_COUNT = 0,
                NOTICE_YN = [NOTICE_YN],
                UPDATE_TIME = SYSDATE()
          WHERE ARTICLE_ID = [ARTICLE_ID]
         ]]></sql>
      </query>
      
      <query id="community.board.detail.select">
         <sql><![CDATA[
         SELECT LB.BOARD_ID, LB.ARTICLE_ID, LB.ARTICLE_NAME, LB.CONTENTS, LC.COMMENT, LU.USER_NICK , LB.UPDATE_TIME AS BOARD_UPDATE, LC.UPDATE_TIME AS COMMENT_UPDATE
           FROM LF_BOARD LB
     INNER JOIN LF_COMMENTS LC
             ON LC.ARTICLE_ID = LB.ARTICLE_ID
LEFT OUTER JOIN LF_USER LU
             ON LB.WRITER_ID = LU.USER_ID
LEFT OUTER JOIN LF_USER LU
             ON LC.USER_NICK = LU.USER_NICK
          WHERE LB.ARTICLE_ID = [ARTICLE_ID]
       ORDER BY LC.UPDATE_TIME
          ]]></sql>
      </query>
      
      <query id="community.board.comment.select">
         <sql><![CDATA[
SELECT CONCAT(REPEAT('  ', level  - 1), LU.USER_ID) AS name, LC.COMMENT, LC.COMMENT_ID, LC.PARENT_ID, FUNC.level FROM
(
SELECT dept_connect_by_parent([ARTICLE_ID]) AS id, @level as level
    FROM    (
        SELECT  @start_with := 0,
                      @id := @start_with,
                      @level := 0
         ) vars, LF_COMMENTS
            WHERE   @id IS NOT NULL
) FUNC
Join LF_COMMENTS LC
ON FUNC.COMMENT_ID = LC.COMMENT_ID
INNER JOIN LF_USER LU
        ON LC.USER_IDX = LU.USER_IDX
     WHERE LC.ARTICLE_ID = [ARTICLE_ID]
          ]]></sql>
      </query>
      
   </query_list>
</jpdk_query>