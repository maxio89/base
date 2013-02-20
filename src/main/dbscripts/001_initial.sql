SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


SET default_with_oids = false;

CREATE TABLE album (
    id bigint NOT NULL,
    large_picture bytea NOT NULL,
    small_picture bytea NOT NULL,
    artist_id bigint,
    ensemble_id bigint
);


CREATE SEQUENCE album_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE album_recording (
    album_id bigint NOT NULL,
    recording_id bigint NOT NULL
);


CREATE TABLE album_trans (
    id bigint NOT NULL,
    subtitle character varying(255),
    title character varying(255) NOT NULL,
    album_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE album_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE artist (
    id bigint NOT NULL,
    large_picture bytea NOT NULL,
    small_picture bytea NOT NULL,
    born_in_country_id bigint NOT NULL,
    lives_in_country_id bigint NOT NULL
);


CREATE TABLE artist_ensemble (
    ensemble_id bigint NOT NULL,
    artist_id bigint NOT NULL
);


CREATE SEQUENCE artist_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE artist_trans (
    id bigint NOT NULL,
    displayname character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    description character varying(10000) NOT NULL,
    lastname character varying(255) NOT NULL,
    artist_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE artist_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE city (
    id bigint NOT NULL,
    country_id bigint NOT NULL
);


CREATE SEQUENCE city_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE city_trans (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    city_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE city_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE composer (
    id bigint NOT NULL,
    large_picture bytea NOT NULL,
    small_picture bytea NOT NULL,
    period_id bigint
);


CREATE SEQUENCE composer_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE composer_trans (
    id bigint NOT NULL,
    name character varying(10000) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    composer_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE composer_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE concert (
    id bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    description character varying(10000),
    large_picture bytea,
    small_picture bytea,
    name character varying(255) NOT NULL,
    venue_part character varying(255),
    venue_id bigint NOT NULL
);


CREATE SEQUENCE concert_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE country (
    id bigint NOT NULL
);


CREATE SEQUENCE country_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE country_trans (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    country_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE country_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE ensemble (
    id bigint NOT NULL,
    country_id bigint NOT NULL,
    large_picture bytea NOT NULL,
    small_picture bytea NOT NULL
);


CREATE SEQUENCE ensemble_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE ensemble_trans (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(10000) NOT NULL,
    ensemble_id bigint NOT NULL,
    language_id bigint NOT NULL
);


CREATE SEQUENCE ensemble_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE language (
    id bigint NOT NULL,
    iso_639_1 character varying(2) NOT NULL,
    iso_639_2 character varying(3) NOT NULL
);


CREATE SEQUENCE language_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE language_trans (
    id bigint NOT NULL,
    language_text character varying(255) NOT NULL,
    language_id bigint NOT NULL,
    translated_object_id bigint NOT NULL
);


CREATE SEQUENCE language_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE opus (
    id bigint NOT NULL,
    number integer,
    oups_text character varying(1024),
    post_humous boolean NOT NULL,
    composer_id bigint
);


CREATE SEQUENCE opus_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE period (
    id bigint NOT NULL,
    period_end integer,
    period_start integer NOT NULL
);


CREATE SEQUENCE period_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE period_trans (
    id bigint NOT NULL,
    description character varying(10000) NOT NULL,
    title character varying(255) NOT NULL,
    language_id bigint NOT NULL,
    period_id bigint NOT NULL
);


CREATE SEQUENCE period_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE piece (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    composer_id bigint NOT NULL,
    opus_id bigint,
    "position" integer NOT NULL,
    piece_group_id bigint,
    transcriber_id bigint
);


CREATE TABLE piece_group (
    id bigint NOT NULL,
    composer_id bigint NOT NULL,
    opus_id bigint,
    transcriber_id bigint NOT NULL,
    piece_groupe_type_id bigint NOT NULL
);


CREATE SEQUENCE piece_group_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE piece_group_trans (
    id bigint NOT NULL,
    description character varying(10000),
    title character varying(255) NOT NULL,
    language_id bigint NOT NULL,
    piece_group_id bigint NOT NULL
);


CREATE SEQUENCE piece_group_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE piece_group_type (
    id bigint NOT NULL
);


CREATE SEQUENCE piece_group_type_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE piece_group_type_trans (
    id bigint NOT NULL,
    term character varying(255) NOT NULL,
    description character varying(10000),
    language_id bigint NOT NULL,
    piece_group_type_id bigint NOT NULL
);


CREATE SEQUENCE piece_group_type_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE SEQUENCE piece_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE piece_trans (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(10000),
    language_id bigint NOT NULL,
    piece_id bigint NOT NULL
);


CREATE SEQUENCE piece_trans_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE producer (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


CREATE SEQUENCE producer_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE recording (
    id bigint NOT NULL,
    duration integer NOT NULL,
    stream_url character varying(1024) NOT NULL,
    artist_id bigint,
    concert_id bigint,
    ensemble_id bigint,
    piece_id bigint NOT NULL,
    producer_id bigint NOT NULL,
    CONSTRAINT recording_duration_check CHECK ((duration >= 1))
);


CREATE SEQUENCE recording_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE setting (
    id character varying(255) NOT NULL,
    value character varying(255)
);


CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    country_id bigint NOT NULL
);


CREATE SEQUENCE users_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE venue (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    city_id bigint NOT NULL
);


CREATE SEQUENCE venue_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ONLY album
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);


ALTER TABLE ONLY album_recording
    ADD CONSTRAINT album_recording_pkey PRIMARY KEY (album_id, recording_id);


ALTER TABLE ONLY album_trans
    ADD CONSTRAINT album_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY artist_ensemble
    ADD CONSTRAINT artist_ensemble_pkey PRIMARY KEY (ensemble_id, artist_id);


ALTER TABLE ONLY artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (id);


ALTER TABLE ONLY artist_trans
    ADD CONSTRAINT artist_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


ALTER TABLE ONLY city_trans
    ADD CONSTRAINT city_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY composer
    ADD CONSTRAINT composer_pkey PRIMARY KEY (id);


ALTER TABLE ONLY composer_trans
    ADD CONSTRAINT composer_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY concert
    ADD CONSTRAINT concert_pkey PRIMARY KEY (id);


ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);


ALTER TABLE ONLY country_trans
    ADD CONSTRAINT country_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY ensemble
    ADD CONSTRAINT ensemble_pkey PRIMARY KEY (id);


ALTER TABLE ONLY ensemble_trans
    ADD CONSTRAINT ensemble_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);


ALTER TABLE ONLY language_trans
    ADD CONSTRAINT language_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY opus
    ADD CONSTRAINT opus_pkey PRIMARY KEY (id);


ALTER TABLE ONLY period
    ADD CONSTRAINT period_pkey PRIMARY KEY (id);


ALTER TABLE ONLY period_trans
    ADD CONSTRAINT period_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece_group
    ADD CONSTRAINT piece_group_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece_group_trans
    ADD CONSTRAINT piece_group_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece_group_type
    ADD CONSTRAINT piece_group_type_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece_group_type_trans
    ADD CONSTRAINT piece_group_type_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece
    ADD CONSTRAINT piece_pkey PRIMARY KEY (id);


ALTER TABLE ONLY piece_trans
    ADD CONSTRAINT piece_trans_pkey PRIMARY KEY (id);


ALTER TABLE ONLY producer
    ADD CONSTRAINT producer_pkey PRIMARY KEY (id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT recording_pkey PRIMARY KEY (id);


ALTER TABLE ONLY setting
    ADD CONSTRAINT setting_pkey PRIMARY KEY (id);


ALTER TABLE ONLY album_trans
    ADD CONSTRAINT unique___album_trans___translation_per_language UNIQUE (language_id, album_id);


ALTER TABLE ONLY artist_trans
    ADD CONSTRAINT unique___artist_trans___translation_per_language UNIQUE (language_id, artist_id);


ALTER TABLE ONLY city_trans
    ADD CONSTRAINT unique___city_trans___translation_per_language UNIQUE (language_id, city_id);


ALTER TABLE ONLY composer_trans
    ADD CONSTRAINT unique___composer_trans___translation_per_language UNIQUE (language_id, composer_id);


ALTER TABLE ONLY country_trans
    ADD CONSTRAINT unique___country_trans___translation_per_language UNIQUE (language_id, country_id);


ALTER TABLE ONLY ensemble_trans
    ADD CONSTRAINT unique___ensemble_trans___translation_per_language UNIQUE (language_id, ensemble_id);


ALTER TABLE ONLY language
    ADD CONSTRAINT unique___language___iso_639_1 UNIQUE (iso_639_1);


ALTER TABLE ONLY language
    ADD CONSTRAINT unique___language___iso_639_2 UNIQUE (iso_639_2);


ALTER TABLE ONLY language_trans
    ADD CONSTRAINT unique___language_trans___translation_per_language UNIQUE (language_id, translated_object_id);


ALTER TABLE ONLY period_trans
    ADD CONSTRAINT unique___period_trans___translation_per_language UNIQUE (language_id, period_id);


ALTER TABLE ONLY piece_group_trans
    ADD CONSTRAINT unique___piece_group_trans___translation_per_language UNIQUE (language_id, piece_group_id);


ALTER TABLE ONLY piece_group_type_trans
    ADD CONSTRAINT unique___piece_group_type_trans___translation_per_language UNIQUE (language_id, piece_group_type_id);


ALTER TABLE ONLY piece_trans
    ADD CONSTRAINT unique___piece_trans___translation_per_language UNIQUE (language_id, piece_id);


ALTER TABLE ONLY users
    ADD CONSTRAINT unique___users___email UNIQUE (email);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


ALTER TABLE ONLY venue
    ADD CONSTRAINT venue_pkey PRIMARY KEY (id);


ALTER TABLE ONLY album
    ADD CONSTRAINT fk___album___artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE;


ALTER TABLE ONLY album
    ADD CONSTRAINT fk___album___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id) ON DELETE CASCADE;


ALTER TABLE ONLY album_recording
    ADD CONSTRAINT fk___album_recording___album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE;


ALTER TABLE ONLY album_recording
    ADD CONSTRAINT fk___album_recording___recording FOREIGN KEY (recording_id) REFERENCES recording(id) ON DELETE CASCADE;


ALTER TABLE ONLY album_trans
    ADD CONSTRAINT fk___album_trans___album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE;


ALTER TABLE ONLY album_trans
    ADD CONSTRAINT fk___album_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY artist
    ADD CONSTRAINT fk___artist_country_of_birth FOREIGN KEY (born_in_country_id) REFERENCES country(id);


ALTER TABLE ONLY artist
    ADD CONSTRAINT fk___artist_country_of_residence FOREIGN KEY (lives_in_country_id) REFERENCES country(id);


ALTER TABLE ONLY artist_ensemble
    ADD CONSTRAINT fk___artist_ensemble___artist FOREIGN KEY (artist_id) REFERENCES artist(id);


ALTER TABLE ONLY artist_ensemble
    ADD CONSTRAINT fk___artist_ensemble___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id);


ALTER TABLE ONLY artist_trans
    ADD CONSTRAINT fk___artist_trans___artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE;


ALTER TABLE ONLY artist_trans
    ADD CONSTRAINT fk___artist_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY city
    ADD CONSTRAINT fk___city___country FOREIGN KEY (country_id) REFERENCES country(id);


ALTER TABLE ONLY city_trans
    ADD CONSTRAINT fk___city_trans___city FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE;


ALTER TABLE ONLY city_trans
    ADD CONSTRAINT fk___city_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY composer
    ADD CONSTRAINT fk___composer___period FOREIGN KEY (period_id) REFERENCES period(id);


ALTER TABLE ONLY composer_trans
    ADD CONSTRAINT fk___composer_trans___composer FOREIGN KEY (composer_id) REFERENCES composer(id) ON DELETE CASCADE;


ALTER TABLE ONLY composer_trans
    ADD CONSTRAINT fk___composer_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY concert
    ADD CONSTRAINT fk___concert___venue FOREIGN KEY (venue_id) REFERENCES venue(id);


ALTER TABLE ONLY country_trans
    ADD CONSTRAINT fk___country_trans___country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE;


ALTER TABLE ONLY country_trans
    ADD CONSTRAINT fk___country_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY ensemble
    ADD CONSTRAINT fk___ensemble___country FOREIGN KEY (country_id) REFERENCES country(id);


ALTER TABLE ONLY ensemble_trans
    ADD CONSTRAINT fk___ensemble_trans___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id) ON DELETE CASCADE;


ALTER TABLE ONLY ensemble_trans
    ADD CONSTRAINT fk___ensemble_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY language_trans
    ADD CONSTRAINT fk___language_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY language_trans
    ADD CONSTRAINT fk___language_trans___translated_object FOREIGN KEY (translated_object_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY opus
    ADD CONSTRAINT fk___opus___composer FOREIGN KEY (composer_id) REFERENCES composer(id);


ALTER TABLE ONLY period_trans
    ADD CONSTRAINT fk___period_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY period_trans
    ADD CONSTRAINT fk___period_trans___period FOREIGN KEY (period_id) REFERENCES period(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece
    ADD CONSTRAINT fk___piece___composer FOREIGN KEY (composer_id) REFERENCES composer(id);


ALTER TABLE ONLY piece
    ADD CONSTRAINT fk___piece___opus FOREIGN KEY (opus_id) REFERENCES opus(id);


ALTER TABLE ONLY piece
    ADD CONSTRAINT fk___piece___piece_group FOREIGN KEY (piece_group_id) REFERENCES piece_group(id);


ALTER TABLE ONLY piece
    ADD CONSTRAINT fk___piece___transcriber FOREIGN KEY (transcriber_id) REFERENCES composer(id);


ALTER TABLE ONLY piece_group
    ADD CONSTRAINT fk___piece_group___composer FOREIGN KEY (composer_id) REFERENCES composer(id);


ALTER TABLE ONLY piece_group
    ADD CONSTRAINT fk___piece_group___opus FOREIGN KEY (opus_id) REFERENCES opus(id);


ALTER TABLE ONLY piece_group
    ADD CONSTRAINT fk___piece_group___piece_group_type FOREIGN KEY (piece_groupe_type_id) REFERENCES piece_group_type(id);


ALTER TABLE ONLY piece_group
    ADD CONSTRAINT fk___piece_group___transcriber FOREIGN KEY (transcriber_id) REFERENCES composer(id);


ALTER TABLE ONLY piece_group_trans
    ADD CONSTRAINT fk___piece_group_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece_group_trans
    ADD CONSTRAINT fk___piece_group_trans___piece_group_type FOREIGN KEY (piece_group_id) REFERENCES piece_group(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece_group_type_trans
    ADD CONSTRAINT fk___piece_group_type_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece_group_type_trans
    ADD CONSTRAINT fk___piece_group_type_trans___piece_group_type FOREIGN KEY (piece_group_type_id) REFERENCES piece_group_type(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece_trans
    ADD CONSTRAINT fk___piece_trans___language FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE;


ALTER TABLE ONLY piece_trans
    ADD CONSTRAINT fk___piece_trans___piece FOREIGN KEY (piece_id) REFERENCES piece(id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT fk___recording___artist FOREIGN KEY (artist_id) REFERENCES artist(id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT fk___recording___concert FOREIGN KEY (concert_id) REFERENCES concert(id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT fk___recording___ensemble FOREIGN KEY (ensemble_id) REFERENCES ensemble(id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT fk___recording___piece FOREIGN KEY (piece_id) REFERENCES piece(id);


ALTER TABLE ONLY recording
    ADD CONSTRAINT fk___recording___producer FOREIGN KEY (producer_id) REFERENCES producer(id);


ALTER TABLE ONLY users
    ADD CONSTRAINT fk___users___country FOREIGN KEY (country_id) REFERENCES country(id);


ALTER TABLE ONLY venue
    ADD CONSTRAINT fk___venue___city FOREIGN KEY (city_id) REFERENCES city(id);


