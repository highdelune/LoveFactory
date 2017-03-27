<?xml version="1.0" encoding="UTF-8"?>
<jpdk_query>
   <query_list>
      <typeAlias alias="JasData" type="koonisoft.jas.util.JasData" />
      
      <query id="community.user.delete">
         <sql><![CDATA[
         UPDATE LF_USER
            SET USER_STATE = 'DEACTIVATED',
                USER_DEACTEVATED = SYSDATE()
          WHERE USER_ID = [USER_ID]
          ]]></sql>
      </query>
      
      <query id="community.admin.user.grade.update">
         <sql><![CDATA[
         UPDATE LF_USER
            SET USER_LEVEL = [USER_LEVEL]
          WHERE USER_ID = [USER_ID]
          ]]></sql>
      </query>
      
   </query_list>
</jpdk_query>