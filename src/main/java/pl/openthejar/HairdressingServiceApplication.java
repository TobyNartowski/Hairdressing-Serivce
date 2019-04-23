package pl.openthejar;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.*;

import java.util.Arrays;
import java.util.HashSet;

public class HairdressingServiceApplication {

    public static void main(String[] args) {
        Client client = new Client("John", "Doe", 123123123L);
        client.addDiscount(new Discount("Regular customer", 10));

        Reservation reservation = new Reservation();

        Service service = new Service("First service", 600, 1000);
        service.setProducts(new HashSet<>(Arrays.asList(
                new Product(new ProductType("First service", 100), 3),
                new Product(new ProductType("Second service", 130), 2)
        )));

        reservation.addReview(new Review("Title", "Content"));
        reservation.addService(service);
        client.addReservation(reservation);

        EntityDao<Client> clientDao = new EntityDao<>(Client.class);
        clientDao.save(client);
        clientDao.cleanUp();
    }
}
