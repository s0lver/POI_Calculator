package tamps.cinvestav.s0lver.databaseaccess;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class QueryConstants {
    public final static SimpleDateFormat SIMPLE_DATE_FORMAT_HUMAN = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_FOR_SQLITE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static final int TOTAL_SECONDS_IN_DAY = 24 * 60 * 60;
    public static final int TOTAL_MINUTES_IN_DAY = 24 * 60;
    public static final String QUERY_GET_ALL_VISITS = "SELECT * FROM %s ORDER BY arrivalTime;";
    public static final String QUERY_GET_VISITS_FROM_ABSOLUTE_TIMESTAMP = "SELECT * FROM %1$s WHERE departureTime>'%2$s' AND departureTime < '%3$s' " +
            "UNION SELECT * FROM %1$s WHERE arrivalTime > '%2$s' AND arrivalTime < '%3$s' " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_HOURLY_INTERVAL_TRESPASSING_DAY = "SELECT * FROM %1$s WHERE CAST(strftime('%%H', departureTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', departureTime) AS INTEGER) <= %3$s " +
            "UNION SELECT * from %1$s WHERE CAST(strftime('%%H', arrivalTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', arrivalTime) AS INTEGER) <= %3$s " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_HOURLY_INTERVAL_NOT_TRESPASSING_DAY = "SELECT * FROM %1$s WHERE (CAST(strftime('%%H', departureTime) AS INTEGER)) BETWEEN %2$s AND %3$s " +
            "UNION SELECT * FROM %1$s WHERE (CAST(strftime('%%H', arrivalTime) AS INTEGER)) BETWEEN %2$s AND %3$s " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_DAY_OF_WEEK = "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', departureTime) AS INTEGER) = %2$s " +
                    "UNION " +
                    "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', arrivalTime) AS INTEGER) = %2$s " +
                    "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_DAY_INTERVAL_TRESPASSING_WEEK = "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', departureTime) AS INTEGER) >= %2$s " +
                    "OR CAST(strftime('%%w', departureTime) AS INTEGER) <= %3$s " +
                    "UNION " +
                    "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', arrivalTime) AS INTEGER) >= %2$s " +
                    "OR CAST(strftime('%%w', arrivalTime) AS INTEGER) <= %3$s " +
                    "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_DAY_INTERVAL_NOT_TRESPASSING_WEEK = "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', departureTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
                    "UNION " +
                    "SELECT * FROM %1$s " +
                    "WHERE CAST(strftime('%%w', arrivalTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
                    "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_TRESPASSING_DAY = "SELECT * FROM %1$s WHERE (CAST(strftime('%%H', departureTime) AS INTEGER) >= %2$s AND CAST(strftime('%%w', departureTime) AS INTEGER) = %4$s) " +
            "OR (CAST(strftime('%%H', departureTime) AS INTEGER) <= %3$s AND CAST(strftime('%%w', departureTime) AS INTEGER) = (%4$s + 1) %% 7) " +
            "UNION SELECT * FROM %1$s WHERE (CAST(strftime('%%H', arrivalTime) AS INTEGER) >= %2$s AND CAST(strftime('%%w', arrivalTime) AS INTEGER) = %4$s) " +
            "OR (CAST(strftime('%%H', arrivalTime) AS INTEGER) <= %3$s AND CAST(strftime('%%w', arrivalTime) AS INTEGER) = (%4$s + 1) %% 7) " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_NOT_TRESPASSING_DAY = "SELECT * FROM %1$s WHERE CAST(strftime('%%H', departureTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
            "AND CAST(strftime('%%w', departureTime) AS INTEGER) = %4$s " +
            "UNION SELECT * FROM %1$s WHERE CAST(strftime('%%H', arrivalTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
            "AND CAST(strftime('%%w', arrivalTime) AS INTEGER) = %4$s " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_INTERVAL_TRESPASSING_DAY_TRESPASSING_WEEK = "SELECT * FROM %1$s WHERE (CAST(strftime('%%H', departureTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', departureTime) AS INTEGER) <= %3$s) " +
            "AND (CAST(strftime('%%w', departureTime) AS INTEGER) >= %4$s OR CAST(strftime('%%w', departureTime) AS INTEGER) <= %5$s) " +
            "UNION SELECT * FROM %1$s WHERE (CAST(strftime('%%H', arrivalTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', arrivalTime) AS INTEGER) <= %3$s) " +
            "AND (CAST(strftime('%%w', arrivalTime) AS INTEGER) >= %4$s \tOR CAST(strftime('%%w', arrivalTime) AS INTEGER) <= %5$s) " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_INTERVAL_TRESPASSING_DAY_NOT_TRESPASSING_WEEK = "SELECT * FROM %1$s WHERE (" +
            "(CAST(strftime('%%H', departureTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', departureTime) AS INTEGER) <= %3$s) " +
            "AND CAST(strftime('%%w', departureTime) AS INTEGER) BETWEEN %4$s AND %5$s " +
            ") " +
            "OR (" +
            "(CAST(strftime('%%H', arrivalTime) AS INTEGER) >= %2$s OR CAST(strftime('%%H', arrivalTime) AS INTEGER) <= %3$s) " +
            "AND CAST(strftime('%%w', arrivalTime) AS INTEGER) BETWEEN %4$s AND %5$s" +
            ") " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_INTERVAL_NOT_TRESPASSING_DAY_TRESPASSING_WEEK = "SELECT * FROM %1$s WHERE (CAST(strftime('%%H', departureTime) AS INTEGER)) BETWEEN %2$s AND %3$s " +
            "AND (CAST(strftime('%%w', departureTime) AS INTEGER) >= %4$s OR CAST(strftime('%%w', departureTime) AS INTEGER) <= %5$s) " +
            "UNION " +
            "SELECT * FROM %1$s WHERE (CAST(strftime('%%H', arrivalTime) AS INTEGER)) BETWEEN %2$s AND %3$s " +
            "AND (CAST(strftime('%%w', arrivalTime) AS INTEGER) >= %4$s OR CAST(strftime('%%w', arrivalTime) AS INTEGER) <= %5$s) " +
            "ORDER BY arrivalTime";

    public static final String QUERY_GET_VISITS_FROM_HOUR_INTERVAL_AND_DAY_INTERVAL_NOT_TRESPASSING_DAY_NOT_TRESPASSING_WEEK = "SELECT * FROM %1$s WHERE CAST(strftime('%%H', departureTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
            "AND CAST(strftime('%%w', departureTime) AS INTEGER) BETWEEN %4$s AND %5$s " +
            "UNION " +
            "SELECT * FROM %1$s " +
            "WHERE CAST(strftime('%%H', arrivalTime) AS INTEGER) BETWEEN %2$s AND %3$s " +
            "AND CAST(strftime('%%w', arrivalTime) AS INTEGER) BETWEEN %4$s AND %5$s " +
            "ORDER BY arrivalTime";
}
