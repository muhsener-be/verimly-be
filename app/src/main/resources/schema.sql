ALTER TABLE folders
    ADD CONSTRAINT fk_folders_owner_id
        FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_owner_id
        FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_folder_id
        FOREIGN KEY (folder_id) REFERENCES folders (id);

ALTER TABLE sessions
    ADD CONSTRAINT fk_sessions_owner_id
        FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE sessions
    ADD CONSTRAINT fk_sessions_task_id
        FOREIGN KEY (task_id) REFERENCES tasks (id);