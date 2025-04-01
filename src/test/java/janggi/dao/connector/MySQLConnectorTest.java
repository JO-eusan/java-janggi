package janggi.dao.connector;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MySQLConnectorTest {

    @Test
    @DisplayName("데이터베이스 연결 테스트")
    void connection() {
        assertThat(MySQLConnector.createConnection()).isInstanceOf(MySQLConnector.class);
    }
}
