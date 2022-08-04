package cinema;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Room {

    private int total_rows;
    private int total_columns;
    private ArrayList<Seat> available_seats = new ArrayList<>();
    @JsonIgnore
    private ArrayList<PurchasedTicket> purchasedTickets = new ArrayList<>();

    public Room() {}

    public Room(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = fillAvailableSeats(total_rows, total_columns);
    }

    ArrayList<Seat> fillAvailableSeats(int total_rows, int total_columns) {
        ArrayList<Seat> av_seats = new ArrayList<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                av_seats.add(new Seat(i, j));
            }
        }
        return av_seats;
    }

    void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    void setPurchasedTickets(ArrayList<PurchasedTicket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    @JsonGetter(value = "total_rows")
    int getTotal_rows() {
        return total_rows;
    }

    @JsonGetter(value = "total_columns")
    int getTotal_columns() {
        return total_columns;
    }

    @JsonGetter(value = "available_seats")
    ArrayList<Seat> getAvailable_seats() {
        return available_seats;
    }

    ArrayList<PurchasedTicket> getPurchasedTickets() {
        return purchasedTickets;
    }
}
