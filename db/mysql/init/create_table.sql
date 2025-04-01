CREATE TABLE IF NOT EXISTS board (
  board_name VARCHAR(50) NOT NULL,
  turn VARCHAR(20) NOT NULL,
  start_time TIME NOT NULL,
  PRIMARY KEY (board_name)
);

CREATE TABLE IF NOT EXISTS pieces (
  piece_id INT NOT NULL AUTO_INCREMENT,
  board_name VARCHAR(50) NOT NULL,
  type VARCHAR(20) NOT NULL,
  team VARCHAR(20) NOT NULL,
  point_row INT NOT NULL,
  point_column INT NOT NULL,
  PRIMARY KEY (piece_id),
  FOREIGN KEY (board_name) REFERENCES board(board_name)
);
