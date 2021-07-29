-- Load the default values in for the test data set.

INSERT INTO tool_type (id, tool_type) VALUES (1, 'Ladder');
INSERT INTO tool_type (id, tool_type) VALUES (2, 'Chainsaw');
INSERT INTO tool_type (id, tool_type) VALUES (3, 'Jackhammer');

INSERT INTO tool (id, tool_type_id, brand, tool_code) VALUES (1, 1, 'Werner', 'LADW');
INSERT INTO tool (id, tool_type_id, brand, tool_code) VALUES (2, 2, 'Stihl', 'CHNS');
INSERT INTO tool (id, tool_type_id, brand, tool_code) VALUES (3, 3, 'Ridgid', 'JAKR');
INSERT INTO tool (id, tool_type_id, brand, tool_code) VALUES (4, 3, 'DeWalt', 'JAKD');

INSERT INTO rental_charge (id, tool_type_id, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES (1, 1, 1.99, true, true, false);
INSERT INTO rental_charge (id, tool_type_id, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES (2, 2, 1.49, true, false, true);
INSERT INTO rental_charge (id, tool_type_id, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES (3, 3, 2.99, true, false, false);