<?xml version="1.0" encoding="UTF-8"?>
<jpdk_query>
   <query_list>
      <typeAlias alias="JasData" type="koonisoft.jas.util.JasData" />
      
      <query id="lovefactory.user.insert">
         <sql><![CDATA[
         INSERT INTO LF_USER (USER_ID, USER_PASSWORD, USER_NICKNAME, USER_NAME, USER_PHONE, USER_STATE, USER_TYPE, USER_LEVEL, USER_CREATED)
              VALUES ([USER_ID], [USER_PW], [USER_NICK], [USER_NAME], [USER_PHONE], [USER_STATE], [USER_TYPE], [USER_LEVEL], SYSDATE())
          ]]></sql>
      </query>
      
      <query id="lovefactory.user.one.select">
         <sql><![CDATA[
         SELECT USER_ID, USER_PASSWORD, USER_NICKNAME, USER_NAME, USER_PHONE, USER_STATE, USER_TYPE, USER_LEVEL
           FROM LF_USER
          WHERE USER_ID = [USER_ID]
            AND USER_PASSWORD = [USER_PW]
          ]]></sql>
      </query>
      
      <query id="lovefactory.user.list.select">
         <sql><![CDATA[
         SELECT USER_ID, USER_PASSWORD, USER_NICKNAME, USER_NAME, USER_PHONE, USER_STATE, USER_TYPE, USER_LEVEL
           FROM LF_USER
          WHERE USER_ID != 'LOVEFACTORY'
          ]]></sql>
      </query>
      
      <query id="lovefactory.user.delete">
         <sql><![CDATA[
         UPDATE LF_USER
            SET USER_STATE = 'DEACTIVATED',
                USER_DEACTEVATED = SYSDATE()
          WHERE USER_ID = [USER_ID]
            AND USER_PASSWORD = [USER_PW]
          ]]></sql>
      </query>
      
      <query id="lovefactory.user.update">
         <sql><![CDATA[
         UPDATE LF_USER
            SET USER_PASSWORD = [USER_NEW_PW],
                USER_NICKNAME = [USER_NICK],
                USER_PHONE    = [USER_PHONE]
          WHERE USER_ID = [USER_ID]
            AND USER_PASSWORD = [USER_PW]
          ]]></sql>
      </query>
      
      <query id="lovefactory.user.grade.list.select">
         <sql><![CDATA[
         SELECT GRADE_CODE, GRADE_NAME, GRADE_DESC
           FROM LF_USER_GRADE
          ]]></sql>
      </query>
      
   </query_list>
</jpdk_query>