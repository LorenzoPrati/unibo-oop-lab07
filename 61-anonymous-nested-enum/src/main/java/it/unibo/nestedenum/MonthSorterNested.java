package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        private Month(final int days) {
            this.days = days;
        }

        public static Month fromString(final String name) {
            try {
                return Month.valueOf(name);
            } catch (IllegalArgumentException e) {
                Month match = null;
                int matchCount = 0;
                final List<Month> matchList = new LinkedList<>();
                for (final Month month : Month.values()) {
                    if (month.name().toLowerCase().startsWith(name.toLowerCase())) {
                        match = month;
                        matchCount++;
                        matchList.add(month);
                    }
                }
                switch (matchCount) {
                    case 0:
                        throw new IllegalArgumentException("No match has been found for " + name, e);
                    case 1:
                        return match;
                    /*
                    * Handle the case when one or more months have been considered a valid match
                    * after a first month has already be considered a valid match
                    */
                    default:
                        throw new IllegalArgumentException(
                            name + "is ambiguos. Valid matched are " + matchList.toString(),
                            e
                        );
                }
            }
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    private static class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String arg0, final String arg1) {
            return Month.fromString(arg0).compareTo(Month.fromString(arg1));
        }
    }

    private static class SortByDays implements Comparator<String> {
        @Override
        public int compare(final String arg0, final String arg1) {
            var a = Integer.valueOf(Month.fromString(arg0).days);
            var b = Integer.valueOf(Month.fromString(arg1).days);
            return Integer.compare(a, b);
        }
    }
}
