ALTER TABLE folders
    ADD CONSTRAINT fk_folders_owner_id
        FOREIGN KEY (owner_id) REFERENCES users (id);