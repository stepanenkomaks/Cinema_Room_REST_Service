package cinema;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Seat {

    private int row;
    private int column;
    private int price;

    public Seat() {}

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = setPrice(row);
    }

    void setRow(int row) {
        this.row = row;
        this.price = setPrice(row);
    }

    void setColumn(int column) {
        this.column = column;
    }

    int setPrice(int row) {
        if (row <= 4) {
            return 10;
        } else {
            return 8;
        }
    }

    @JsonGetter(value = "row")
    int getRow() {
        return row;
    }

    @JsonGetter(value = "column")
    int getColumn() {
        return column;
    }

    @JsonGetter(value = "price")
    int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (row != seat.row) return false;
        return column == seat.column;
    }
}
