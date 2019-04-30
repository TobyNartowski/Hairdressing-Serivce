package pl.openthejar;

import org.hibernate.jdbc.Work;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class HairdressingServiceApplication {

    public static void main(String[] args) {
        EntityDao<Product> entityDao = new EntityDao<>(Product.class);
        entityDao.findAll().forEach(System.out::println);
        System.exit(0);

        //loadDummyData();
    }

    public static void loadDummyData() {
        Client client = new Client("John", "Doe", 123123123L);
        client.addDiscount(new Discount("Regular customer", 10));

        Reservation reservation = new Reservation();

        Service service = new Service("First service", 600, 1000);
        service.setProducts(new HashSet<>(Arrays.asList(
                new Product(new ProductType("First product", 100), 3),
                new Product(new ProductType("Second product", 130), 2)
        )));

        reservation.setReview(new Review("Title", "Content"));
        reservation.setService(service);

        Employee employee = new Employee("employee1", "0x1acf2137ff", "Ricardo", "Milos");
        WorkDate workDate = new WorkDate(new Date(1556015700L), employee);
        reservation.setWorkDate(workDate);

        client.addReservation(reservation);

        EntityDao<Client> clientDao = new EntityDao<>(Client.class);
        clientDao.save(client);
        clientDao.cleanUp();
    }
}
