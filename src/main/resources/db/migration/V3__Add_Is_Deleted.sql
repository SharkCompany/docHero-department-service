ALTER TABLE department ADD COLUMN is_deleted BOOLEAN DEFAULT false;

ALTER TABLE folder ADD COLUMN is_deleted BOOLEAN DEFAULT false;
