# Instructions to Run Project
When you clone the project, you have to add this run config: --module-path javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml

You also need a path to the javafx-sdk-11.0.2 folder in Project Structure -> Libraries

**Don't commit any datasource files in the .idea folder, since these will be different for everyone's local databases**

- - - -
### To Use Your Local Database: ###
You'll need SQL Server Express installed on your laptop, since this uses SQL Express.

SQL Server Express 2017: https://www.microsoft.com/en-us/sql-server/sql-server-editions-express

SQL Server Management Studio (download version 17.9.1): https://docs.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-2017

The SQLEXPRESS service should also be running on your computer.

* Add a path in Project Structure -> Libraries to the jdbcjar folder

* Copy the sqljdbc_auth.dll from the jdbcjar folder in the project and paste it to your JDK bin in C:\Program Files\Java\jdk-11.0.2\bin
   * This is for Windows authentication to work so we don't have to use a username & password.
* By default, IntelliJ will use the "master" database (under Databases -> System Databases in SQL Studio), so any tables you add will go there
   * To create tables, go to "New Query" in SQL Studio and run the create scripts from the Drive (but have these lines at the start of the query: USE databaseName; GO)
   * You can insert data into the tables to test the application by INSERT statements in SQL Studio, or by BULK INSERT using a .csv
