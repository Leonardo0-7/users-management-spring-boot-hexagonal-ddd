package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

public record DatabaseConfig(
    String host,
    int port,
    String databaseName,
    String username,
    String password,
    String sslMode) {

  private static final String MYSQL_URL_TEMPLATE =
      "jdbc:mysql://%s:%d/%s?sslMode=%s&serverTimezone=UTC&allowPublicKeyRetrieval=true";

  private static final String PG_URL_TEMPLATE =
      "jdbc:postgresql://%s:%d/%s?sslmode=%s";

  public String buildJdbcUrl() {
    if (isPostgreSql()) {
      final String ssl = (sslMode == null || sslMode.isBlank() || "DISABLED".equalsIgnoreCase(sslMode))
          ? "disable"
          : "require";
      return String.format(PG_URL_TEMPLATE, host, port, databaseName, ssl);
    }
    return String.format(MYSQL_URL_TEMPLATE, host, port, databaseName, sslMode);
  }

  public boolean isPostgreSql() {
    return port == 5432 || (host != null && (host.contains("supabase") || host.contains("postgres") || host.contains("render.com") || host.contains("dpg-")));
  }
}
