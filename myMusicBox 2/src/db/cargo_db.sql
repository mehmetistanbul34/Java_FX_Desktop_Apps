PGDMP                     	    y            cargo_db    13.3    13.2 
    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    24759    cargo_db    DATABASE     S   CREATE DATABASE cargo_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';
    DROP DATABASE cargo_db;
                postgres    false            �            1259    24781    cargo    TABLE     3  CREATE TABLE public.cargo (
    id integer NOT NULL,
    customer_name character varying NOT NULL,
    customer_address character varying NOT NULL,
    users_id character varying NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    cargo_status boolean NOT NULL
);
    DROP TABLE public.cargo;
       public         heap    postgres    false            �            1259    24773    users    TABLE     '  CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �          0    24781    cargo 
   TABLE DATA           q   COPY public.cargo (id, customer_name, customer_address, users_id, latitude, longitude, cargo_status) FROM stdin;
    public          postgres    false    201   �
       �          0    24773    users 
   TABLE DATA           [   COPY public.users (id, name, surname, username, password, latitude, longitude) FROM stdin;
    public          postgres    false    200   �
       4           2606    24788    cargo cargo_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.cargo DROP CONSTRAINT cargo_pkey;
       public            postgres    false    201            2           2606    24780    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    200            �   (   x�3䬨ବ�4�4ԃ�.#N4bc=c�4�=... ���      �   o   x�3��=�';13��p{bvnb6gnf2��i�i�e�鑘��"#Nsqq�p�P��l��	Ɯ�\�@��&'#�&�&\������%g$�iNC=CC0����� �9o     