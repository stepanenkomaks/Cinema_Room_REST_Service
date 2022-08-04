package cinema;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class PurchasedTicket {

    UUID token;
    Seat seat;

    PurchasedTicket() {}

    PurchasedTicket(UUID token, Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    void setTicket(UUID token) {
        this.token = token;
    }

    void setSeat(Seat seat) {
        this.seat = seat;
    }

    @JsonGetter(value = "token")
    UUID getToken() {
        return token;
    }

    @JsonGetter(value = "ticket")
    Seat getSeat() {
        return seat;
    }
}
