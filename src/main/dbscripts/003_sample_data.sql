INSERT INTO users (id, email, firstname, lastname, password_digest, registration_date, active, role)
  VALUES (nextval('users_id_sequence'),'rafal.gielczowski@itcrowd.pl', 'Rafal', 'RRRRR', '47bce5c74f589f4867dbd57e9ca9f808', current_timestamp, true, 'ADMIN');
INSERT INTO users (id, email, firstname, lastname, password_digest, registration_date, active, role)
  VALUES (nextval('users_id_sequence'),'piotr.kozlowski@itcrowd.pl', 'Piotr', 'K.', '47bce5c74f589f4867dbd57e9ca9f808', current_timestamp, true, 'CLIENT');
