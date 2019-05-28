package pl.openthejar.misc;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.WorkDate;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseService {

    private ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
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

    public DatabaseService() {
        if (currentDate.compareTo(nextStartDate) > 0) {
            nextStartDate = nextStartDate.plusDays(1);
        }

        Duration duration = Duration.between(currentDate, nextStartDate);
        long delay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new AddWorkDates(), delay,  TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    public static AddWorkDates getAdder() {
        return new AddWorkDates();
    }
}
