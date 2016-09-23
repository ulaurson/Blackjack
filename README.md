Tegemist on blackjacki mänguga.

Komplireerimiseks kasutab rakendus gradle't.

Tarkvara on kompileeritav ehitustööriista gradle abil.

Tarkvara kompileerimiseks on vaja teha järgmised sammud:

	1. Arvutisse peab olema installeeritud gradle.
	
	2. Sisenege kausta, kus asub tarkvara lähtekood.
	
	3. Käsurealt jooksutage järgmine skrip:
		gradle build
	Antud käsk kompileerib faili blackjackServer.war, mis sisaldab serverit ja 
	faili blackjackClient.jar, mis sisaldab klienti, kes suhtleb serveriga.
	
	4. Serverit on võimalik käivitada kahel viisil:
		1. Käsurealt jooksutage järgmine skript:
			java -jar server/build/libs/blackjackServer.war
		Kui käivitate server sellisel viisil, siis peate muutma kliendi konfiguratsioonifaili (õpetus all).
		Seal peate muutma muutuja "server" väärtuse "/" ilma jutumärkideta.
		
		2. Paigutada blackjackServer.war standardsele rakendusserverile, näiteks Tomcat.
		war faili leiate server/build/libs/ kaustast.
		
	5. Muutke kliendi konfiguratsioonifaili vastavalt vajadusele (õpetus all).
	
	6. Kliendi käivitamiseks jooksutage järgmine skript:
		java -jar client/build/libs/blackjackClient.jar
	Käsk käivitab kliendi ja klient võib hakata mängima. 



Kliendi konfiguratsioonfaili seadistamine:

Konfiguratsioonifail "config.properties" asub client/src/main/resources kaustas.

Konfiguratsioonifaili parameetreid on vaja muuta selleks, et klient leiaks õige serveri üles.

See sisaldab kolme muutujat:

	host: Vaikeväärtus "localhost". Saate muuta serveri hosti.
	
	port: Vaikeväärtus "localhost". Saate muuta serveri porti.
	
	server: Vaikeväärtus "/blackjackServer/". Saate muuta serveri teed.


