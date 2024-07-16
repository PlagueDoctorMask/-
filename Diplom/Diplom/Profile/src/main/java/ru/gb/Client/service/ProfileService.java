package ru.gb.Client.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.Client.domain.Message;
import ru.gb.Client.domain.Profile;
import ru.gb.Client.repository.MessageRepository;
import ru.gb.Client.repository.ProfileRepository;
import ru.gb.Client.aspect.TrackUserAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MessageRepository messageRepository;



    /**
     * Прочитать все входящие сообщения
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @return - список сообщений присланных на этот профиль
     */
    @TrackUserAction
    public List<Message> readAllReceivedMessages(Long id, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getReceiver(), profileRepository.findById(id).get().getName())).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Прочитать все исходящие сообщения
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @return - список сообщений отправленных с этого профиль
     */
    @TrackUserAction
    public List<Message> readAllSentMessages(Long id, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getSender(), profileRepository.findById(id).get().getName())).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Прочитать все исходящие сообщения конкретному профилю
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @param idReceiver - id профиля, которому отправлялись сообщения
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readAllSentMessagesToProfile(Long id, String password, Long idReceiver){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getSender(), profileRepository.findById(id).get().getName())).
                    filter(message -> Objects.equals(message.getReceiver(), profileRepository.findById(idReceiver).get().getName())).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Прочитать все входящие сообщения от конкретного профиля
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @param idSender - id профиля, которым отправлялись сообщения
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readAllSentMessagesFromProfile(Long id, String password, Long idSender){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getSender(), profileRepository.findById(idSender).get().getName())).
                    filter(message -> Objects.equals(message.getReceiver(), profileRepository.findById(id).get().getName())).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Прочитать все входящие сообщения от сервера
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readAllMessagesFromServer(Long id, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getSender(), "Server")).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Прочитать все исходящие сообщения серверу
     * @param id - id профиля(своего)
     * @param password - пароль от профиля
     * @return - список сообщений
     */
    @TrackUserAction
    public List<Message> readAllMessagesToServer(Long id, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){// Проверка на то, что сообщения читаются именно владельцем
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getReceiver(), "Server")).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Отправка сообщения серверу
     * @param idSender - id профиля, с которого отправляется сообщение
     * @param messageText - текст сообщения
     * @param password - пароль профиля, с которого отправляется сообщение
     * @return - отправленное сообщение
     */
    @TrackUserAction
    @Transactional
    public Message sendMessageToServer(Long idSender, String messageText, String password){
        if (Objects.equals(profileRepository.findById(idSender).get().getPassword(), password)) {
            Profile sender = profileRepository.findById(idSender).orElse(null);

            Message message = new Message();
            message.setSender(sender.getName());
            message.setReceiver("Server");
            message.setMessage(messageText);
            messageRepository.save(message);

            return message;
        }else{
            throw  new RuntimeException("Wrong password");
        }


    }

    /**
     * Отправка сообщения другому профилю
     * @param idSender - id профиля, с которого отправляется сообщение
     * @param idReceiver - id профиля, которому надо отправить сообщение
     * @param messageText - текст сообщения
     * @param password - пароль от профиля, с которого отправляется сообщение
     * @return - сообщение
     */
    @TrackUserAction
    @Transactional
    public Message sendMessageToProfile(Long idSender, Long idReceiver, String messageText, String password){
        if (Objects.equals(profileRepository.findById(idSender).get().getPassword(), password)) {
            Profile sender = profileRepository.findById(idSender).orElse(null);
            Profile receiver = profileRepository.findById(idReceiver).orElse(null);

            Message message = new Message();
            message.setSender(sender.getName());
            message.setReceiver(receiver.getName());
            message.setMessage(messageText);
            messageRepository.save(message);

            return message;
        }else{
            throw  new RuntimeException("Wrong password");
        }


    }

    /**
     * Фильтрация сообщений по имени
     * @param id - id профиля, которому приходили сообщения
     * @param password - пароль этого же профиля
     * @param senderName - им отправителя, сообщения которого надо вывести
     * @return - список сообщений отправленных, от конкретного пользователя
     */
    @TrackUserAction
    public List<Message> filterMessagesByName(Long id, String password, String senderName){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getReceiver(), profileRepository.findById(id).get().getName())).
                    filter(message -> Objects.equals(message.getSender(), senderName)).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }

    /**
     * Фильтрация сообщений отправленных серверу
     * @param id - id профиля, которому приходили сообщения
     * @param password - пароль этого же профиля
     * @return - список сообщений отправленных, от сервера
     */
    @TrackUserAction
    public List<Message> filterMessagesFromServer(Long id, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){
            return messageRepository.findAll().stream().
                    filter(message -> Objects.equals(message.getReceiver(), profileRepository.findById(id).get().getName())).
                    filter(message -> Objects.equals(message.getSender(), "Server")).
                    collect(Collectors.toList());
        }else{
            throw  new RuntimeException("Wrong password");
        }
    }


    /**
     * Фильтрация аккаунтов по дате рождения
     * @param birthdate - дата рождения, по которой проиходит фильтрация согласно условию: "дата рождения больше чем переданный в запросе"
     * @return аккаунты прошедшие фильтрацию
     */
    @TrackUserAction
    public List<Profile> filterUsersByAge(String birthdate) {
        List<Profile> profiles = profileRepository.findAll();
        LocalDate date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int year = date.getYear();
        return profiles.stream().filter(profile -> profile.getAge() == year).collect(Collectors.toList());
    }


    /**
     * поиск по номеру телефона
     * @param phoneNumber - номер телефона, который должен иметь искомый аккаунт
     * @return - аккаунт с номером телефона, идентичным заданному
     */
    @TrackUserAction
    public List<Profile> getByPhoneNumber(String phoneNumber){
        return profileRepository.findAll().stream().filter(profile -> Objects.equals(profile.getPhoneNumber(), phoneNumber)).collect(Collectors.toList());
    }

    /**
     *  поиск по ФИО
     * @param name - ФИО, который должен иметь искомый аккаунт
     * @return - аккаунт с ФИО, идентичным заданному
     */
    @TrackUserAction
    public List<Profile> getByName(String name){
        return profileRepository.findAll().stream().filter(profile -> Objects.equals(profile.getName(), name)).collect(Collectors.toList());

    }

    /**
     * Изменение номера телефона по id и паролю с проверкой на уникальность
     * @param id - id аккаунта, на котором требуется изменить номер телефона
     * @param newPhoneNumber - дополнительный номер телефона
     * @param password - пароль от аккаунта, на котором требуется сменить номер телефона
     */
    @TrackUserAction
    public void editPhoneNumber(Long id, String newPhoneNumber, String password){
        if(Objects.equals(profileRepository.findById(id).get().getPassword(), password)){//проверка на совпадение паролей
            List<Profile> phones = profileRepository.findAll().stream().
                    filter(profile -> Objects.equals(profile.getPhoneNumber(), newPhoneNumber)).
                    toList();//проверка на уникальность
            if(phones.isEmpty()){
                profileRepository.findById(id).get().setPhoneNumber(newPhoneNumber);
                profileRepository.save(profileRepository.findById(id).get());
            }else{
                throw new RuntimeException("Phone number must be unique");
            }

        }else{
            throw new RuntimeException("Wrong password");
        }
    }


}
