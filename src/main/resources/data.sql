INSERT INTO Organization (version, name, full_name, inn, kpp, phone, address) VALUES (0, 'Орг', 'Организация', '0123456789',
'123456789', '+7(845)222-22-22', 'г. Саратов');

INSERT INTO Organization (version, name, full_name, inn, kpp, phone, address) VALUES (0, 'OCS', 'OCS Distribution', '1234567890',
'123456789', '+7 (495) 995-2575', '108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1');

INSERT INTO Office (version, name, phone, address, is_active, org_id) VALUES (0, 'Офис Организации', '+7(845)222-22-33',
'г. Саратов, пр. Кирова', true, 1);

INSERT INTO Office (version, name, phone, address, is_active, org_id) VALUES (0, 'OCS Саратов', '8-800-555-3-999',
'410004, Саратов, Ул. Чернышевского, 60/62, офис 903', true, 2);

INSERT INTO Document (code, name) VALUES ('21', 'Паспорт гражданина Российской Федерации');

INSERT INTO Country (code, name) VALUES ('643', 'Российская Федерация');

INSERT INTO Position (name) VALUES ('менеджер');

INSERT INTO Employee (version, first_name, last_name, pos_id, phone, country_id, office_id)
VALUES (0, 'Иван', 'Иванов', 1, '+7(927)111-11-11', 1, 1);

INSERT INTO Employee (version, first_name, last_name, pos_id, phone, country_id, office_id)
VALUES (0, 'Петр', 'Петров', 1, '+7(917)000-00-00', 1, 2);

INSERT INTO Document_Data (doc_id, number, date, empl_id) VALUES (1, '6305 454552', '2007-05-25', 1);

INSERT INTO Document_Data (doc_id, number, date, empl_id) VALUES (1, '6305 454356', '2017-02-03', 2);