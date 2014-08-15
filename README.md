Lemo-Data-Management-Server
===========================
--------------------------------------------------------------------------------
Inhaltsverzeichnis
--------------------------------------------------------------------------------
 - Links
 - Beschreibung
 - Quickinstaller
 - Lizenz

--------------------------------------------------------------------------------
Links
--------------------------------------------------------------------------------
Lemo Website
  http://lemo.htw-berlin.de/

Lemo Application Server Sourcecode
	https://github.com/LemoProject/Lemo-Application-Server

Lemo Datamanagement Server Sourcecode
	https://github.com/LemoProject/Lemo-Data-Management-Server

Lemo Project Download
	http://lemo.htw-berlin.de/Download/

--------------------------------------------------------------------------------
Beschreibung
--------------------------------------------------------------------------------
LeMo ist eine webbasierte Learning Analytics Anwendung. Die Plattform erlaubt
es Benutzern, Daten aus verschiedenen Learning Management Systemen und aus 
Logdateien von Servern zu importieren und zu analysieren. Momentan werden die 
Plattformen Moodle 1.9, Moodle 2.1 - 2.4 und Clix unterstützt. LeMo bietet eine 
breite Palette von Analysen und Visualisierungen, u.a. wie sich die
Kursnutzung über die Zeit verhält, die Verwendung von Lernobjekten,
Navigationspfade, Sequential Pattern Mining von häufig genutzten Pfaden und die
Visualisierung der Test Performance der LMS-Nutzer.

--------------------------------------------------------------------------------
Quickinstaller
--------------------------------------------------------------------------------
---------------------------------------
1 Systemvoraussetzungen
---------------------------------------
Um Lemo nutzen zu können, müssen Tomcat 6 und MySQL 5.5 installiert sein.
Es müssen mindestens 2 GB RAM vorhanden sein.

---------------------------------------
2 Download
---------------------------------------
Download Lemo 0.7 Bundle:
http://www.lemo-projekt.de/downloads/lemo.tar.gz

Das Archiv enthält:

war-Archiv des Data-Management-Server (DMS): lemo.war
war-Archiv des Aplication-Server (APS): lemo#dms.war
Beispielkonfiguration: lemo.xml
generierte Testdaten lemo_mining.sql
diese Installationsanleitung: README.txt

---------------------------------------
3 Datenbank vorbereiten
---------------------------------------
Für den DMS und APS muss jeweils eine Datenbank angelegt werden. Zusätzlich 
muss ein Datenbanknutzer mit Zugriffsrechten erstellt werden. Die Lemo-
Testdaten stehen als SQL-Dump mit künstlich generierten Kursdaten zu Verfügung
und können in die Datenbank des DMS geladen werden.

Die MySQL-Konsole kann im Terminal mit mysql -u root -p aufgerufen werden.
Folgende Befehle sind einzugeben.

CREATE DATABASE lemo_mining;
CREATE DATABASE lemo_apps;
CREATE USER `lemo`;
GRANT ALL PRIVILEGES ON lemo_mining.* TO
	lemo@localhost IDENTIFIED BY 'lemo1234';
GRANT ALL PRIVILEGES ON lemo_apps.* TO 
	lemo@localhost IDENTIFIED BY 'lemo1234';
EXIT

Mit dem folgendem Befehl werden die generierten Daten in die Datenbank geladen.

mysql -u root -p lemo_mining < lemo_mining.sql

---------------------------------------
4 Deployment
---------------------------------------
Der Pfad zum Tomcat-Server kann je nach verwendetem Betriebsystem oder 
Installationsart variieren. Einige typische Pfade sind:

z.B. in Linux: /var/lib/tomcat6/
z.B. in OS X: /Library/tomcat
z.B. in Windows mit xampp ...\xampp\tomcat\

-------------------
4.1 Konfiguration
-------------------
Die lemo.xml muss ins lib-Verzeichnis von Tomcat kopiert werden.

Das lib-Verzeichnis muss erstellt werden falls es nicht existiert.
Falls nicht die standardmäßige URL und Port localhost:3306 für MySQL genutzt
wird, muss dieses in der lemo.xml geändert werden. Hierzu muss der Wert von
<property name="hibernate.connection.url">...</property> angepasst werden.

-------------------
4.2 Webapps
-------------------
Die lemo.war und lemo#dms.war müssen ins webapps-Verzeichnis von Tomcat
kopiert werden.

---------------------------------------
5. Tomcat neu starten
---------------------------------------
z.B. mit /etc/init.d/tomcat6 restart oder über den XAMPP-Manager.

---------------------------------------
6. Installation Testen
---------------------------------------
Wenn im Browser die URL localhost:8080/lemo aufgerufen wird, sollte die
Startseite von Lemo zu sehen sein. Nach dem Login mit dem vordefinierten
Nutzer-Account (Name: user, Passwort: lemolemo) sollte unter dem Menüpunkt
"Meine Kurse" eine Übersicht von Kurs 1.1.1 dargestellt werden. Mit dem
Admin-Account (Name: admin, Passwort: lemolemo) können Nutzer verwaltet werden.

---------------------------------------
7. Anbindung an LMS
---------------------------------------
Folgende LMS/Versionen werden unterstützt.

Moodle 1.9
Moodle 2.1-2.4
Clix 2010

-------------------
7.1 Vorausetzungen
-------------------

Es muss ein Datenbank-Nutzer mit lesendem Zugriff auf die Datenbank des LMS
eingerichtet sein. Die Einrichtung dieses Nutzers muss durch einen
LMS-Administrator vorgenommen werden.

-------------------
7.2 Konnektor anlegen
-------------------
Der Konnektor für die Anbindung an das LMS wird in die Datei lemo.xml
eingetragen. Folgende Attribute müssen konfiguriert werden.

Anzeigename des Konnektors
einmalige ID des Konnektors
Platform-Typ (z.B. moodle_1_9, moodle_2_3 oderclix_2010)
Datenbank-URL
Datenbank-Nutzername
Datenbank-Passwort
Hibernate-SQL-Dialect
IDs der zu importierenden Kurse (Wenn keine IDs angegeben sind werden
standardmäßig alle Kurse importiert)
In der bereistgestellten lemo.xml sind Beispiele für die Konfiguration von
Konnektoren zu finden.
Tomcat muss anschließend neu gestartet werden, um die Konfiguration zu
übernehmen.

---------------------------------------
8. Import
---------------------------------------
Der Datenimport wird über das Admin-Dashboard eingeleitet. Im Abschnitt
"Datenadministration" werden alle Konnektoren aufgelistet. Der Import wird
gestartet, wenn "Starte Aktualisierung" angeklickt wird.

Die Dauer der Importaktion ist abhängig von der Anzahl der Kurse. Der erste
Import kann länger dauern, von einigen Minuten bis zu einer Stunde beim
kompletten Import einer großen Datenbank.
Für weitere Aufgaben wie das Anlegen von Nutzern oder das Aktivieren von
inkrementellen Imports sehen Sie in das Administratorhandbuch.

---------------------------------------
9 Hinweise
---------------------------------------
Es sollte aus Sicherheitsgründen darauf geachtet werden, dass bei einer
lokalen Installation Tomcat und MySQL nicht von außerhalb des Systems zu
erreichen sind.

Wird die Lemo-Anwendung auf einem Server betrieben, müssen die vordefinierten
Datenbank-Passwörter in der lemo.xml neu gewählt werden. Auch die die
Passwörter der vorgegebenen Nutzer müssen durch den Administrator geändert
werden.

Unter Umständen kann es passieren, dass die Anwendung mehr Speicher benötigt,
als Tomcat zur Verfügung steht. Die Umgebungsvariable JAVA_OPTS kann in diesem
Fall genutzt werden, um die von Tomcat nutzbare Speichergröße zu erhöhen.

In OS X / Linux mit dem Befehl:

export JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=128M"
In Windows mit dem Befehl:

set JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=128M

--------------------------------------------------------------------------------
Lizenz
--------------------------------------------------------------------------------
Copyright (C) 2013
Beuth Hochschule Berlin, Hochschule für Technik und Wirtschaft Berlin,
Hochschule für Wirtschaft und Recht Berlin

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
