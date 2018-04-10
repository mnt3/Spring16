
### Aprašymas

Spring egzamino užduotis.

Realizuoti REST tipo servisą, naudojant **Spring Boot** technologiją.

### Vertinimas

Pažymį sudarys sėkmingai išpildytų reikalavimų kiekis, kodo stilius, papildomų užduočių išpildymas. 
Testų pabaigoje yra atspausdinamas **galimas** pažymys:
```
Score:         24/24
Avarage grade: 10.00
Grade:         10
```
Nusirašinėti, dalintis patarimais, kopijuoti iš kolegų github'o yra draudžiama.
Destytojų akis yra pakankamai įgudusi pamatyti copy-paste.

### Aplikacijos aprašymas

#### Aplikacijos paleidimas

Aplikacija gali būti paleista šiais būdais:

`$ mvn clean tomcat7:run`

Sėkmingai paleidus aplikaciją, adresu http://localhost:9092/spring-exam/ turėtumėte pamatyti tekstą: 'Spring-Exam 2016'.

Aplikacijos darinio (angl. *build*) paruošimas įvykdant testus:

`$ mvn clean verify`

#### Aplikacijos konfigūracija

Apache tomcat serverio port'as nurodomas pom.xml esančiame parametre `<server.port>9092</server.port>`.

### Reikalavimai

#### Veikianti aplikacija (19 taškų)

##### Realizuotas trūkstamas funkcionalumas (6 taškų iš 19)

Vadovaudamiesi klasių JavaDoc, testais bei šiame dokumente pateikta informacija, realizuokite trūkstamas aplikacijos dalis.

Klasės, kurias reikia realizuoti:
- `lt.itakademija.repository.SimpleDateProvider`
- `lt.itakademija.repository.SimpleSequenceNumberGenerator`
- `lt.itakademija.repository.InMemorySecurityEventsRepository`

Testas, kuris testuoja realizaciją: `lt.itakademija.JavaCodingTask` (galima paleisti per Eclipse aplinką)

##### Korektiškai sukonfigūruotas Spring aplikacijų kontekstas (4 taškai iš 19)

Korektiškai panaudotos Spring aplikacijų konteksto anotacijos (@Component, @Repository, @Autowired ir t.t.).

Klasės, kurių objektai turi būti valdomi Spring aplikacijų konteksto:
- `lt.itakademija.controller.SecurityServiceController`
- `lt.itakademija.repository.SimpleDateProvider`
- `lt.itakademija.repository.SimpleSequenceNumberGenerator`
- `lt.itakademija.repository.InMemorySecurityEventsRepository`

Testas, kuris testuoja realizaciją: `lt.itakademija.SpringConfigurationTask` (galima paleisti per Eclipse aplinką)

##### Korektiškai realizuotas REST serviso kontroleris (9 taškai iš 19)

Vadovaudamiesi šiame dokumente pateiktu REST serviso aprašymu bei integraciniu testu, realizuokite serviso kontrolerį iki galo.

Serviso kontrolerio klasė: `lt.itakademija.controller.SecurityServiceController`.

Serviso URL: http://localhost:9092/spring-exam/webapi/events

Testas, kuris testuoja realizaciją: `lt.itakademija.RestServicesTask`

#### REST serviso dokumentavimas ir publikavimas (5 taškai)

Aprašykite serviso operacijas, naudojant Swagger anotacijas.

Minimaliai turi būti aprašyta:
- Operacijos
- Parametrai

Dokumentacijos URL: http://localhost:9092/spring-exam/swagger-ui.html

Testas, kuris testuoja realizaciją: `lt.itakademija.SwaggerDocTask` (testas tikrina tik dokumentacijos prieinamumą)

### Papildoma užduotis (2 balai prie galutinio pažymio)

---

#### Užduotis (1 balas)

Papildykite servisą galimybe filtruoti gaunamus registruotus įvykius pagal:

- datų intervalą. Parametrai: dateFrom, dateTill.
- pagal lokaciją. Parametras: location.
- pagal aprašymą. Parametras: description.

###### Parametrai:

| Parametras    | Duomenų tipas | Aprašymas  |
| ------------- |:------------- | :-----     |
| dateFrom      | `date-time`   | Data nuo.  |
| dateTill      | `date-time`   | Data iki.  |
| location      | `string`      | Teskto fragmento įvykių filtravimui pagal lokaciją. |
| description   | `string`      | Teskto fragmento įvykių filtravimui pagal aprašymą. |

###### Pavyzdys:

```
GET /webapi/events?dateFrom=2016-12-18T14:26:37.162Z&dateTill=2016-12-18T14:26:37.162Z&description=test&location=Vil
```
---

#### Užduotis (0,5 balo)

Papildykite servisą nauja operacija registruoto įvykio informacijos gavimui pagal ID.

###### Parametrai:

| Parametras    | Duomenų tipas | Aprašymas  |
| ------------- |:------------- | :-----|
| eventId       | `string`      | Ieškomo įvykio ID. |

###### Pavyzdys:

```
GET /webapi/events/1
```

#### Užduotis (0,5 balo)

Sukurkite web filtrą, kuris žurnalizuotų visas HTTP užklausas naudojant `SLF4J` žurnalizavimo technologiją.

Norėdami sukurti filtrą, turite panaudoti egzistuojančias klases:
- `lt.itakademija.servlet.HttpFilter` - jūsų filtras turi išplėsti šią klasę.
- `lt.itakademija.servlet.RequestFormatter#formatForLog` - į žurnalą turi būti išvedamas šio metodo rezultatas.

###### Pavyzdys:

```
2016-12-19 16:54:13.600  INFO 4320 --- [bio-9092-exec-7] lt.itakademija.servlet.LoggingFilter     : Handling http request: (URI: '/spring-exam/webapi/events/3', METHOD: 'PUT', PARAMS: [], HEADERS: [accept='application/json, application/*+json'; content-type='application/json;charset=UTF-8'; user-agent='Java/1.8.0_05'; host='localhost:9092'; connection='keep-alive'; content-length='24'], BODY: {"severityLevel":"HIGH"})
```

### REST serviso aprašymas

Servisas `/webapi/events` teikia operacijas, leidžiančias registruoti su viešosios tvarkos pažeidimais 
susijusius įvykius.

---

##### Operacija: `POST /webapi/events`

Registruoja naują įvykį.

###### Užklausos duomenys:

Modelis: `lt.itakademija.model.EventRegistration`

| Laukas        | Apribojimai  |
| ------------- |:------------- |
| location      | privalomas, maksimalus ilgis `100` simbolių |
| description   | privalomas, maksimalus ilgis `1000` simbolių |
| severityLevel | privalomas |

Pavyzdys (JSON):
```
{
  "description": "string",
  "location": "string", 
  "severityLevel": "LOW"
}
```

###### Atsakymo duomenys:

HTTP Statusas: 201 Created

Modelis: `lt.itakademija.model.RegisteredEvent`

Pavyzdys (JSON):
```
{
  "description": "string",
  "id": 0,
  "location": "string",
  "registrationDate": "2016-12-18T14:26:37.162Z",
  "severityLevel": "LOW"
}
```
---

##### Operacija: `GET /webapi/events`

Grąžinamas visų registruotų įvykių sąrašas.

###### Užklausos duomenys:

Nėra

###### Atsakymo duomenys:

HTTP Statusas: 200 OK

Modelis: `lt.itakademija.model.RegisteredEvent`

Pavyzdys (JSON):
```
[
    {
      "description": "string",
      "id": 0,
      "location": "string",
      "registrationDate": "2016-12-18T14:26:37.162Z",
      "severityLevel": "LOW"
    },
    ...
]
```
---

##### Operacija: `PUT /webapi/events/{eventId}`

Modifikuoja egzistuojantį įvykį.

###### Užklausos duomenys:

Parametrai:

| Parametras    | Duomenų tipas | Aprašymas  |
| ------------- |:------------- | :-----|
| eventId       | `integer`      | Modifikuojamo įvykio ID. |

Modelis: `lt.itakademija.model.RegisteredEventUpdate`

| Laukas        | Apribojimai  |
| ------------- |:------------- |
| severityLevel | privalomas |

Pavyzdys (JSON):
```
{
  "severityLevel": "LOW" (required)
}
```

###### Atsakymo duomenys:

HTTP Statusas: 200 OK

Modelis: `lt.itakademija.model.RegisteredEvent`

JSON:
```
{
  "description": "string",
  "id": 0,
  "location": "string",
  "registrationDate": "2016-12-18T14:26:37.162Z",
  "severityLevel": "LOW"
}
```
---

##### Operacija: `DELETE /webapi/events/{eventId}`

Trina egzistuojantį įvykį.

###### Užklausos duomenys:

Parametrai:

| Parametras    | Duomenų tipas | Aprašymas  |
| ------------- |:------------- | :-----|
| eventId       | `integer`      | Modifikuojamo įvykio ID. |

###### Atsakymo duomenys:

HTTP Statusas: 200 OK

Modelis: `lt.itakademija.model.RegisteredEvent`

JSON:
```
{
  "description": "string",
  "id": 0,
  "location": "string",
  "registrationDate": "2016-12-18T14:26:37.162Z",
  "severityLevel": "LOW"
}
```
