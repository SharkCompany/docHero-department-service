CREATE TABLE IF NOT EXISTS document_type (
    document_type_id          VARCHAR(36) PRIMARY KEY,
    type_name                 VARCHAR(255),
    extension_name            VARCHAR(255),
    description               VARCHAR(255),
    created_by                VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS document (
    document_id                 VARCHAR(36) PRIMARY KEY,
    document_title              VARCHAR(255),
    document_type_id            VARCHAR(255),
    document_folder_id          VARCHAR(255),
    is_deleted                  BOOLEAN DEFAULT false,
    created_by                  VARCHAR(255),
    updated_by                  VARCHAR(255),
    created_at                  TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at                  TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
    deleted_at                  TIMESTAMP WITH TIME ZONE
);

INSERT INTO document_type (document_type_id, type_name, extension_name, description) VALUES ('1', 'excel', 'xls', 'excel document type');
INSERT INTO document_type (document_type_id, type_name, extension_name, description) VALUES ('2', 'docx', 'docx', 'docx document type');

INSERT INTO document (document_id, document_title, document_type_id, document_folder_id) VALUES ('1', 'Ke hoach tuan 20', '2', '1');
INSERT INTO document (document_id, document_title, document_type_id, document_folder_id) VALUES ('2', 'Ke hoach tuan 21', '2', '1');
INSERT INTO document (document_id, document_title, document_type_id, document_folder_id) VALUES ('3', 'Ke hoach tuan 22', '2', '1');