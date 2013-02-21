ALTER TABLE album_recording ADD CONSTRAINT fk___album_recording___album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE;
ALTER TABLE album_recording ADD CONSTRAINT fk___album_recording___recording FOREIGN KEY (recording_id) REFERENCES recording(id) ON DELETE CASCADE;
ALTER TABLE album_trans ADD CONSTRAINT fk___album_trans___album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE;
ALTER TABLE album_trans ADD CONSTRAINT fk___album_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE album ADD CONSTRAINT fk___album___artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE;
ALTER TABLE album ADD CONSTRAINT fk___album___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id) ON DELETE CASCADE;
ALTER TABLE artist_ensemble ADD CONSTRAINT fk___artist_ensemble___artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE;
ALTER TABLE artist_ensemble ADD CONSTRAINT fk___artist_ensemble___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id) ON DELETE CASCADE;
ALTER TABLE artist_trans ADD CONSTRAINT fk___artist_trans___artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE;
ALTER TABLE artist_trans ADD CONSTRAINT fk___artist_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE artist ADD CONSTRAINT fk___artist_country_of_birth FOREIGN KEY (born_in_country_id) REFERENCES country(id);
ALTER TABLE artist ADD CONSTRAINT fk___artist_country_of_residence FOREIGN KEY (lives_in_country_id) REFERENCES country(id);
ALTER TABLE city_trans ADD CONSTRAINT fk___city_trans___city FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE;
ALTER TABLE city_trans ADD CONSTRAINT fk___city_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE city ADD CONSTRAINT fk___city___country FOREIGN KEY (country_id) REFERENCES country(id);
ALTER TABLE composer_trans ADD CONSTRAINT fk___composer_trans___composer FOREIGN KEY (composer_id) REFERENCES composer(id) ON DELETE CASCADE;
ALTER TABLE composer_trans ADD CONSTRAINT fk___composer_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE composer ADD CONSTRAINT fk___composer___period FOREIGN KEY (period_id) REFERENCES period(id);
ALTER TABLE concert ADD CONSTRAINT fk___concert___venue FOREIGN KEY (venue_id) REFERENCES venue(id);
ALTER TABLE country_trans ADD CONSTRAINT fk___country_trans___country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE;
ALTER TABLE country_trans ADD CONSTRAINT fk___country_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE ensemble_trans ADD CONSTRAINT fk___ensemble_trans___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id) ON DELETE CASCADE;
ALTER TABLE ensemble_trans ADD CONSTRAINT fk___ensemble_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE ensemble ADD CONSTRAINT fk___ensemble___country FOREIGN KEY (country_id) REFERENCES country(id);
ALTER TABLE language_trans ADD CONSTRAINT fk___language_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE language_trans ADD CONSTRAINT fk___language_trans___translated_object FOREIGN KEY (translated_object_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE opus ADD CONSTRAINT fk___opus___composer FOREIGN KEY (composer_id) REFERENCES composer(id) ON DELETE CASCADE;
ALTER TABLE period_trans ADD CONSTRAINT fk___period_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE period_trans ADD CONSTRAINT fk___period_trans___period FOREIGN KEY (period_id) REFERENCES period(id) ON DELETE CASCADE;
ALTER TABLE piece_group_trans ADD CONSTRAINT fk___piece_group_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE piece_group_trans ADD CONSTRAINT fk___piece_group_trans___piece_group_type FOREIGN KEY (piece_group_id) REFERENCES piece_group(id) ON DELETE CASCADE;
ALTER TABLE piece_group_type_trans ADD CONSTRAINT fk___piece_group_type_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE piece_group_type_trans ADD CONSTRAINT fk___piece_group_type_trans___piece_group_type FOREIGN KEY (piece_group_type_id) REFERENCES piece_group_type(id) ON DELETE CASCADE;
ALTER TABLE piece_group ADD CONSTRAINT fk___piece_group___composer FOREIGN KEY (composer_id) REFERENCES composer(id) ON DELETE CASCADE;
ALTER TABLE piece_group ADD CONSTRAINT fk___piece_group___opus FOREIGN KEY (opus_id) REFERENCES opus(id) ON DELETE SET NULL;
ALTER TABLE piece_group ADD CONSTRAINT fk___piece_group___piece_group_type FOREIGN KEY (piece_groupe_type_id) REFERENCES piece_group_type(id);
ALTER TABLE piece_group ADD CONSTRAINT fk___piece_group___transcribed_from FOREIGN KEY (transcribed_from_id) REFERENCES composer(id) ON DELETE SET NULL;
ALTER TABLE piece_trans ADD CONSTRAINT fk___piece_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;
ALTER TABLE piece_trans ADD CONSTRAINT fk___piece_trans___piece FOREIGN KEY (piece_id) REFERENCES piece(id) ON DELETE CASCADE;
ALTER TABLE piece ADD CONSTRAINT fk___piece___composer FOREIGN KEY (composer_id) REFERENCES composer(id) ON DELETE CASCADE;
ALTER TABLE piece ADD CONSTRAINT fk___piece___opus FOREIGN KEY (opus_id) REFERENCES opus(id) ON DELETE SET NULL;
ALTER TABLE piece ADD CONSTRAINT fk___piece___piece_group FOREIGN KEY (piece_group_id) REFERENCES piece_group(id) ON DELETE SET NULL;
ALTER TABLE piece ADD CONSTRAINT fk___piece___transcribed_from FOREIGN KEY (transcribed_from_id) REFERENCES composer(id) ON DELETE SET NULL;
ALTER TABLE recording ADD CONSTRAINT fk___recording___artist FOREIGN KEY (artist_id) REFERENCES artist(id);
ALTER TABLE recording ADD CONSTRAINT fk___recording___concert FOREIGN KEY (concert_id) REFERENCES concert(id);
ALTER TABLE recording ADD CONSTRAINT fk___recording___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id);
ALTER TABLE recording ADD CONSTRAINT fk___recording___piece FOREIGN KEY (piece_id) REFERENCES piece(id);
ALTER TABLE recording ADD CONSTRAINT fk___recording___producer FOREIGN KEY (producer_id) REFERENCES producer(id);
ALTER TABLE users ADD CONSTRAINT fk___users___country FOREIGN KEY (country_id) REFERENCES country(id);
ALTER TABLE venue ADD CONSTRAINT fk___venue___city FOREIGN KEY (city_id) REFERENCES city(id);