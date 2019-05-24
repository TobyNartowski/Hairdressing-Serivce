package pl.openthejar.dao;

import pl.openthejar.model.Reservation;

public class ReservationDao extends EntityDao<Reservation> {

    public ReservationDao() {
        super(Reservation.class);
    }

    public Reservation changeStatus(Reservation reservation, boolean status) {
        reservation.setDone(status);
        saveOrUpdate(reservation);
        return reservation;
    }
}
