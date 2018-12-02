CREATE TABLE IF NOT EXISTS Organization (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    full_name   VARCHAR(80) NOT NULL,
    inn         VARCHAR(12) NOT NULL UNIQUE,
    kpp         CHAR(9) NOT NULL,
    phone       VARCHAR(25),
    address     VARCHAR(50) NOT NULL,
    is_active   BOOLEAN DEFAULT FALSE
);

COMMENT ON TABLE Organization IS 'Организация';
COMMENT ON COLUMN Organization.id IS 'Уникальный идентификатор организации, с автоприращением';
COMMENT ON COLUMN Organization.name IS 'Название организации, обязательно к заполнению';
COMMENT ON COLUMN Organization.full_name IS 'Полное название организации, обязательно к заполнению';
COMMENT ON COLUMN Organization.inn IS 'ИНН организации, обязателен к заполнению, значение уникально';
COMMENT ON COLUMN Organization.kpp IS 'КПП организации, обязателен к заполнению';
COMMENT ON COLUMN Organization.phone IS 'Телефон организации';
COMMENT ON COLUMN Organization.address IS 'Адрес организации, обязателен к заполнению';
COMMENT ON COLUMN Organization.is_active IS 'Статус организации, по умолчанию неактивен';

CREATE TABLE IF NOT EXISTS Office (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    phone       VARCHAR(25),
    address     VARCHAR(50) NOT NULL,
    is_active   BOOLEAN DEFAULT FALSE,
    org_id      INTEGER REFERENCES Organization (id) ON DELETE SET NULL ON UPDATE CASCADE
);

COMMENT ON TABLE Office IS 'Офис';
COMMENT ON COLUMN Office.id IS 'Уникальный идентификатор офиса, с автоприращением';
COMMENT ON COLUMN Office.name IS 'Название офиса, обязательно к заполнению';
COMMENT ON COLUMN Office.phone IS 'Телефон офиса';
COMMENT ON COLUMN Office.address IS 'Адрес офиса, обязателен к заполнению';
COMMENT ON COLUMN Office.is_active IS 'Статус офиса, по умолчанию неактивен';
COMMENT ON COLUMN Office.org_id IS
'Идентификатор организации из таблицы "Organization", при удалении организации устанавливается значение NULL';

CREATE TABLE IF NOT EXISTS Country (
    id      SMALLINT  PRIMARY KEY AUTO_INCREMENT,
    code    CHAR(3)  NOT NULL UNIQUE,
    name    VARCHAR(50)
);

COMMENT ON TABLE Country IS 'Справочник Общероссийского классификатора стран мира';
COMMENT ON COLUMN Country.id IS 'Уникальный идентификатор страны, с автоприращением';
COMMENT ON COLUMN Country.code IS 'Цифровой код страны, обязателен к заполнению, уникален';
COMMENT ON COLUMN Country.name IS 'Название страны';

CREATE TABLE IF NOT EXISTS Document (
    id       TINYINT  PRIMARY KEY AUTO_INCREMENT,
    code     CHAR(2)  NOT NULL UNIQUE,
    name     VARCHAR(50)
);

COMMENT ON TABLE Document IS 'Справочник видов документов, удостоверяющих личность физического лица';
COMMENT ON COLUMN Document.id IS 'Уникальный идентификатор документа, с автоприращением';
COMMENT ON COLUMN Document.code IS 'Цифровой код документа, обязателен к заполнению, уникален';
COMMENT ON COLUMN Document.name IS 'Название документа';

CREATE TABLE IF NOT EXISTS Position (
    id       SMALLINT  PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL
);

COMMENT ON TABLE Position IS 'Должность работника';
COMMENT ON COLUMN Position.id IS 'Уникальный идентификатор должности, с автоприращением';
COMMENT ON COLUMN Position.name IS 'Название должности';

CREATE TABLE IF NOT EXISTS Employee (
    id              INTEGER  PRIMARY KEY AUTO_INCREMENT,
    first_name      VARCHAR(50) NOT NULL,
    second_name     VARCHAR(50),
    middle_name     VARCHAR(50),
    last_name       VARCHAR(50),
    phone           VARCHAR(50),
    is_identified   BOOLEAN DEFAULT FALSE,
    pos_id          SMALLINT REFERENCES Position (id) ON DELETE NO ACTION ON UPDATE CASCADE,
    country_id      SMALLINT REFERENCES Country (id) ON DELETE NO ACTION ON UPDATE CASCADE,
    office_id       INTEGER REFERENCES Office (id) ON DELETE SET NULL ON UPDATE CASCADE
);

COMMENT ON TABLE Employee IS 'Работник (в запросах user)';
COMMENT ON COLUMN Employee.id IS 'Уникальный идентификатор работника, с автоприращением';
COMMENT ON COLUMN Employee.first_name IS 'Имя работника, обязательно к заполнению';
COMMENT ON COLUMN Employee.second_name IS 'Второе имя работника';
COMMENT ON COLUMN Employee.middle_name IS 'Среднее имя работника';
COMMENT ON COLUMN Employee.last_name IS 'Фамилия работника';
COMMENT ON COLUMN Employee.phone IS 'Телефон работника';
COMMENT ON COLUMN Employee.is_identified IS 'Статус работника, по умолчанию не идентифицирован';
COMMENT ON COLUMN Employee.pos_id IS 'Идентификатор должности из таблицы "Position" с запретом удаления должности';
COMMENT ON COLUMN Employee.country_id IS 'Идентификатор гражданства из таблицы "Country" с запретом удаления страны';
COMMENT ON COLUMN Employee.office_id IS
'Идентификатор офиса из таблицы "Office", при удалении офиса устанавливается значение NULL';

CREATE TABLE IF NOT EXISTS Document_Data (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    doc_id      TINYINT REFERENCES Document (id) ON DELETE NO ACTION ON UPDATE CASCADE,
    number      VARCHAR(30) NOT NULL UNIQUE,
    date        DATE,
    empl_id     INTEGER REFERENCES Employee (id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE Document_Data IS 'Персональные данные работника';
COMMENT ON COLUMN Document_Data.id IS 'Уникальный идентификатор персональных данных, с автоприращением';
COMMENT ON COLUMN Document_Data.doc_id IS
'Идентификатор документа из таблицы "Document" с запретом удаления вида документа';
COMMENT ON COLUMN Document_Data.number IS 'Номер документа, обязателен к заполнению, уникален';
COMMENT ON COLUMN Document_Data.date IS 'Дата документа';
COMMENT ON COLUMN Document_Data.empl_id IS
'Идентификатор работника из таблицы "Employee", при удалении работника удаляются строки с персональными данными';

CREATE INDEX IX_Office_Name ON Office (name);
CREATE INDEX IX_Office_Phone ON Office (phone);
CREATE INDEX IX_Office_Is_Active ON Office (is_active);

CREATE INDEX IX_Employee_First_Name ON Employee (first_name);
CREATE INDEX IX_Employee_Last_Name ON Employee (last_name);
CREATE INDEX IX_Employee_Middle_Name ON Employee (middle_name);

CREATE INDEX IX_Position_name ON Position (name);

CREATE INDEX IX_Organization_Name ON Organization (name);
CREATE INDEX IX_Organization_Is_Active ON Organization (is_active);