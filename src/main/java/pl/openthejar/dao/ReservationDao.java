package pl.openthejar.dao;

import pl.openthejar.model.Reservation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO rezerwacji
 */
public class ReservationDao extends EntityDao<Reservation> {

    /**
     * Konstruktor DAO
     */
    public ReservationDao() {
        super(Reservation.class);
    }

    /**
     * Zmienia status rezerwacji
     * @param reservation Rezerwacja, dla ktorej ma byc zmieniony status
     * @param status Status dla rezerwacji, ktory ma byc ustawiony
     * @return Rezerwacja, ktora zostala zmodyfikowana
     */
    public Reservation changeStatus(Reservation reservation, boolean status) {
        reservation.setDone(status);
        saveOrUpdate(reservation);
        return reservation;
    }

    /**
     * Pobiera wszystkie ukonczone rezerwacje
     * @return Lista ukonczonych rezerwacji
     */
    public List<Reservation> getDoneReservations() {
        return findAll().stream().filter(Reservation::isDone).collect(Collectors.toList());
    }

    /**
     * Pobiera wszystkie nieukonczone rezerwacje
     * @return Lista nieukonczonych rezerwacji
     */
    public List<Reservation> getUndoneReservations() {
        return findAll().stream().filter(r -> !r.isDone()).collect(Collectors.toList());
    }
}