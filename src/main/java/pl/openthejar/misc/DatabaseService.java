package pl.openthejar.misc;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Product;
import pl.openthejar.model.ProductType;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.WorkDate;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Klasa zarzadzajaca watkami pomocniczymi w tle
 */
public class DatabaseService {

    private ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of("Europe/Warsaw")).plusHours(2);
    private ZonedDateTime nextStartDate = currentDate.withHour(0).withMinute(0).withSecond(0);

    /**
     * Klasa dodajaca kolejne terminy na nastepny dzien oraz dodajaca ilosc produktow
     */
    private static class DailyChecker implements Runnable {

        private static final int SECONDS_IN_DAY = 86400;
        private static final int SECONDS_IN_15_MINUTES = 900;

        private static final int PRODUCT_QUANTITY = 10;

        private EntityDao<WorkDate> workDateDao = new EntityDao<>(WorkDate.class);
        private EntityDao<Product> productDao = new EntityDao<>(Product.class);
        private EntityDao<ProductType> productTypeDao = new EntityDao<>(ProductType.class);

        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            Date todayDate = new Date();
            cal.setTime(todayDate);
            cal.set(Calendar.HOUR_OF_DAY, 12);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            todayDate = cal.getTime();

            long unixTime = (todayDate.getTime() / 1000L) + SECONDS_IN_DAY;

            for (int i = 0; i < 32; i++) {
                WorkDate workDate = new WorkDate(new Date(unixTime * 1000L));
                workDateDao.saveOrUpdate(workDate);
                unixTime += SECONDS_IN_15_MINUTES;
            }

            List<Product> products = productDao.findAll();

            productTypeDao.findAll().forEach(productType -> {
                Optional<Product> find = products.stream().filter(p -> p.getType().equals(productType)).findAny();
                if (find.isPresent()) {
                    Product product = find.get();
                    product.setQuantity(product.getQuantity() + PRODUCT_QUANTITY);
                    productDao.saveOrUpdate(product);
                } else {
                    Product product = new Product(productType, PRODUCT_QUANTITY);
                    productDao.save(product);
                }
            });
        }
    }

    /**
     * Klasa zmieniajaca typ rezerwacji na ukonczony
     */
    private static class ReservationsChecker implements Runnable {

        private EntityDao<Reservation> dao = new EntityDao<>(Reservation.class);
        private static final int SECONDS_IN_15_MINUTES = 900;

        @Override
        public void run() {
            dao.findAll().forEach(r -> {
                if (r.getWorkDate() != null && r.getWorkDate().getDate().before(new Date(System.currentTimeMillis() - SECONDS_IN_15_MINUTES))) {
                    r.setDone(true);
                    dao.saveOrUpdate(r);
                }
            });
        }
    }

    /**
     * Konstruktor klasy bazowej, ktory inicjuje watki
     */
    public DatabaseService() {
        if (currentDate.compareTo(nextStartDate) > 0) {
            nextStartDate = nextStartDate.plusDays(1);
        }

        long findTime = currentDate.toEpochSecond();
        while (findTime % 900 != 0) {
            findTime++;
        }

        Duration duration = Duration.between(currentDate,
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(findTime), ZoneId.of("Europe/Warsaw")));
        long delay = duration.getSeconds();
        delay += 5;
        ScheduledExecutorService reservationScheduler = Executors.newScheduledThreadPool(1);
        reservationScheduler.scheduleAtFixedRate(new ReservationsChecker(), delay,  TimeUnit.MINUTES.toSeconds(15), TimeUnit.SECONDS);

        Duration secondDuration = Duration.between(currentDate, nextStartDate);
        delay = secondDuration.getSeconds();
        ScheduledExecutorService workDatesScheduler = Executors.newScheduledThreadPool(1);
        workDatesScheduler.scheduleAtFixedRate(new DailyChecker(), delay,  TimeUnit.HOURS.toSeconds(1), TimeUnit.SECONDS);
    }

    public static DailyChecker getDailyChecker() {
        return new DailyChecker();
    }

    public static ReservationsChecker getReservationsChecker() {
        return new ReservationsChecker();
    }
}
