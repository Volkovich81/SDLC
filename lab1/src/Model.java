import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Model {
    private static final TreeMap<Integer, Long> HISTORICAL_DATA = new TreeMap<>();

    static {
        HISTORICAL_DATA.put(1600, 500_000_000L);
        HISTORICAL_DATA.put(1700, 650_000_000L);
        HISTORICAL_DATA.put(1800, 950_000_000L);
        HISTORICAL_DATA.put(1900, 1_600_000_000L);
        HISTORICAL_DATA.put(1950, 2_500_000_000L);
        HISTORICAL_DATA.put(1959, 3_000_000_000L);
        HISTORICAL_DATA.put(1974, 4_000_000_000L);
        HISTORICAL_DATA.put(1987, 5_000_000_000L);
        HISTORICAL_DATA.put(1998, 6_000_000_000L);
        HISTORICAL_DATA.put(2011, 7_000_000_000L);
        HISTORICAL_DATA.put(2025, 8_200_000_000L);
    }

    public long calculatePopulation(String dateStr) throws Exception {
        LocalDate inputDate = parseDate(dateStr);
        int year = inputDate.getYear();

        LocalDate today = LocalDate.now();
        if (inputDate.isAfter(today)) {
            throw new Exception("Нельзя вводить даты в будущем! Сегодня: " + today);
        }

        if (HISTORICAL_DATA.containsKey(year)) {
            return HISTORICAL_DATA.get(year);
        }

        Map.Entry<Integer, Long> lower = HISTORICAL_DATA.floorEntry(year);
        Map.Entry<Integer, Long> upper = HISTORICAL_DATA.ceilingEntry(year);

        if (lower == null) {
            return HISTORICAL_DATA.firstEntry().getValue();
        }
        if (upper == null) {
            return extrapolateForward(inputDate);
        }

        return interpolateByDay(inputDate, lower, upper);
    }

    private long interpolateByDay(LocalDate inputDate, Map.Entry<Integer, Long> lower, Map.Entry<Integer, Long> upper) {
        LocalDate lowerDate = LocalDate.of(lower.getKey(), 1, 1);
        LocalDate upperDate = LocalDate.of(upper.getKey(), 1, 1);

        long totalDays = ChronoUnit.DAYS.between(lowerDate, upperDate);
        long daysPassed = ChronoUnit.DAYS.between(lowerDate, inputDate);

        if (daysPassed < 0) return lower.getValue();
        if (daysPassed > totalDays) return upper.getValue();

        long popDiff = upper.getValue() - lower.getValue();
        double fraction = (double) daysPassed / totalDays;

        return lower.getValue() + (long)(popDiff * fraction);
    }

    private long extrapolateForward(LocalDate date) {
        LocalDate baseDate = LocalDate.of(2025, 1, 1);
        long basePop = HISTORICAL_DATA.get(2025);
        long daysAfter = ChronoUnit.DAYS.between(baseDate, date);
        if (daysAfter < 0) daysAfter = 0;
        long growthPerDay = 219_178L;
        return basePop + (daysAfter * growthPerDay);
    }

    private LocalDate parseDate(String dateStr) throws Exception {
        String[] parts = dateStr.split("\\.");
        if (parts.length != 3) {
            throw new Exception("Дата должна быть в формате ДД.ММ.ГГГГ");
        }

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (day < 1 || day > 31 || month < 1 || month > 12) {
            throw new Exception("Некорректная дата");
        }

        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new Exception("Некорректная дата (например, 31 апреля не существует)");
        }
    }
}