INSERT INTO Organization (name, full_name, inn, kpp, phone, address) VALUES ('Орг', 'Организация', '0123456789',
'123456789', '+7(845)222-22-22', 'г. Саратов');

INSERT INTO Office (name, phone, address, is_active, org_id) VALUES ('Офис Организации', '+7(845)222-22-33',
'г. Саратов, пр. Кирова', true, 1);

INSERT INTO Document (code, name) VALUES ('21', 'Паспорт гражданина Российской Федерации');

INSERT INTO Country (code, name) VALUES ('643', 'Российская Федерация');

INSERT INTO Position (name) VALUES ('менеджер');

INSERT INTO Employee (first_name, last_name, pos_id, phone, country_id, office_id)
VALUES ('Иван', 'Иванов', 1, '+7(927)111-11-11', 1, 1);

INSERT INTO Employee (first_name, last_name, pos_id, phone, country_id, office_id)
VALUES ('Петр', 'Петров', 1, '+7(917)000-00-00', 1, 1);

INSERT INTO Document_Data (doc_id, number, date, empl_id) VALUES (1, '6305 454552', '2007-05-25', 1);

INSERT INTO Document_Data (doc_id, number, date, empl_id) VALUES (1, '6305 454356', '2017-02-03', 2);
