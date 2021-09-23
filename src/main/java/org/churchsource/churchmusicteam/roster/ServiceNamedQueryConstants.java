package org.churchsource.churchmusicteam.roster;

public class ServiceNamedQueryConstants {

  public static final String NAME_FIND_SERVICE_BY_ID = "Service.findServiceById";
  public static final String QUERY_FIND_SERVICE_BY_ID = "SELECT s FROM Service s WHERE s.id = :id";

}
