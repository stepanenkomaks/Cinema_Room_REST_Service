package cinema;

import cinema.util.AlreadyPurchasedException;
import cinema.util.OutOfRangeException;
import cinema.util.WrongPasswordException;
import cinema.util.WrongTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class TaskController {

    private final Room room1 = new Room(9, 9);

    @GetMapping("/seats")
    public Room getCinema() {
        return room1;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> postPurchase(@RequestBody Seat seat) {
        if (seat.getColumn() > room1.getTotal_columns()
                || seat.getRow() > room1.getTotal_rows()
                || seat.getRow() < 1
                || seat.getColumn() < 1) {
            throw new OutOfRangeException();
        }
        for (int i = 0; i < room1.getAvailable_seats().size(); i++) {
            Seat s = room1.getAvailable_seats().get(i);
            if (s.equals(seat)) {
                UUID token = UUID.randomUUID();
                PurchasedTicket purchasedTicket = new PurchasedTicket(token, s);
                room1.getAvailable_seats().remove(i);
                room1.getPurchasedTickets().add(purchasedTicket);
                return new ResponseEntity<>(purchasedTicket, HttpStatus.OK);
            }
        }
        throw new AlreadyPurchasedException();
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        ArrayList<PurchasedTicket> purchasedTickets = room1.getPurchasedTickets();
        for (PurchasedTicket ticket : purchasedTickets) {
            if(ticket.getToken().equals(token.getToken())) {
                room1.getPurchasedTickets().remove(ticket);
                room1.getAvailable_seats().add(ticket.seat);
                return new ResponseEntity<>(Map.of("returned_ticket",
                        ticket.seat), HttpStatus.OK);
            }
        }
        throw new WrongTokenException();
    }

    @PostMapping("/stats")
    public ResponseEntity<?> returnStats(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")) {
            Map<String, Integer> returnMap = new HashMap<>();
            int currentIncome = 0;
            if (!room1.getPurchasedTickets().isEmpty()) {
                for (PurchasedTicket ticket : room1.getPurchasedTickets()) {
                    currentIncome += ticket.seat.getPrice();
                }
            }
            returnMap.put("number_of_purchased_tickets", room1.getPurchasedTickets().size());
            returnMap.put("number_of_available_seats", room1.getAvailable_seats().size());
            returnMap.put("current_income", currentIncome);
            return new ResponseEntity<>(returnMap, HttpStatus.OK);
        }
        throw new WrongPasswordException();
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> rangeError(OutOfRangeException outOfRangeException) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "The number of a row or a column is out of bounds!");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> purchasedError(AlreadyPurchasedException alreadyPurchasedException) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "The ticket has been already purchased!");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> tokenError(WrongTokenException wrongTokenException) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Wrong token!");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> passwordError(WrongPasswordException wrongPasswordException) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "The password is wrong!");
        return new ResponseEntity<>(map, HttpStatus.valueOf(401));
    }
}
