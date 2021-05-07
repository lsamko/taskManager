CREATE TABLE task
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    priority INTEGER      NOT NULL,
    name     VARCHAR(255) NOT NULL,
    uuid     VARCHAR(255) NOT NULL,
    date     DATETIME,
    CONSTRAINT UNIQUE_NAME
        UNIQUE (name),
    CONSTRAINT PK_ID
        PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    user_uuid  VARCHAR(255) NOT NULL,

    CONSTRAINT PK_USER_ID
        PRIMARY KEY (id)
);
CREATE TABLE user_tasks
(
    user_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,

    CONSTRAINT PK_USER_TASKS_ID
        PRIMARY KEY (user_id, task_id),
    CONSTRAINT FK_USER_ID
        FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT FK_TASK_ID
        FOREIGN KEY (task_id) REFERENCES task (id)
);