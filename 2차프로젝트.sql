-- ȸ��
DROP TABLE BITUSER;

-- ������
DROP TABLE ADMIN;

-- ��������
DROP TABLE NOTICE;

-- ���
DROP TABLE REPLY;

-- �Խñ�
DROP TABLE BOARD;

-- �̹���
DROP TABLE IMAGE;

-- ���ɸ��
DROP TABLE FAVORITE;

-- ī�װ�
DROP TABLE CATEGORY;

-- ��������
DROP TABLE USERQNA;

-- ����������
DROP TABLE QNAREPLY;

-- ȸ��
CREATE TABLE BITUSER1 (
	ID      VARCHAR2(20)  NOT NULL, -- ID
	PWD     VARCHAR2(20)  NOT NULL, -- ��й�ȣ
	LOC     VARCHAR2(300) NOT NULL, -- ��ġ����
	NICK    VARCHAR2(20)  NOT NULL, -- �г���
	PROFILE VARCHAR2(100) NULL,      -- ������ ����
    RTIME   VARCHAR2(30)  NOT NULL,
    lat     number        not null,
    lon     number        not null
);

-- ȸ�� �⺻Ű
CREATE UNIQUE INDEX PK_BITUSER
	ON BITUSER ( -- ȸ��
		ID ASC -- ID
	);

-- ȸ��
ALTER TABLE BITUSER
	ADD
		CONSTRAINT PK_BITUSER -- ȸ�� �⺻Ű
		PRIMARY KEY (
			ID -- ID
		);

-- ������
CREATE TABLE ADMIN (
	ID  VARCHAR2(20) NOT NULL, -- ID
	PWD VARCHAR2(20) NOT NULL  -- ��й�ȣ
);

-- ������ �⺻Ű
CREATE UNIQUE INDEX PK_ADMIN
	ON ADMIN ( -- ������
		ID ASC -- ID
	);

-- ������
ALTER TABLE ADMIN
	ADD
		CONSTRAINT PK_ADMIN -- ������ �⺻Ű
		PRIMARY KEY (
			ID -- ID
		);

-- ��������
CREATE TABLE NOTICE (
	NCINDEX   NUMBER        NOT NULL, -- �۹�ȣ
	TITLE     VARCHAR2(100) NOT NULL, -- ����
	NCCONTENT VARCHAR2(500) NOT NULL, -- ����
	RTIME     VARCHAR2(40)  NOT NULL, -- ��Ͻð�
	NCSTATE   VARCHAR2(4)   NOT NULL, -- �������� ����
	ADMINID   VARCHAR2(20)  NOT NULL  -- ID
);

-- �������� �⺻Ű
CREATE UNIQUE INDEX PK_NOTICE
	ON NOTICE ( -- ��������
		NCINDEX ASC -- �۹�ȣ
	);

-- ��������
ALTER TABLE NOTICE
	ADD
		CONSTRAINT PK_NOTICE -- �������� �⺻Ű
		PRIMARY KEY (
			NCINDEX -- �۹�ȣ
		);

-- ���
CREATE TABLE REPLY (
	RPINDEX  NUMBER        NOT NULL, -- ��۹�ȣ
	CONTENT  VARCHAR2(100) NOT NULL, -- ����
	SCSTATE  VARCHAR2(4)   NOT NULL, -- �������
	DELSTATE VARCHAR2(4)   NOT NULL, -- ��������
	TRSTATE  VARCHAR2(4)   NOT NULL, -- ������ �ŷ�����
	RTIME    VARCHAR2(40)  NOT NULL, -- ��Ͻð�
	REFER    NUMBER        NOT NULL, -- refer
	DEPTH    NUMBER        NOT NULL, -- depth
	STEP     NUMBER        NOT NULL, -- step
	ID       VARCHAR2(20)  NOT NULL, -- ID
	BDINDEX  NUMBER        NOT NULL  -- �۹�ȣ
);

-- ��� �⺻Ű
CREATE UNIQUE INDEX PK_REPLY
	ON REPLY ( -- ���
		RPINDEX ASC -- ��۹�ȣ
	);

-- ���
ALTER TABLE REPLY
	ADD
		CONSTRAINT PK_REPLY -- ��� �⺻Ű
		PRIMARY KEY (
			RPINDEX -- ��۹�ȣ
		);

-- �Խñ�
CREATE TABLE BOARD (
	BDINDEX  NUMBER        NOT NULL, -- �۹�ȣ
	TITLE    VARCHAR2(100) NOT NULL, -- ����
	PRICE    NUMBER        NOT NULL, -- ����
	CONTENT  VARCHAR2(500) NOT NULL, -- ����
	RTIME    DATE          NOT NULL, -- ��Ͻð�
	TRSTATE  VARCHAR2(4)   NOT NULL, -- �Ǹ��� �ŷ�����
	DELSTATE VARCHAR2(4)   NOT NULL, -- ��������
	COUNT    NUMBER        NOT NULL, -- ��ȸ��
	IMG      VARCHAR2(500) NULL,     -- �̹���
	ID       VARCHAR2(20)  NOT NULL, -- ID
	CTCODE   VARCHAR2(20)  NOT NULL  -- ī�װ��ڵ�
);

-- �Խñ� �⺻Ű
CREATE UNIQUE INDEX PK_BOARD
	ON BOARD ( -- �Խñ�
		BDINDEX ASC -- �۹�ȣ
	);

-- �Խñ�
ALTER TABLE BOARD
	ADD
		CONSTRAINT PK_BOARD -- �Խñ� �⺻Ű
		PRIMARY KEY (
			BDINDEX -- �۹�ȣ
		);

-- �̹���
CREATE TABLE IMAGE (
	IMGINDEX NUMBER        NOT NULL, -- �̹�����ȣ
	IMGNAME  VARCHAR2(100) NOT NULL, -- �̹����̸�
	BDINDEX  NUMBER        NOT NULL  -- �۹�ȣ
);

-- �̹��� �⺻Ű
CREATE UNIQUE INDEX PK_IMAGE
	ON IMAGE ( -- �̹���
		IMGINDEX ASC -- �̹�����ȣ
	);

-- �̹���
ALTER TABLE IMAGE
	ADD
		CONSTRAINT PK_IMAGE -- �̹��� �⺻Ű
		PRIMARY KEY (
			IMGINDEX -- �̹�����ȣ
		);

-- ���ɸ��
CREATE TABLE FAVORITE (
	FAVINDEX NUMBER       NOT NULL, -- ���ɸ�� ��ȣ
	ID       VARCHAR2(20) NOT NULL, -- ID
	BDINDEX  NUMBER       NOT NULL  -- �۹�ȣ
);

-- ���ɸ�� �⺻Ű
CREATE UNIQUE INDEX PK_FAVORITE
	ON FAVORITE ( -- ���ɸ��
		FAVINDEX ASC -- ���ɸ�� ��ȣ
	);

-- ���ɸ��
ALTER TABLE FAVORITE
	ADD
		CONSTRAINT PK_FAVORITE -- ���ɸ�� �⺻Ű
		PRIMARY KEY (
			FAVINDEX -- ���ɸ�� ��ȣ
		);

-- ī�װ�
CREATE TABLE CATEGORY (
	CTCODE VARCHAR2(20) NOT NULL, -- ī�װ��ڵ�
	CTNAME VARCHAR2(20) NOT NULL  -- ī�װ��ڵ��
);

-- ī�װ� �⺻Ű
CREATE UNIQUE INDEX PK_CATEGORY
	ON CATEGORY ( -- ī�װ�
		CTCODE ASC -- ī�װ��ڵ�
	);

-- ī�װ�
ALTER TABLE CATEGORY
	ADD
		CONSTRAINT PK_CATEGORY -- ī�װ� �⺻Ű
		PRIMARY KEY (
			CTCODE -- ī�װ��ڵ�
		);

-- ��������
CREATE TABLE USERQNA (
	QAINDEX  NUMBER        NOT NULL, -- �۹�ȣ
	TITLE    VARCHAR2(100) NOT NULL, -- ����
	QATIME   VARCHAR2(40)  NOT NULL, -- ��Ͻð�
	COUNT    NUMBER        NOT NULL, -- ��ȸ��
	SCSTATE  VARCHAR2(4)   NOT NULL, -- �������
	CONTENT  VARCHAR2(500) NOT NULL, -- ����
	FILENAME VARCHAR2(100) NULL,     -- ÷������
	AWSTATE  VARCHAR2(4)   NOT NULL, -- �亯�ϷῩ��
	ID       VARCHAR2(20)  NOT NULL  -- ID
);

-- �������� �⺻Ű
CREATE UNIQUE INDEX PK_USERQNA
	ON USERQNA ( -- ��������
		QAINDEX ASC -- �۹�ȣ
	);

-- ��������
ALTER TABLE USERQNA
	ADD
		CONSTRAINT PK_USERQNA -- �������� �⺻Ű
		PRIMARY KEY (
			QAINDEX -- �۹�ȣ
		);

-- ����������
CREATE TABLE QNAREPLY (
	TITLE   VARCHAR2(100) NOT NULL, -- ����
	ID      VARCHAR2(20)  NOT NULL, -- ID
	CONTENT VARCHAR2(500) NOT NULL, -- ����
	QARTIME VARCHAR2(40)  NOT NULL, -- ��Ͻð�
	QAINDEX NUMBER        NOT NULL  -- �۹�ȣ
);

-- ���������� �⺻Ű
CREATE UNIQUE INDEX PK_QNAREPLY
	ON QNAREPLY ( -- ����������
		TITLE ASC -- ����
	);

-- ����������
ALTER TABLE QNAREPLY
	ADD
		CONSTRAINT PK_QNAREPLY -- ���������� �⺻Ű
		PRIMARY KEY (
			TITLE -- ����
		);

-- ��������
ALTER TABLE NOTICE
	ADD
		CONSTRAINT FK_ADMIN_TO_NOTICE -- ������ -> ��������
		FOREIGN KEY (
			ADMINID -- ID
		)
		REFERENCES ADMIN ( -- ������
			ID -- ID
		);

-- ���
ALTER TABLE REPLY
	ADD
		CONSTRAINT FK_BITUSER_TO_REPLY -- ȸ�� -> ���
		FOREIGN KEY (
			ID -- ID
		)
		REFERENCES BITUSER ( -- ȸ��
			ID -- ID
		);

-- ���
ALTER TABLE REPLY
	ADD
		CONSTRAINT FK_BOARD_TO_REPLY -- �Խñ� -> ���
		FOREIGN KEY (
			BDINDEX -- �۹�ȣ
		)
		REFERENCES BOARD ( -- �Խñ�
			BDINDEX -- �۹�ȣ
		);

-- �Խñ�
ALTER TABLE BOARD
	ADD
		CONSTRAINT FK_BITUSER_TO_BOARD -- ȸ�� -> �Խñ�
		FOREIGN KEY (
			ID -- ID
		)
		REFERENCES BITUSER ( -- ȸ��
			ID -- ID
		);

-- �Խñ�
ALTER TABLE BOARD
	ADD
		CONSTRAINT FK_CATEGORY_TO_BOARD -- ī�װ� -> �Խñ�
		FOREIGN KEY (
			CTCODE -- ī�װ��ڵ�
		)
		REFERENCES CATEGORY ( -- ī�װ�
			CTCODE -- ī�װ��ڵ�
		);

-- �̹���
ALTER TABLE IMAGE
	ADD
		CONSTRAINT FK_BOARD_TO_IMAGE -- �Խñ� -> �̹���
		FOREIGN KEY (
			BDINDEX -- �۹�ȣ
		)
		REFERENCES BOARD ( -- �Խñ�
			BDINDEX -- �۹�ȣ
		);

-- ���ɸ��
ALTER TABLE FAVORITE
	ADD
		CONSTRAINT FK_BITUSER_TO_FAVORITE -- ȸ�� -> ���ɸ��
		FOREIGN KEY (
			ID -- ID
		)
		REFERENCES BITUSER ( -- ȸ��
			ID -- ID
		);

-- ���ɸ��
ALTER TABLE FAVORITE
	ADD
		CONSTRAINT FK_BOARD_TO_FAVORITE -- �Խñ� -> ���ɸ��
		FOREIGN KEY (
			BDINDEX -- �۹�ȣ
		)
		REFERENCES BOARD ( -- �Խñ�
			BDINDEX -- �۹�ȣ
		);

-- ��������
ALTER TABLE USERQNA
	ADD
		CONSTRAINT FK_BITUSER_TO_USERQNA -- ȸ�� -> ��������
		FOREIGN KEY (
			ID -- ID
		)
		REFERENCES BITUSER ( -- ȸ��
			ID -- ID
		);

-- ����������
ALTER TABLE QNAREPLY
	ADD
		CONSTRAINT FK_USERQNA_TO_QNAREPLY -- �������� -> ����������
		FOREIGN KEY (
			QAINDEX -- �۹�ȣ
		)
		REFERENCES USERQNA ( -- ��������
			QAINDEX -- �۹�ȣ
		);

-- ����������
ALTER TABLE QNAREPLY
	ADD
		CONSTRAINT FK_ADMIN_TO_QNAREPLY -- ������ -> ����������
		FOREIGN KEY (
			ID -- ID
		)
		REFERENCES ADMIN ( -- ������
			ID -- ID
		);



--���� �Խ���  �ε��� ���� ó��
CREATE SEQUENCE board_bdindex
START WITH 1
INCREMENT BY 1
NOCACHE;      
       
--�������� �Խ��� �ε��� ���� ó��
CREATE SEQUENCE notice_ncindex
START WITH 1
INCREMENT BY 1
NOCACHE;

--��� �Խ��� �ε��� ���� ó��
CREATE SEQUENCE reply_rpindex
START WITH 1
INCREMENT BY 1
NOCACHE;

commit;

--QnA �Խ��� �ε��� ���� ó��
CREATE SEQUENCE userqna_qaindex
START WITH 1
INCREMENT BY 1
NOCACHE;

--���ɸ�� �Խ��� �ε��� ���� ó��
CREATE SEQUENCE favorite_favindex
START WITH 1
INCREMENT BY 1
NOCACHE;

-- ������ ���� ��ȸ
SELECT * FROM USER_SEQUENCES;

insert into admin values('admin',1004);

select * from bituser;
desc bituser;

select * from board;

select b.*,round((google_distance(lat,lon, 37.6438784, 127.06775039999998)),10) t from 
board b join bituser u
on b.id=u.id
order by t;

select round((google_distance(37.6438784, 127.06775039999998, lat, lon)),10) t from bituser1 u;
desc bituser1;
select * from bituser1;



select google_distance(37.6438784, 127.06775039999998,37.6438784,127.0677503999998) from dual;

-- �� ��ǥ��(��/����) ������ �Ÿ� ���ϴ� �Լ�
CREATE OR REPLACE FUNCTION GOOGLE_DISTANCE (
  LAT1 FLOAT, 
  LNG1 FLOAT,
  LAT2 FLOAT,
  LNG2 FLOAT 
)

  RETURN FLOAT
 
IS

  fEARTH_R FLOAT := 6371000.0;
  fPIE FLOAT := 3.141592653589793;
  fRAD FLOAT := 0.0;
  radLAT1 FLOAT := 0.0;
  radLAT2 FLOAT := 0.0;
  radDIST FLOAT := 0.0;
  fDIST FLOAT := 0.0;

BEGIN

  fRAD := fPIE / 180;
  radLAT1 := fRAD * LAT1;
  radLAT2 := fRAD * LAT2;
  radDIST := fRAD * ( LNG1 - LNG2 );
  fDIST := SIN( radLAT1 ) * SIN( radLAT2 );
  fDIST := fDIST + COS( radLAT1 ) * COS( radLAT2 ) * COS( radDIST );
  RETURN ((fEARTH_R * ACOS(ROUND(fDIST,10)))/1000);

END;
/

---�Լ� �� '/'�̰͵� �����̴�.

 SELECT * from 
					(select rownum rn, bdindex, title,price,content,rtime,trstate,delstate,count,id,img,dist
				 , ctcode FROM (SELECT b.*, (power(lat-37,2)+power(lon-127,2)) dist FROM board b JOIN bituser u ON b.id = u.id order by dist)
					 where rownum <=10) where rn >= 1;

 

select * from reply;



select * from admin;

desc notice;

commit;

INSERT into CATEGORY(ctcode, ctname) values (1,'������/����');
INSERT into CATEGORY(ctcode, ctname) values (2,'������/����');
INSERT into CATEGORY(ctcode, ctname) values (3,'�Ƿ�/��ȭ');
INSERT into CATEGORY(ctcode, ctname) values (4,'��Ƽ/�̿�');
INSERT into CATEGORY(ctcode, ctname) values (5,'��Ȱ/����');

commit;




