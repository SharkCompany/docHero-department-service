-- Add departmentId column to document table
ALTER TABLE document ADD COLUMN document_department_id VARCHAR(36);

--Add foreign key to document_department_id column
ALTER TABLE document ADD CONSTRAINT fk_document_department_id FOREIGN KEY (document_department_id) REFERENCES department(department_id);