<?xml version="1.0" encoding="UTF-8"?>
<jpdk_query>
   <query_list>
      <typeAlias alias="JasData" type="koonisoft.jas.util.JasData" />
      
      <query id="lovefactory.board.all.list.select">
         <sql><![CDATA[
         SELECT BOARD_ID, BOARD_NAME, ACCESS_LEVEL
           FROM LF_BOARD_INFO
          ]]></sql>
      </query>
      
      <query id="lovefactory.board.update">
         <sql><![CDATA[
         UPDATE LF_BOARD_INFO
            SET BOARD_NAME = [BOARD_NAME],
                ACCESS_LEVEL = [ACCESS_LEVEL]
          WHERE BOARD_ID = [BOARD_ID]
          ]]></sql>
      </query>
      
      <query id="lovefactory.board.list.select">
         <sql><![CDATA[
         SELECT LB.BOARD_ID, LBI.BOARD_NAME, LBI.ACCESS_LEVEL, LB.ARTICLE_ID, LB.ARTICLE_NAME, LU.USER_NICK 
           FROM LF_BOARD_INFO LBI
     INNER JOIN LF_BOARD LB
             ON LBI.BOARD_ID = LB.BOARD_ID
LEFT OUTER JOIN LF_USER LU
             ON LB.WRITER_ID = LU.USER_ID
          WHERE LB.BOARD_ID = [BOARD_ID]
          ]]></sql>
      </query>
      
      <query id="lovefactory.board.detail.select">
         <sql><![CDATA[
         SELECT LB.BOARD_ID, LB.ARTICLE_ID, LB.ARTICLE_NAME, LB.CONTENTS, LC.COMMENT, LU.USER_NICK 
           FROM LF_BOARD LB
     INNER JOIN LF_COMMENTS LC
             ON LC.ARTICLE_ID = LB.ARTICLE_ID
LEFT OUTER JOIN LF_USER LU
             ON LB.WRITER_ID = LU.USER_ID
LEFT OUTER JOIN LF_USER LU
             ON LC.USER_NICK = LU.USER_NICK
          WHERE LB.ARTICLE_ID = [ARTICLE_ID]
          ]]></sql>
      </query>
      
   </query_list>
</jpdk_query>