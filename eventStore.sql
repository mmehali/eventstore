-- -----------------------------------------------------
-- Schema EventStore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `EventStore` ;
USE `EventStore` ;

-- -----------------------------------------------------
-- Table `EventStore`.`Event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventStore`.`Event` (
  `id` INT NOT NULL,
  `TimeStamp` DATETIME NULL,
  `Name` VARCHAR(45) NULL,
  `version` VARCHAR(45) NULL,
  `EventSourceId` INT NULL,
  `Sequence` VARCHAR(45) NULL,
  `Data` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EventStore`.`Snapshots`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventStore`.`Snapshots` (
  `EventSourceId` INT NOT NULL,
  `Version` BIGINT(0) NULL,
  `TimeStamp` DATETIME NULL,
  `Type` VARCHAR(45) NULL,
  `Date` TEXT NULL,
  PRIMARY KEY (`EventSourceId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EventStore`.`EventSources`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventStore`.`EventSources` (
  `ID` INT NOT NULL,
  `type` INT NULL,
  `data` TEXT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




-- DeleteUnusedProviders
  DELETE FROM [EventSources] WHERE (SELECT Count(EventSourceId) FROM [Events] WHERE [EventSourceId]=[EventSources].[Id]) = 0"; 

-- InsertNewEventQuery  (saveEvent)
INSERT INTO Events (Id, EventSourceId, Name, Version, Data, Sequence, TimeStamp) 
VALUES (EventId, EventSourceId, Name, Version, Data, Sequence, TimeStamp); 

--  InsertNewProviderQuery
INSERT INTO [EventSources](Id, Type, Version) VALUES (@Id, @Type, @Version)"; 

-- InsertSnapshot  (saveSnapshot)
DELETE FROM Snapshots WHERE EventSourceId= ?EventSourceId; 
INSERT INTO Snapshots(EventSourceId, Timestamp, Version, Type, Data) 
VALUES (?EventSourceId, GETDATE(), ?Version, ?Type, ?Data)"; 

-- SelectAllEventsQuery
SELECT Id, EventSourceId, Name, Version, TimeStamp, Data, Sequence 
FROM Events 
WHERE EventSourceId = EventSourceId 
AND Sequence >= EventSourceMinVersion 
AND Sequence <= EventSourceMaxVersion 
ORDER BY Sequence 

-- SelectEventsAfterQuery
SELECT TOP {0} [Id], [EventSourceId], [Name], [Version], [TimeStamp], [Data], [Sequence] 
FROM [Events] 
WHERE SequentialId > (SELECT [SequentialId] 
                        FROM [Events] 
						WHERE [Id] = @EventId) 
ORDER BY SequentialId; 

-- SelectEventsFromBeginningOfTime 
SELECT TOP {0} [Id], [EventSourceId], [Name], [Version], [TimeStamp], [Data], [Sequence] 
FROM [Events] 
ORDER BY [SequentialId]"; 

-- SelectAllIdsForTypeQuery 
  SELECT [Id] 
  FROM EventSources
  WHERE Type = @Type"; 

-- SelectVersionQuery 
  SELECT [Version] 
  FROM [EventSources] 
  WHERE [Id] = @id"; 

-- SelectLatestSnapshot 
  SELECT TOP 1 * FROM Snapshots 
  WHERE [EventSourceId]=@EventSourceId 
  ORDER BY Version DESC
  
-- UpdateEventSourceVersionQuery 
  UPDATE [EventSources] 
  SET [Version] = @NewVersion 
  WHERE [Id] = @id AND Version = @initialVersion"; 


