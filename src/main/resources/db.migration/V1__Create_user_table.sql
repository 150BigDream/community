CREATE CACHED TABLE USER(
    ID INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_8F7A2924_33D0_4E8A_90A8_8053A05DD83A" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_8F7A2924_33D0_4E8A_90A8_8053A05DD83A",
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)
