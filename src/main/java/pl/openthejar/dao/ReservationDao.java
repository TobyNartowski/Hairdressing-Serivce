package pl.openthejar.dao;

import pl.openthejar.model.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationDao extends EntityDao<Reservation> {

    public ReservationDao() {
        super(Reservation.class);
    }

    public Reservation changeStatus(Reservation reservation, boolean status) {
        reservation.setDone(status);
        saveOrUpdate(reservation);
        return reservation;
    }

    public List<Reservation> getDoneReservations() {
        return findAll().stream().filter(Reservation::isDone).collect(Collectors.toList());
    }

    public List<Reservation> getUndoneReservations() {
        return findAll().stream().filter(r -> !r.isDone()).collect(Collectors.toList());
    }
}