INSERT INTO "department" (department_id, department_name, description) VALUES ('1', 'Finance Department', 'Finance department description');
INSERT INTO "department" (department_id, department_name, description) VALUES ('2', 'Human Resources Department', 'Human Resources Department descriptions');

INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES ('1', 'Root folder finance department', NULL, '1', false, true, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);
INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES ('2', 'Root folder human resources department', NULL, '2', false, true, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);

--init data subfolder of root folder
INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES (gen_random_uuid(), 'Finance Sub folder 1', '1', '1', false, false, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);
INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES (gen_random_uuid(), 'Finance Sub folder 2', '1', '1', false, false, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);

INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES (gen_random_uuid(), 'Human resources Sub folder 1', '2', '2', false, false, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);
INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES (gen_random_uuid(), 'Human resources Sub folder 2', '2', '2', false, false, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL);
INSERT INTO "folder" (folder_id, folder_name, parent_folder_id, department_reference_id, is_deleted, is_root, created_by, updated_by, created_at, updated_at, deleted_at)
    VALUES (gen_random_uuid(), 'Human resources Sub folder 3', '2', '2', false, false, 'admin', 'admin', '2021-05-20 00:00:00', '2021-05-20 00:00:00', NULL)


