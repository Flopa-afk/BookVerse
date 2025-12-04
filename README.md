BookVerse — Database setup and running (Windows PowerShell)

Problem: "No suitable driver found for jdbc:mysql://..." shown when placing an order.

Cause: The MySQL JDBC driver (Connector/J) is not on the application's classpath at runtime.

Quick fixes (choose one):

1) Add Connector/J jar to classpath when running from command line
- Download the driver (mysql-connector-java-8.x.x.jar) from https://dev.mysql.com/downloads/connector/j/
- Put the jar in a `lib` folder inside the project, for example `BookVerse\lib\mysql-connector-java-8.x.x.jar`.
- Compile and run with the jar on the classpath (PowerShell example):

```powershell
# compile (if needed)
javac -d bin -cp ".;lib\mysql-connector-java-8.x.x.jar" src\\*.java

# run mainPage (adjust paths to your compiled classes)
java -cp ".;lib\mysql-connector-java-8.x.x.jar;bin" mainPage
```

2) Add the jar to your IDE project's libraries
- In IntelliJ: File → Project Structure → Libraries → + → add the jar.
- In Eclipse: Right click project → Build Path → Configure Build Path → Libraries → Add External JARs...

3) Use a proper build tool (recommended)
- If you migrate to Maven or Gradle, add the dependency `mysql:mysql-connector-java:8.0.x` and the driver will be handled automatically.

Database creation (phpMyAdmin or SQL):

```sql
CREATE DATABASE bookverse CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- (optional) create user
CREATE USER 'bookuser'@'localhost' IDENTIFIED BY 'StrongPassHere';
GRANT ALL PRIVILEGES ON bookverse.* TO 'bookuser'@'localhost';
FLUSH PRIVILEGES;
```

Configuration
- Update `DBHelper.java` constants `URL`, `USER`, `PASS` to match your local MySQL configuration.

Notes in code
- `DBHelper` now attempts to load the driver at class-load time and prints a helpful message if it's missing.
- If you still see "No suitable driver found", the driver jar is not on the runtime classpath.

If you want, I can:
- Add a `config.properties` to keep credentials out of source,
- Add a small script `run.ps1` to compile and run with the connector on Windows,
- Or switch the project to Maven/Gradle and add dependency management. 
