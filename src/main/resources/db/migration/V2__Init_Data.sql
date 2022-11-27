INSERT INTO "department" (department_id, department_name, description) VALUES ('1', 'Finance Department', 'Finance department description');
INSERT INTO "department" (department_id, department_name, description) VALUES ('2', 'Human Resources Department', 'Human Resources Department descriptions');

INSERT INTO "folder" (folder_id, folder_name, department_reference_id) VALUES ('1', 'Finance folder 1', '1');
INSERT INTO "folder" (folder_id, folder_name, department_reference_id) VALUES ('2', 'Finance folder 2', '1');

INSERT INTO "folder" (folder_id, folder_name, department_reference_id) VALUES ('3', 'human resource folder 1', '2');
INSERT INTO "folder" (folder_id, folder_name, department_reference_id) VALUES ('4', 'human resource folder 2', '2');

INSERT INTO "folder" (folder_id, parent_folder_id, folder_name, department_reference_id) VALUES (gen_random_uuid(), '1', 'finance sub folder 1 1', '1');
INSERT INTO "folder" (folder_id, parent_folder_id, folder_name, department_reference_id) VALUES (gen_random_uuid(), '1', 'finance sub folder 1 2', '1');

INSERT INTO "folder" (folder_id, parent_folder_id, folder_name, department_reference_id) VALUES (gen_random_uuid(), '2', 'finance sub folder 2 1', '1');

