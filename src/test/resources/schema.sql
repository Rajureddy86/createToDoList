CREATE TABLE IF NOT EXISTS ToDos (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     description TEXT NOT NULL,
                                     completionstatus BOOLEAN NOT NULL DEFAULT FALSE
);