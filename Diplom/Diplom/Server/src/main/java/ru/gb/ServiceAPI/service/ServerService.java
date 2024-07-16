package ru.gb.ServiceAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.ServiceAPI.aspect.TrackUserAction;
import ru.gb.ServiceAPI.domain.Message;
import ru.gb.ServiceAPI.domain.Profile;
import ru.gb.ServiceAPI.repository.MessageRepository;
import ru.gb.ServiceAPI.repository.ServerRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    private final MessageRepository messageRepository;

    /**
     * Прочитать все сообщения присланные на сервер
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readToServerMessages(){

            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getSender(), "Server")).
                    collect((Collectors.toList()));

    }
    /**
     * Прочитать все сообщения отправленные с сервера
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readFromServerMessages(){

        return messageRepository.findAll().stream().
                filter(message -> Objects.equals(message.getReceiver(), "Server")).
                collect((Collectors.toList()));

    }

    /**
     * Прочитать все сообщения присланные на сервер конкретным пользователем
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readToServerMessagesBySingleProfile(Long id){

        return messageRepository.findAll().stream().
                filter(message -> Objects.equals(message.getSender(), "Server")).
                filter(message -> Objects.equals(message.getReceiver(), serverRepository.findById(id).get().getName())).
                collect((Collectors.toList()));

    }
    /**
     * Прочитать все сообщения отправленные с сервера конкретному пользователю
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readFromServerMessagesBySingleProfile(Long id){

        return messageRepository.findAll().stream().
                filter(message -> Objects.equals(message.getReceiver(), "Server")).
                filter(message -> Objects.equals(message.getSender(), serverRepository.findById(id).get().getName())).
                collect((Collectors.toList()));

    }

    /**
     * Прочитать все сообщения отправленные конкретным пользователем
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readProfilesSentMessages(Long id){

        return messageRepository.findAll().stream().
                filter(message -> Objects.equals(message.getSender(), serverRepository.findById(id).get().getName())).
                collect((Collectors.toList()));

    }
    /**
     * Прочитать все сообщения полученные конкретным пользователем
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readProfilesReceivedMessages(Long id){

        return messageRepository.findAll().stream().
                filter(message -> Objects.equals(message.getReceiver(), serverRepository.findById(id).get().getName())).
                collect((Collectors.toList()));

    }

    /**
     * Прочитать абсолютно все сообщения
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readAllMessages(){

        return messageRepository.findAll();

    }

    /**
     * Отправить сообщение
     * @param idReceiver - id получателя
     * @param messageText - текст сообщения
     * @return - отправленное сообщение
     */
    @TrackUserAction
    @Transactional
    public Message sendMessage(Long idReceiver, String messageText){

        Profile receiver = serverRepository.findById(idReceiver).get();

        Message message = new Message();
        message.setSender("Server");
        message.setReceiver(receiver.getName());
        message.setMessage(messageText);
        messageRepository.save(message);

        return message;

    }

    /**
     * Поиск сообщений между двумя собеседниками
     * @param receiver - имя получателя
     * @param sender - имя отправителя
     * @return - Список сообщений
     */
    @TrackUserAction
    public List<Message> filterMessagesByName(String receiver, String sender){
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getReceiver(), receiver)).
                    filter(message -> Objects.equals(message.getSender(), sender)).
                    collect(Collectors.toList());
    }

    /**
     * Создание нового профиля
     * @param profile - данные, которые должны быть у созданного профиля
     * @return - созданный аккаунт
     */
    @TrackUserAction
    public Profile createProfile(Profile profile){
        Profile newProfile = new Profile();
        newProfile.setName(profile.getName());
        newProfile.setBirthdate(profile.getBirthdate());
        newProfile.setPhoneNumber(profile.getPhoneNumber());
        newProfile.setLogin(profile.getLogin());
        newProfile.setPassword(profile.getPassword());
        serverRepository.save(newProfile);
        return newProfile;
    }


    /**
     * Удаление профиля
     * @param id - id профиля, который надо удалить
     */
    @TrackUserAction
    public void deleteById(Long id){
        serverRepository.deleteById(id);
    }

    /**
     * Возвращает список всех профилей
     * @return список всех профилей
     */
    @TrackUserAction
    public List<Profile> getAllProfiles(){
        return serverRepository.findAll();
    }

    /**
     * Возвращает список всех сообщений
     * @return список всех сообщений
     */
    @TrackUserAction
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }


    /**
     * Поиск профилей по дате рождения
     * @param birthdate - дата рождения искомого профиля
     * @return найденный-ые профили
     */
    @TrackUserAction
    public List<Profile> filterUsersByAge(String birthdate) {
        List<Profile> profiles = serverRepository.findAll();
        LocalDate date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int age = 2024 - date.getYear();
        return profiles.stream().filter(profile -> profile.getAge() == age).collect(Collectors.toList());
    }


    /**
     * Поиск по номеру телефона
     * @param phoneNumber - номер телефона, который должен иметь искомый аккаунт
     * @return - аккаунт с номером телефона, идентичным заданному
     */
    @TrackUserAction
    public List<Profile> getByPhoneNumber(String phoneNumber){
        return serverRepository.findAll().stream().filter(profile -> Objects.equals(profile.getPhoneNumber(), phoneNumber)).collect(Collectors.toList());
    }

    /**
     *  Поиск по имени
     * @param name - Имя, которое должен иметь искомый профиль
     * @return - профиль с именем, идентичным заданному
     */
    @TrackUserAction
    public List<Profile> getByName(String name){
        return serverRepository.findAll().stream().filter(profile -> Objects.equals(profile.getName(), name)).collect(Collectors.toList());
    }

}
