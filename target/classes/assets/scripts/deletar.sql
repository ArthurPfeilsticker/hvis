TABELA PRODUTO

CREATE TABLE IF NOT EXISTS public.produto
(
    id integer NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
    nome character varying(128) COLLATE pg_catalog."default" NOT NULL,
    descricao character varying(512) COLLATE pg_catalog."default",
    preco double precision NOT NULL,
    quantidade integer,
    imagem character varying(512) COLLATE pg_catalog."default",
    CONSTRAINT produto_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.produto
    OWNER to hvis_admin;

