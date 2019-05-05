DELIMITER //

CREATE TRIGGER Delete_horse_starting_price
BEFORE DELETE ON horse_starting_price
FOR EACH ROW 
BEGIN
  DECLARE location VARCHAR(200);
  DECLARE horse_name VARCHAR(50);
  DECLARE time DATETIME;
  DECLARE sp FLOAT(6,3);
  DECLARE race_cur_finished INT DEFAULT false;
  DECLARE race_cur CURSOR FOR SELECT horse.name, race.location, race.time, horse_starting_price.sp FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id INNER JOIN race ON race.id = horse_starting_price.race_id WHERE horse_starting_price.id = old.id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET race_cur_finished = TRUE;
  
  OPEN race_cur;
  get_bet: LOOP
			FETCH race_cur INTO horse_name, location, time, sp;
			IF race_cur_finished THEN 
					 LEAVE get_bet;
			END IF;
		    BLOCK2: BEGIN
					    DECLARE bet_id BIGINT(5);
						DECLARE bet_type VARCHAR(30);
						DECLARE bet_finished INT DEFAULT false;
						DECLARE first_horse_id BIGINT(5);
                        DECLARE second_horse_id BIGINT(5);
						DECLARE bet_cur CURSOR FOR SELECT bet.id, bet.type, bet.first_horse_id, bet.second_horse_id FROM bet WHERE bet.first_horse_id = OLD.ID OR bet.second_horse_id = OLD.ID;
						DECLARE CONTINUE HANDLER FOR NOT FOUND SET bet_finished = TRUE;
                                                        
						OPEN bet_cur;
						get_user_bet: LOOP
						FETCH bet_cur INTO bet_id, bet_type, first_horse_id, second_horse_id;
						IF bet_finished THEN 
							 LEAVE get_user_bet;
						END IF;
					    BLOCK4: BEGIN
							   DECLARE count_records_1 INT;
							   DECLARE result_finished INT DEFAULT false;
							   DECLARE result_cur CURSOR FOR SELECT result.id FROM result WHERE result.race_id = race_id;
							   DECLARE CONTINUE HANDLER FOR NOT FOUND SET result_finished = TRUE;
                                                                                                      
							   OPEN result_cur;
							   SELECT FOUND_ROWS() into count_records_1;
							   BLOCK5: BEGIN
							           DECLARE id BIGINT(5);
							           DECLARE have_sp TINYINT(1);
								       DECLARE user_id BIGINT(5);
								       DECLARE bet_money DECIMAL(10,3);
                                       DECLARE coefficient FLOAT(6,3);
								       DECLARE user_bet_finished INT DEFAULT false;
								       DECLARE user_bet_cur CURSOR FOR SELECT user_bet.id, user_bet.user_id, user_bet.bet_money, user_bet.have_sp, user_bet.coefficient FROM user_bet WHERE user_bet.bet_id = bet_id;
								       DECLARE CONTINUE HANDLER FOR NOT FOUND SET user_bet_finished = TRUE;
                                                                                 
								       OPEN user_bet_cur;
											get_user: LOOP
													   FETCH user_bet_cur INTO id, user_id, bet_money, have_sp, coefficient;
									                   IF user_bet_finished THEN 
										                   LEAVE get_user;
									                   END IF;
									                   IF bet_type = 'Win' OR bet_type = 'Place' OR bet_type = 'Show' THEN
																IF count_records_1 = 0 THEN
																	CALL UpdateBetRefund(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name);
														        ELSE
																    CALL UpdateBetInResult(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name, sp, coefficient);
																END IF;			
													   ELSE 
										               BLOCK6: BEGIN
															   DECLARE first_horse_name VARCHAR(50);
													           DECLARE second_horse_name VARCHAR(50);
													           DECLARE horse_finished INT DEFAULT false;
															   DECLARE first_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = first_horse_id;
													           DECLARE second_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = second_horse_id;
													           DECLARE CONTINUE HANDLER FOR NOT FOUND SET horse_finished = TRUE;
                                                                                                           
													           OPEN first_horse_cur;
													           OPEN second_horse_cur;
													           get_horse: LOOP
																		     FETCH first_horse_cur INTO first_horse_name;
																			 FETCH second_horse_cur INTO second_horse_name;
																             IF user_bet_finished THEN 
																		         LEAVE get_horse;
																	         END IF;
																			  IF count_records_1 = 0 THEN
																					CALL UpdateBetRefund(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name));
																			  ELSE 
																				    CALL UpdateBetInResult(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name), 0, coefficient);
																			   END IF;
																			 END LOOP get_horse;
																	         CLOSE first_horse_cur;
																	         CLOSE second_horse_cur;
													 END BLOCK6;
											         END IF;
							            END LOOP get_user;
				                    CLOSE user_bet_cur;
		                  END BLOCK5;
                               
		           CLOSE result_cur;
	            END BLOCK4;
	       END LOOP get_user_bet;
       CLOSE bet_cur; 
     END BLOCK2;
  END LOOP get_bet;
  CLOSE race_cur;
END; //

CREATE TRIGGER Delete_horse
BEFORE DELETE ON horse
FOR EACH ROW 
BEGIN
  DECLARE location VARCHAR(200);
  DECLARE sp_id BIGINT(5);
  DECLARE race_id BIGINT(5);
  DECLARE horse_name VARCHAR(50);
  DECLARE sp FLOAT(6,3);
  DECLARE time DATETIME;
  DECLARE race_cur_finished INT DEFAULT false;
  DECLARE race_cur CURSOR FOR SELECT horse_starting_price.id, horse.name, race.location, race.time, horse_starting_price.race_id, horse_starting_price.sp  FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id INNER JOIN race ON race.id = horse_starting_price.race_id WHERE horse.id = old.id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET race_cur_finished = TRUE;
  
  OPEN race_cur;
  get_bet: LOOP
			FETCH race_cur INTO sp_id, horse_name, location, time, race_id, sp;
			IF race_cur_finished THEN 
					 LEAVE get_bet;
			END IF;
		    BLOCK2: BEGIN
					    DECLARE bet_id BIGINT(5);
						DECLARE bet_type VARCHAR(30);
						DECLARE bet_finished INT DEFAULT false;
						DECLARE first_horse_id BIGINT(5);
                        DECLARE second_horse_id BIGINT(5);
						DECLARE bet_cur CURSOR FOR SELECT bet.id, bet.type, bet.first_horse_id, bet.second_horse_id FROM bet WHERE bet.first_horse_id = sp_id OR bet.second_horse_id = sp_id;
						DECLARE CONTINUE HANDLER FOR NOT FOUND SET bet_finished = TRUE;
                                                        
						OPEN bet_cur;
						get_user_bet: LOOP
						FETCH bet_cur INTO bet_id, bet_type, first_horse_id, second_horse_id;
						IF bet_finished THEN 
							 LEAVE get_user_bet;
						END IF;
                        
					   BLOCK4: BEGIN
							   DECLARE count_records_1 INT;
							   DECLARE result_finished INT DEFAULT false;
							   DECLARE result_cur CURSOR FOR SELECT result.id FROM result WHERE result.race_id = race_id;
							   DECLARE CONTINUE HANDLER FOR NOT FOUND SET result_finished = TRUE;
                                                                                                      
							   OPEN result_cur;
							   SELECT FOUND_ROWS() into count_records_1;
                               
							   BLOCK5: BEGIN
									   DECLARE id BIGINT(5);
							           DECLARE have_sp TINYINT(1);
									   DECLARE user_id BIGINT(5);
								       DECLARE bet_money DECIMAL(10,3);
                                       DECLARE coefficient FLOAT(6,3);
								       DECLARE user_bet_finished INT DEFAULT false;
								       DECLARE user_bet_cur CURSOR FOR SELECT user_bet.id, user_bet.user_id, user_bet.bet_money, user_bet.have_sp, user_bet.coefficient FROM user_bet WHERE user_bet.bet_id = bet_id;
								       DECLARE CONTINUE HANDLER FOR NOT FOUND SET user_bet_finished = TRUE;
                                                                                 
								       OPEN user_bet_cur;
									        get_user: LOOP
									                    FETCH user_bet_cur INTO id, user_id, bet_money, have_sp, coefficient;
									                    IF user_bet_finished THEN 
										                     LEAVE get_user;
														END IF;
									                    IF bet_type = 'Win' OR bet_type = 'Place' OR bet_type = 'Show' THEN
																IF count_records_1 = 0 THEN
																	CALL UpdateBetRefund(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name);
														        ELSE
																    CALL UpdateBetInResult(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name, sp, coefficient);
																END IF;		
									                    ELSE 
										                BLOCK6: BEGIN
										                        DECLARE first_horse_name VARCHAR(50);
													            DECLARE second_horse_name VARCHAR(50);
													            DECLARE horse_finished INT DEFAULT false;
													            DECLARE first_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = first_horse_id;
																DECLARE second_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = second_horse_id;
													            DECLARE CONTINUE HANDLER FOR NOT FOUND SET horse_finished = TRUE;
                                                                                                           
													            OPEN first_horse_cur;
													            OPEN second_horse_cur;
													            get_horse: LOOP
																	          FETCH first_horse_cur INTO first_horse_name;
																              FETCH second_horse_cur INTO second_horse_name;
																			  IF horse_finished THEN 
																					LEAVE get_horse;
																			  END IF;
																			  IF count_records_1 = 0 THEN
																					CALL UpdateBetRefund(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name));
																			  ELSE 
																				    CALL UpdateBetInResult(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name), 0, coefficient);
																			   END IF;
															END LOOP get_horse;
                                                            CLOSE first_horse_cur;
                                                            CLOSE second_horse_cur;
															END BLOCK6;
											                END IF;
                                      END LOOP get_user;
									CLOSE user_bet_cur;
		                        END BLOCK5;
                        CLOSE result_cur;
					END BLOCK4;
	       END LOOP get_user_bet;
       CLOSE bet_cur; 
     END BLOCK2;
  END LOOP get_bet;
  CLOSE race_cur;
END; //

CREATE TRIGGER Delete_race
BEFORE DELETE ON race
FOR EACH ROW 
BEGIN
  DECLARE location VARCHAR(200);
  DECLARE time DATETIME;
  DECLARE race_cur_finished INT DEFAULT false;
  DECLARE race_cur CURSOR FOR SELECT race.location, race.time FROM race WHERE race.id = old.id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET race_cur_finished = TRUE;
  
  OPEN race_cur;
  get_starting_price: LOOP
				        FETCH race_cur INTO location, time;
                        IF race_cur_finished THEN 
					       LEAVE get_starting_price;
						END IF;
						BLOCK2: BEGIN
								DECLARE horse_name VARCHAR(50);
                                DECLARE sp_id BIGINT(5);
                                DECLARE sp_cur_finished INT DEFAULT false;
                                DECLARE sp FLOAT(6,3);
                                DECLARE sp_cur CURSOR FOR SELECT horse_starting_price.id, horse.name, horse_starting_price.sp FROM horse_starting_price INNER JOIN horse ON horse_starting_price.horse_id = horse.id WHERE horse_starting_price.race_id = OLD.id;
                                DECLARE CONTINUE HANDLER FOR NOT FOUND SET sp_cur_finished = TRUE;
                                
                                OPEN sp_cur;
                                get_bet: LOOP
                                            FETCH sp_cur INTO sp_id, horse_name, sp;
										    IF sp_cur_finished THEN 
												LEAVE get_bet;
											END IF;
                                            BLOCK3: BEGIN
														DECLARE bet_id BIGINT(5);
                                                        DECLARE bet_type VARCHAR(30);
                                                        DECLARE bet_finished INT DEFAULT false;
                                                        DECLARE first_horse_id BIGINT(5);
                                                        DECLARE second_horse_id BIGINT(5);
                                                        DECLARE bet_cur CURSOR FOR SELECT bet.id, bet.type, bet.first_horse_id, bet.second_horse_id FROM bet WHERE bet.first_horse_id = sp_id OR bet.second_horse_id = sp_id;
                                                        DECLARE CONTINUE HANDLER FOR NOT FOUND SET bet_finished = TRUE;
                                                        
                                                        OPEN bet_cur;
														get_user_bet: LOOP
																		 FETCH bet_cur INTO bet_id, bet_type, first_horse_id, second_horse_id;
                                                                         IF bet_finished THEN 
																			 LEAVE get_user_bet;
																		 END IF;
                                                                         BLOCK4: BEGIN
                                                                                 DECLARE id BIGINT(5);
																				 DECLARE have_sp TINYINT(1);
																				 DECLARE user_id BIGINT(5);
                                                                                 DECLARE bet_money DECIMAL(10,3);
                                                                                 DECLARE coefficient FLOAT(6,3);
                                                                                 DECLARE user_bet_finished INT DEFAULT false;
                                                                                 DECLARE user_bet_cur CURSOR FOR SELECT user_bet.id, user_bet.user_id, user_bet.bet_money, user_bet.have_sp, user_bet.coefficient FROM user_bet WHERE user_bet.bet_id = bet_id;
                                                                                 DECLARE CONTINUE HANDLER FOR NOT FOUND SET user_bet_finished = TRUE;
                                                                                 
                                                                                 OPEN user_bet_cur;
                                                                                 get_user: LOOP
                                                                                              FETCH user_bet_cur INTO id, user_id, bet_money, have_sp, coefficient;
                                                                                              IF user_bet_finished THEN 
                                                                                                   LEAVE get_user;
																							  END IF;
                                                                                              BLOCK5: BEGIN
																									  DECLARE count_records_1 INT;
                                                                                                      DECLARE result_finished INT DEFAULT false;
                                                                                                      DECLARE result_cur CURSOR FOR SELECT result.id FROM result WHERE result.race_id = OLD.id;
                                                                                                      DECLARE CONTINUE HANDLER FOR NOT FOUND SET result_finished = TRUE;
                                                                                                      
                                                                                                      OPEN result_cur;
                                                                                                      SELECT FOUND_ROWS() into count_records_1;
																									
                                                                                                      
																							          IF bet_type = 'Win' OR bet_type = 'Place' OR bet_type = 'Show' THEN
																											 IF count_records_1 = 0 THEN
															                                                    CALL UpdateBetRefund(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name);
																											 ELSE
																												CALL UpdateBetInResult(id, have_sp, bet_money, user_id, location, time, bet_type, horse_name, sp, coefficient);
                                                                                                             END IF;
																							          ELSE 
                                                                                                      BLOCK6: BEGIN
																										   DECLARE first_horse_name VARCHAR(50);
                                                                                                           DECLARE second_horse_name VARCHAR(50);
                                                                                                           DECLARE horse_finished INT DEFAULT false;
																										   DECLARE first_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = first_horse_id;
																										   DECLARE second_horse_cur CURSOR FOR SELECT horse.name FROM horse_starting_price INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.race_id = OLD.id AND horse_starting_price.id = second_horse_id;
                                                                                                           DECLARE CONTINUE HANDLER FOR NOT FOUND SET horse_finished = TRUE;
                                                                                                           
                                                                                                           OPEN first_horse_cur;
																										
                                                                                                           OPEN second_horse_cur;
                                                                                                           get_horse: LOOP
                                                                                                                         FETCH first_horse_cur INTO first_horse_name;
                                                                                                                         FETCH second_horse_cur INTO second_horse_name;
                                                                                                                         IF horse_finished THEN 
																															 LEAVE get_horse;
																														 END IF;
                                                                                                                         IF count_records_1 = 0 THEN
                                                                                                                             CALL UpdateBetRefund(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name));
																														 ELSE 
                                                                                                                             CALL UpdateBetInResult(id, 0, bet_money, user_id, location, time, bet_type, CONCAT(first_horse_name, ', ', second_horse_name), 0, coefficient);
																														 END IF;
                                                                                                           END LOOP get_horse;
																										CLOSE first_horse_cur;
                                                                                                        CLOSE second_horse_cur;
                                                                                                   END BLOCK6;
																				              END IF;
																							  CLOSE result_cur;
                                                                                              END BLOCK5;
													
                                                                                 END LOOP get_user;
																				CLOSE user_bet_cur;
                                                                         END BLOCK4;
                                                        END LOOP get_user_bet;
													CLOSE bet_cur;
                                            END BLOCK3; 
								 END LOOP get_bet;
						CLOSE sp_cur;
				 END BLOCK2;
			END LOOP get_starting_price;
		CLOSE race_cur;
END; //

CREATE TRIGGER Consider_balance
AFTER INSERT ON result
FOR EACH ROW 
BEGIN 
  DECLARE starting_price FLOAT(6,3);
  DECLARE sp_id  BIGINT(5);
  DECLARE sp_finished INT DEFAULT FALSE;
  DECLARE horse_sp_cur CURSOR FOR 
				         SELECT horse_starting_price.id, horse_starting_price.sp 
						 FROM horse_starting_price 
                         WHERE horse_starting_price.race_id = NEW.race_id AND horse_starting_price.horse_id = NEW.horse_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sp_finished = TRUE;
  
  OPEN horse_sp_cur;
  get_horse_sp: LOOP
				   FETCH horse_sp_cur INTO sp_id, starting_price;
				   IF sp_finished THEN 
					   LEAVE get_horse_sp;
				   END IF;
                   BLOCK2: BEGIN
                       DECLARE bet_id_new BIGINT(5);
                       DECLARE type_bet varchar(20);
                       DECLARE first_horse_id BIGINT(5);
                       DECLARE second_horse_id BIGINT(5);
                       DECLARE bet_finished INT DEFAULT false;
                       DECLARE bet_cur CURSOR FOR SELECT bet.id, bet.type, bet.first_horse_id, bet.second_horse_id FROM bet WHERE bet.first_horse_id = sp_id OR bet.second_horse_id = sp_id;
                       DECLARE CONTINUE HANDLER FOR NOT FOUND SET bet_finished = TRUE;
                       
                       OPEN bet_cur;
                       get_bet: LOOP
								   FETCH bet_cur INTO bet_id_new, type_bet, first_horse_id, second_horse_id;
				                   IF bet_finished THEN 
									  LEAVE get_bet;
								   END IF;
								   BLOCK3: BEGIN
                                       DECLARE id_user BIGINT(5);
                                       DECLARE id_bet BIGINT(5);
                                       DECLARE have_sp TINYINT(1);
					                   DECLARE coefficient FLOAT(6,3);
                                       DECLARE bet_money DECIMAL(10, 3);
                                       DECLARE user_bet_finished INT DEFAULT false;
                                       DECLARE user_bet_cur CURSOR FOR SELECT user_bet.id, user_bet.user_id, user_bet.have_sp, user_bet.bet_money, user_bet.coefficient FROM user_bet WHERE user_bet.bet_id = bet_id_new;
                                       DECLARE CONTINUE HANDLER FOR NOT FOUND SET user_bet_finished = TRUE;
                                       
                                       OPEN user_bet_cur;
                                       get_user_bet: LOOP
									                    FETCH user_bet_cur INTO id_bet, id_user, have_sp, bet_money, coefficient;
														IF user_bet_finished THEN 
										                    LEAVE get_user_bet;
														END IF;
									         BLOCK4: BEGIN
                                                DECLARE balance DECIMAL(10,3);
                                                DECLARE user_cur_finished INT DEFAULT false;
                                                DECLARE user_cur CURSOR FOR SELECT user.balance FROM user WHERE user.id = id_user;
                                                DECLARE CONTINUE HANDLER FOR NOT FOUND SET user_cur_finished = TRUE;
                                                
                                                OPEN user_cur;
                                                get_user: LOOP
															FETCH user_cur INTO balance;
															IF user_cur_finished THEN 
															   LEAVE get_user;
									                        END IF;
                                                            IF type_bet = 'Win' THEN
															   IF NEW.place = 1 THEN
																  CALL UpdateBetWon(id_bet, id_user, have_sp, balance, bet_money, coefficient, starting_price);
															   ELSE 
																  CALL UpdateBetLost(id_bet);
															   END IF;
															END IF;
														    IF type_bet = 'Show' THEN
														       IF NEW.place = 1 OR NEW.place = 2 THEN
																  CALL UpdateBetWon(id_bet, id_user, have_sp, balance, bet_money, coefficient, starting_price);
															   ELSE 
																  CALL UpdateBetLost(id_bet);
															   END IF;
															END IF;
														    IF type_bet = 'Place' THEN
														       IF NEW.place = 1 OR NEW.place = 2 OR NEW.place = 3 THEN
																  CALL UpdateBetWon(id_bet, id_user, have_sp, balance, bet_money, coefficient, starting_price);
																ELSE 
																  CALL UpdateBetLost(id_bet);
                                                                END IF;
															END IF;
															IF type_bet = 'Opposite' OR type_bet = 'Exacta'  THEN
														       BLOCK5: BEGIN
																  DECLARE result_finished INT DEFAULT false;
                                                                  DECLARE count_records_1 INT;
                                                                  DECLARE count_records_2 INT;
																  DECLARE first_horse_place INT(11);
                                                                  DECLARE second_horse_place INT(11);
                                                                  DECLARE result_cur_first_horse CURSOR FOR SELECT result.place FROM horse_starting_price INNER JOIN result ON result.race_id = horse_starting_price.race_id AND result.horse_id = horse_starting_price.horse_id INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.id = first_horse_id AND result.race_id = NEW.race_id;
                                                                  DECLARE result_cur_second_horse CURSOR FOR SELECT result.place FROM horse_starting_price INNER JOIN result ON result.race_id = horse_starting_price.race_id AND result.horse_id = horse_starting_price.horse_id INNER JOIN horse ON horse.id = horse_starting_price.horse_id WHERE horse_starting_price.id = second_horse_id AND result.race_id = NEW.race_id;
                                                                  DECLARE CONTINUE HANDLER FOR NOT FOUND SET result_finished = TRUE;
                                                                  
                                                                  OPEN result_cur_first_horse;
                                                                  SELECT FOUND_ROWS() into count_records_1;
                                                                  
                                                                  OPEN result_cur_second_horse;
                                                                  SELECT FOUND_ROWS() into count_records_2;
														          IF count_records_1 != 0 AND count_records_2 != 0 THEN
																	 get_result: LOOP
                                                                                     FETCH result_cur_first_horse INTO first_horse_place;
                                                                                     FETCH result_cur_second_horse INTO second_horse_place;
																				     IF result_finished THEN 
															                             LEAVE get_user;
									                                                 END IF;
                                                                                     IF type_bet = 'Opposite' THEN
                                                                                         IF first_horse_place < second_horse_place THEN
																						     CALL UpdateBetWon(id_bet, id_user, 0, balance, bet_money, coefficient, 0);
																						 ELSE 
																							 CALL UpdateBetLost(id_bet);
                                                                                         END IF;
                                                                                     END IF;
																					 IF type_bet = 'Exacta' THEN
                                                                                         IF first_horse_place = 1 AND second_horse_place = 2 THEN
																						     CALL UpdateBetWon(id_bet, id_user, 0, balance, bet_money, coefficient, 0);
																						 ELSE 
																							 CALL UpdateBetLost(id_bet);
                                                                                         END IF;
                                                                                     END IF;
																				 END LOOP;
                                                                  END IF;
															   CLOSE result_cur_first_horse;
                                                               CLOSE result_cur_second_horse;
                                                               END BLOCK5;
															END IF;
														  END LOOP;
												CLOSE user_cur;
											 END BLOCK4;
								   END LOOP;
                     CLOSE user_bet_cur;
                   END BLOCK3;
			END LOOP get_bet;
    CLOSE bet_cur;
  END BLOCK2;
  END LOOP get_horse_sp;
  CLOSE horse_sp_cur;
END; //

CREATE PROCEDURE UpdateBetWon(IN bet_id BIGINT(5), IN user_id BIGINT(5), have_sp TINYINT(1), IN balance DECIMAL(10,3), IN bet_money DECIMAL(10,3), IN coefficient FLOAT(6,3), IN starting_price  FLOAT(6,3))
 BEGIN
   IF have_sp = 1 THEN
		IF coefficient <= starting_price THEN
		     UPDATE user SET user.balance = user.balance + bet_money * starting_price WHERE user.id = user_id;
		ELSE
			 UPDATE user SET user.balance = user.balance + bet_money * coefficient WHERE user.id = user_id;
		END IF;
   ELSE
		UPDATE user SET user.balance = user.balance + bet_money * coefficient WHERE user.id = user_id;   
   END IF;
 UPDATE user_bet SET user_bet.status = 'Сompleted bet won' WHERE user_bet.id = bet_id;  
 END //

CREATE PROCEDURE UpdateBetLost(IN bet_id BIGINT(5))
 BEGIN
    UPDATE user_bet SET user_bet.status = 'Сompleted bet lost' WHERE user_bet.id = bet_id;  
 END //

CREATE PROCEDURE UpdateBetRefund(IN bet_id BIGINT(5), IN have_sp TINYINT(1), IN bet_money DECIMAL(10,3),IN user_id BIGINT(5),IN location VARCHAR(50),IN date DATETIME, IN bet_type VARCHAR(20), IN horses VARCHAR(70))
 BEGIN
   IF have_sp = 1 THEN
	  UPDATE user SET user.balance = user.balance + bet_money WHERE user.id = user_id;
      UPDATE user_bet SET user_bet.status = Concat('Refund. Race location: ', location, ' Date: ', date, ' was cancelled. You haved strting price. Bet type: ', bet_type, ' Horse(s): ', horses)  WHERE user_bet.id = bet_id;  
   ELSE 
	  UPDATE user_bet SET user_bet.status = Concat('Refund. Race location: ', location, ' Date: ', date, ' was cancelled. You haven\'t strting price. Bet type: ', bet_type, ' Horse(s): ', horses)  WHERE user_bet.id = bet_id;    
   END IF;
   UPDATE user_bet SET user_bet.bet_id = NULL WHERE user_bet.id = bet_id;
 END //

CREATE PROCEDURE UpdateHorseRefund(IN horse_name VARCHAR(50), IN bet_id BIGINT(5), IN have_sp TINYINT(1), IN bet_money DECIMAL(10,3),IN user_id BIGINT(5),IN location VARCHAR(50),IN date DATETIME, IN bet_type VARCHAR(20), IN horses VARCHAR(70))
 BEGIN
   IF have_sp = 1 THEN
	  UPDATE user SET user.balance = user.balance + bet_money WHERE user.id = user_id;
      UPDATE user_bet SET user_bet.status = Concat('Refund. ', 'Horse ', horse_name, ' not involved in race.', ' You haved strting price. Bet type: ', bet_type, ' Horse(s): ', horses)  WHERE user_bet.id = bet_id;  
   ELSE 
	  UPDATE user_bet SET user_bet.status = Concat('Refund. ', 'Horse ', horse_name, ' not involved in race.', ' You haven\'t strting price. Bet type: ', bet_type, ' Horse(s): ', horses)  WHERE user_bet.id = bet_id;    
   END IF;
   UPDATE user_bet SET user_bet.bet_id = NULL WHERE user_bet.id = bet_id;
 END //

CREATE PROCEDURE UpdateBetInResult(IN bet_id BIGINT(5), IN have_sp TINYINT(1), IN bet_money DECIMAL(10,3),IN user_id BIGINT(5),IN location VARCHAR(50),IN date DATETIME, IN bet_type VARCHAR(20), IN horses VARCHAR(70), IN starting_price FLOAT(6,3), IN coefficient FLOAT(6,3))
 BEGIN
   IF have_sp = 1 THEN
      UPDATE user_bet SET user_bet.status = Concat(user_bet.status, '. Location: ', location, '. Date: ', date, '. Bet money: ', bet_money, '. You haved strting price: ', starting_price , '. Coefficient: ', coefficient, '. Bet type: ', bet_type, '. Horse(s): ', horses)  WHERE user_bet.id = bet_id;  
   ELSE 
      UPDATE user_bet SET user_bet.status = Concat(user_bet.status, '. Location: ', location, '. Date: ', date, '. Bet money: ', bet_money, '. You haven\'t strting price. ', '. Coefficient: ', coefficient, '. Bet type: ', bet_type, '. Horse(s): ', horses)  WHERE user_bet.id = bet_id;    
   END IF;
   UPDATE user_bet SET user_bet.bet_id = NULL WHERE user_bet.id = bet_id;
 END //

CREATE PROCEDURE MakeBet(IN bet_id BIGINT(5), IN user_id BIGINT(5), IN have_sp BOOLEAN, IN bet_money DECIMAL, IN coefficient FLOAT(6,3))
 BEGIN
     DECLARE exit handler FOR SQLEXCEPTION, SQLWARNING
     BEGIN
        ROLLBACK;
		RESIGNAL;
	 END;
     
     START TRANSACTION;
     UPDATE user SET user.balance = user.balance - bet_money WHERE user.id = user_id;
     INSERT INTO user_bet VALUES(default, bet_id, user_id, have_sp, bet_money, default, coefficient);
     
	 COMMIT;
 END //
 
DELIMITER ;


