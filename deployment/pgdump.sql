--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: zhparser; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS zhparser WITH SCHEMA public;


--
-- Name: EXTENSION zhparser; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION zhparser IS 'a parser for full-text search of Chinese';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: chinese; Type: TEXT SEARCH CONFIGURATION; Schema: public; Owner: root
--

CREATE TEXT SEARCH CONFIGURATION public.chinese (
PARSER = public.zhparser );

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR a WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR e WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR i WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR l WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR n WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.chinese
ADD MAPPING FOR v WITH simple;


ALTER TEXT SEARCH CONFIGURATION public.chinese OWNER TO root;

--
-- Name: parser_name; Type: TEXT SEARCH CONFIGURATION; Schema: public; Owner: root
--

CREATE TEXT SEARCH CONFIGURATION public.parser_name (
PARSER = public.zhparser );

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR a WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR e WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR i WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR j WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR l WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR n WITH simple;

ALTER TEXT SEARCH CONFIGURATION public.parser_name
ADD MAPPING FOR v WITH simple;


ALTER TEXT SEARCH CONFIGURATION public.parser_name OWNER TO root;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: s_keyword_data; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.s_keyword_data (
  id character(36) NOT NULL,
  k_content character varying(8),
  k_count integer
);


ALTER TABLE public.s_keyword_data OWNER TO root;

--
-- Name: s_raw_data; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.s_raw_data (
  id character(36) NOT NULL,
  s_time bigint,
  s_depth smallint,
  s_length integer,
  s_url character varying(2048),
  s_content text,
  s_title character varying(128)
);


ALTER TABLE public.s_raw_data OWNER TO root;

--
-- Name: s_same_record; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.s_same_record (
  id character(36) NOT NULL,
  s_origin_id character(36),
  s_same_id character(36),
  s_hd smallint
);


ALTER TABLE public.s_same_record OWNER TO root;

--
-- Name: s_search_data; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.s_search_data (
  id character(36) NOT NULL,
  s_title character varying(128),
  s_content character varying(2047),
  s_url character varying(2048),
  s_time bigint,
  s_simhash bigint,
  s_img_url character varying(2048),
  s_source character varying(16),
  s_raw_id character(36)
);


ALTER TABLE public.s_search_data OWNER TO root;

--
-- Name: s_search_history; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.s_search_history (
  id character(36) NOT NULL,
  s_ip character varying(15),
  s_time bigint,
  k_content character varying(64)
);


ALTER TABLE public.s_search_history OWNER TO root;

--
-- Name: s_keyword_data s_keyword_data_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_keyword_data
ADD CONSTRAINT s_keyword_data_pkey PRIMARY KEY (id);


--
-- Name: s_raw_data s_raw_data_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_raw_data
ADD CONSTRAINT s_raw_data_pkey PRIMARY KEY (id);


--
-- Name: s_same_record s_same_record_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_same_record
ADD CONSTRAINT s_same_record_pkey PRIMARY KEY (id);


--
-- Name: s_search_data s_search_data_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_search_data
ADD CONSTRAINT s_search_data_pkey PRIMARY KEY (id);


--
-- Name: s_search_history s_search_history_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_search_history
ADD CONSTRAINT s_search_history_pkey PRIMARY KEY (id);


--
-- Name: tsv_idx; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX tsv_idx ON public.s_search_data USING gin (to_tsvector('public.chinese'::regconfig, ((s_title)::text || (s_content)::text)));


--
-- Name: s_same_record s_same_record_s_origin_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_same_record
ADD CONSTRAINT s_same_record_s_origin_id_fkey FOREIGN KEY (s_origin_id) REFERENCES public.s_search_data(id);


--
-- Name: s_same_record s_same_record_s_same_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_same_record
ADD CONSTRAINT s_same_record_s_same_id_fkey FOREIGN KEY (s_same_id) REFERENCES public.s_search_data(id);


--
-- Name: s_search_data s_search_data_s_raw_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.s_search_data
ADD CONSTRAINT s_search_data_s_raw_id_fkey FOREIGN KEY (s_raw_id) REFERENCES public.s_raw_data(id);


--
-- PostgreSQL database dump complete
--
