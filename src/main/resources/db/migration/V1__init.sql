CREATE TABLE IF NOT EXISTS department (
    department_id       VARCHAR(36) PRIMARY KEY,
    department_name                VARCHAR(255),
    description         VARCHAR(255),
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at          TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    deleted_at          TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS folder (
    folder_id                   VARCHAR(36) PRIMARY KEY,
    folder_name                 VARCHAR(255),
    parent_folder_id            VARCHAR(255),
    department_reference_id     VARCHAR(255),
    created_at                  TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at                  TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    deleted_at                  TIMESTAMP WITH TIME ZONE
);

ALTER TABLE folder ADD CONSTRAINT folder_department_id_fkey FOREIGN KEY (department_reference_id) REFERENCES department (department_id);
ALTER TABLE folder ADD CONSTRAINT folder_parent_fkey FOREIGN KEY (parent_folder_id) REFERENCES folder (folder_id);
