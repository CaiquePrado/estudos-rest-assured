package real_world_test_application.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
  public String getDateWithDifferenceOfDays(Integer daysQuantity) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, daysQuantity);
      return getDateFormatted(calendar.getTime());
  }

  public String getDateFormatted(Date date) {
      DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      return format.format(date);
  }
}