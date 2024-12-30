FROM jboss/wildfly:34.0.1.Final

# Копируем файл WAR в WildFly
COPY build/libs/Backend-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/backend.war

# Устанавливаем драйвер PostgreSQL
ADD https://jdbc.postgresql.org/download/postgresql-42.6.0.jar /opt/jboss/wildfly/standalone/deployments/

# Конфигурация базы данных
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
RUN /opt/jboss/wildfly/bin/standalone.sh -c standalone.xml --server-config=standalone.xml & sleep 10 && \
    /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="data-source add --name=PostgresDS --driver-name=postgresql-42.6.0.jar --connection-url=jdbc:postgresql://db:5432/appdb --jndi-name=java:/PostgresDS --user-name=postgres --password=root --use-ccm=true --driver-class=org.postgresql.Driver --valid-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.postgresql.PostgreSQLValidConnectionChecker --exception-sorter-class-name=org.jboss.jca.adapters.jdbc.extensions.postgresql.PostgreSQLExceptionSorter" && \
    /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="reload"

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
