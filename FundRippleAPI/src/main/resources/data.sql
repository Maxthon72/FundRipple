DO $$
BEGIN
    IF (SELECT COUNT(*) FROM tags) = 0 THEN
        INSERT INTO tags (id,tag) VALUES (1,'Art'), (2,'Game'), (3,'Mechanic'), (4,'Movie');
    END IF;
END $$;
