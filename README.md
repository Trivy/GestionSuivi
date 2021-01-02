## JIBApp

### A program to follow and manage investments using a SQL database.

Main class: JIBApp.java, the program then runs in a window (no output, just modification of the database).

It uses a PostgreSQL database to store data.

### Quickstart

* The JIBApp.jar file enables to run the interface.
* However, to get a fully functional system, you need to create a PostgreSQL database (default name: "jibapp", default user: "postgres"). A minimal database is provided by the files baseDeDonnees.sql and InitialisationBdD.sql (which create and populate the database, respectively). Once in PostgreSQL, type:
```
create database jibapp;
\c jibapp
\i path\to\folder\baseDeDonnees.sql
\i path\to\folder\InitialisationBdD.sql
```
* An overview of basic functions:
   * When connecting to the database, select "jibapp" and use your chosen username.
   * You land on the "Accueil" page (homepage). Select the page (and the
     associated operations) using the combo at the top center of the page.
   * To add a new operation on a given fund, go to the "Opérations et cours" page.
     Select the relevant fund with the "Placement sélectionn"" combo (right and slightly below the previous combo).
     You can then create a new transaction with the 'Ajouter une ligne' button
     (bottom left). To create a new order, use the tab 'Ordres'. With the last tab
     'Cours', you can update the value of the investment by providing a Date and a quotation.
   * The "Edition" page enables you to edit the basic properties of objects in the database.

### Exporting a new version of JIBApp

In Eclipse, select `File > Export > Runnable JAR file > Launch config: Gestion - suivi` and choose a suitable name for the resulting JAR. `Package required libraries into generated JAR` is the Library packaging option to prefer.

### About the structure

Packages:
* "accueil" - default/initial panel
* "aexecuter" - panel with the alerts on quotations
* "compta" - Java side of objects in the database (Pojo)
* "data" contains the commands for the database (and in particular the subpackage DAO)
* "edition" - edition panel of the program (where new investments can be defined, for instance)
* "observer" for an observer pattern
* "operation" - operation panel, where buying and selling investments are recorded
* "util" - classes used in several different panels.
* "sqliteImport" - provides functions for an import function from an Sqlite database.

The git repository contains only the source codes, so the best option to edit it in Eclipse is to clone the repository in .../JIBapp/src/gestion (to avoid any problem with packages...).

## Licence

All rights reserved Olivier GABRIEL and SoGaR 2018, 2021.
