INSERT INTO users (id, email, firstname, lastname, password_digest, registration_date, active, role)
  VALUES (nextval('users_id_sequence'),'rafal.gielczowski@itcrowd.pl', 'Rafal', 'RRRRR', '44f437ced647ec3f40fa0841041871cd', current_timestamp, true, 'ADMIN');
INSERT INTO users (id, email, firstname, lastname, password_digest, registration_date, active, role)
  VALUES (nextval('users_id_sequence'),'rafal.koziol@itcrowd.pl', 'Rafal', 'K.', '44f437ced647ec3f40fa0841041871cd', current_timestamp, true, 'CLIENT');
