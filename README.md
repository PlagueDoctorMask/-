Дипломный проект «Базовый функционал социальной сети»

Порты:

-8765 - Gateway

-8081 - Профиль

-8082 - Сервер

Контроллеры

Сервер:

@RequestMapping("/server")

@GetMapping("/allProfiles") – просмотреть все профили

@GetMapping("/allMessages") – просмотреть все сообщения

@GetMapping("/readAllToServer") – прочитать все сообщения, присланные на сервер

@GetMapping("/readAllFromServer") – прочитать все сообщения, присланные с сервера

@GetMapping("/readAllToServerBy") – прочитать все сообщения, присланные на сервер конкретным профилем

@GetMapping("/readAllFromServerBy") – прочитать все сообщения, присланные сервером конкретному профилю

@GetMapping("/readFromProfile/{id}") – прочитать сообщения, отправленные конкретным профилем

@GetMapping("/readToProfile/{id}") – прочитать сообщения, полученные конкретным профилем

@PostMapping("/new") – Создание нового аккаунта с параметрами, заданными в теле запроса

@DeleteMapping("/delete/{id}") – Удаление аккаунта по id переданного в запросе

@GetMapping("/filter/phone/{phoneNumber}") – поиск аккаунтов с номером телефона идентичным переданному в запросе

@GetMapping("/filter/age/{birthdate}") – поиск аккаунтов, “где дата рождения больше чем переданный в запросе”

@GetMapping("/filter/name") - поиск аккаунтов с ФИО идентичному переданному в параметре

Профиль:

@RequestMapping("/profile")

@GetMapping("/readAllIn/{id}/{password}") – прочитать все входящие сообщения

@GetMapping("/readAllOut/{id}/{password}") – прочитать все исходящие сообщения

@GetMapping("/readAllOutTo/{id}/{password}/{idReceiver}") – прочитать все исходящие сообщения конкретному профилю

@GetMapping("/readAllInTo/{id}/{password}/{idSender}") – прочитать все входящие сообщения от конкретного профиля

@GetMapping("/readAllFromServer/{id}/{password}") – прочитать все сообщения от сервера

@GetMapping("/readAllToServer/{id}/{password}") – прочитать все сообщения, отправленные серверу

@PutMapping("/edit/phone/{id}/{newPhoneNumber}/{password}") – изменение номера телефона с данными заданными в запросе

@GetMapping("/filter/phone/{phoneNumber}") – поиск аккаунтов с номером телефона идентичным переданному в запросе

@GetMapping("/filter/age/{birthdate}") – поиск аккаунтов, “где дата рождения больше чем переданный в запросе”

@GetMapping("/filter/name") - поиск аккаунтов с ФИО идентичному переданному в параметре

Конфиг безопасности: прописана конфигурация, согласно которой профиль и сервер авторизуются перед использованием

Аспект: работает у методов с аннотацией “TrackUserAction” и выводит некоторое логирование в консоль

