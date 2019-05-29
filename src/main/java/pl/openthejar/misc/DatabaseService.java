package pl.openthejar.misc;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.Reservation;
import pl.openthejar.model.WorkDate;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseService {

    private ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of("Europe/Warsaw")).plusHours(2);
    private ZonedDateTime nextStartDate = currentDate.withHour(0).withMinute(0).withSecond(0);

    private static class AddWorkDates implements Runnable {

        private static final int SECONDS_IN_DAY = 86400;
        private static final int SECONDS_IN_15_MINUTES = 900;

        private EntityDao<WorkDate> dao = new EntityDao<>(WorkDate.class);

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
                dao.saveOrUpdate(workDate);
                unixTime += SECONDS_IN_15_MINUTES;
            }
        }
    }

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
        workDatesScheduler.scheduleAtFixedRate(new AddWorkDates(), delay,  TimeUnit.HOURS.toSeconds(1), TimeUnit.SECONDS);
    }

    public static AddWorkDates getAdder() {
        return new AddWorkDates();
    }

    public static ReservationsChecker getReservationsChecker() {
        return new ReservationsChecker();
    }
}
