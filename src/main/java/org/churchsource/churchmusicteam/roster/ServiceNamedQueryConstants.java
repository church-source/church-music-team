package org.churchsource.churchmusicteam.roster;

public class ServiceNamedQueryConstants {

  public static final String NAME_FIND_SERVICE_BY_ID = "Service.findServiceById";
  public static final String QUERY_FIND_SERVICE_BY_ID = "SELECT s FROM Service s WHERE s.id = :id";

  public static final String NAME_FIND_SERVICE_BY_DATE_AND_TYPE = "Service.findServiceByDateAndType";
  public static final String QUERY_FIND_SERVICE_BY_DATE_AND_TYPE = "SELECT s FROM Service s WHERE " +
          "(:includeDeleted = TRUE OR s.deleted = false) " +
          "AND (:type is null OR s.serviceType = :type) " +
          "AND s.serviceDate = :date";

  public static final String NAME_GET_SERVICES_BETWEEN_DATES = "Service.findServiceBetweenDates";
  public static final String QUERY_GET_SERVICES_BETWEEN_DATES = "SELECT s FROM Service s WHERE " +
          "(:includeDeleted = TRUE OR s.deleted = false) " +
          "AND s.serviceDate >= :startDate AND s.serviceDate < :endDate";
}
