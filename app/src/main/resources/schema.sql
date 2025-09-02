CREATE  TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT u_users_email UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS folders
(
    id          UUID PRIMARY KEY,
    owner_id    UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(240),
    label_color VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_folders_owner_id FOREIGN KEY (owner_id) REFERENCES users (id)
);
CREATE TABLE  IF NOT EXISTS tasks
(
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    folder_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(240),
    due_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (folder_id) REFERENCES folders (id)
);
CREATE TABLE IF NOT EXISTS sessions
(
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    task_id UUID NOT NULL,
    started_at TIMESTAMP NOT NULL,
    paused_at TIMESTAMP,
    finished_at TIMESTAMP,
    total_pause NUMERIC(21,0),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);
