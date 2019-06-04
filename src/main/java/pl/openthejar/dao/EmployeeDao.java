package pl.openthejar.dao;

import pl.openthejar.misc.DateUtils;
import pl.openthejar.model.Employee;
import pl.openthejar.model.Reservation;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pracownika
 */
public class EmployeeDao extends EntityDao<Employee> {

    /**
     * Konstruktor tworzacy DAO
     */
    public EmployeeDao() {
        super(Employee.class);
    }

    /**
     * Pobiera wszystkie dzisiejsze rezerwacje pracownika
     * @param employee Pracownik, dla ktorego maja zostac pobrane rezerwacje
     * @return Lista dzisiejszych rezerwacji
     */
    public List<Reservation> getTodaysReservations(Employee employee) {
        final String query = "SELECT r FROM Reservation r";
        TypedQuery<Reservation> typedQuery = entityManager.createQuery(query, Reservation.class);

        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : typedQuery.getResultList()) {
            if (reservation.getWorkDate() != null
                    && DateUtils.isToday(reservation.getWorkDate().getDate())
                    && reservation.getWorkDate().getEmployees().contains(employee)) {
                result.add(reservation);
            }
        }

        return result;
    }

    /**
     * Pobiera wszystkie rezerwacje pracownika
     * @param employee Pracownik, dla ktorego maja zostac pobrane rezerwacje
     * @param done Parametr okreslajacy czy pobrac rezerwacje skonczone
     * @return Lista rezerwacji
     */
    public List<Reservation> getReservations(Employee employee, Boolean done) {
        final String query = "SELECT r FROM Reservation r";
        TypedQuery<Reservation> typedQuery = entityManager.createQuery(query, Reservation.class);

        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : typedQuery.getResultList()) {
            if (reservation.getWorkDate() != null && reservation.getWorkDate().getEmployees().contains(employee)) {
                if ((done == null) || (done == reservation.isDone())) {
                    result.add(reservation);
                }
            }
        }

        return result;
    }

    /**
     * Pobiera wszystkie miesieczne rezerwacje pracownika
     * @param employee Pracownik, dla ktorego pobierane sa rezerwacje
     * @return Lista rezerwacji
     */
    public List<Reservation> getMonthlyReservations(Employee employee) {
        final String query = "SELECT r FROM Reservation r";
        TypedQuery<Reservation> typedQuery = entityManager.createQuery(query, Reservation.class);

        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : typedQuery.getResultList()) {
            if (reservation.getWorkDate() != null
                    && DateUtils.isThisMonth(reservation.getWorkDate().getDate())
                    && reservation.getWorkDate().getEmployees().contains(employee)) {
                result.add(reservation);
            }
        }

        return result;
    }
}
