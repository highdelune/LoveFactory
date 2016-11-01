DELIMITER $$
 
DROP FUNCTION IF EXISTS test.dept_connect_by_parent;
 
CREATE FUNCTION  dept_connect_by_parent(ARTICLE_ID INT) RETURNS INT
NOT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE _id INT;
    DECLARE _parent INT;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;
    DECLARE _articleId INT;
 
    SET _parent = @id;
    SET _id = -1;
    set _articleId = ARTICLE_ID;

    IF @id IS NULL THEN
        RETURN NULL;
    END IF;
 
    LOOP
        SELECT  MIN(COMMENT_ID)
        INTO    @id
        FROM    LF_COMMENTS
        WHERE   PARENT_ID = _parent
            AND COMMENT_ID > _id
            AND ARTICLE_ID = _articleId;
        
 IF @id IS NOT NULL OR _parent = @start_with THEN
            SET @level = @level + 1;
            RETURN @id;
        END IF;
  
        SET @level := @level - 1;
        
 SELECT  COMMENT_ID, PARENT_ID
        INTO    _id, _parent
        FROM    LF_COMMENTS
        WHERE   COMMENT_ID = _parent
          AND ARTICLE_ID = _articleId;
    END LOOP;
END

$$
    
DELIMITER ;