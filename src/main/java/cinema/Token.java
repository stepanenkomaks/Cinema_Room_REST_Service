package cinema;

import java.util.UUID;

public class Token {
    UUID token;

    Token() {}

    Token(UUID token) {
        this.token = token;
    }

    void setToken(UUID token) {
        this.token = token;
    }

    UUID getToken() {
        return token;
    }
}
