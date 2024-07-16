package ru.gb.MicroService2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.ServiceAPI.ServerApplication;
import ru.gb.ServiceAPI.domain.Message;
import ru.gb.ServiceAPI.domain.Profile;
import ru.gb.ServiceAPI.repository.MessageRepository;
import ru.gb.ServiceAPI.repository.ServerRepository;
import ru.gb.ServiceAPI.service.ServerService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServerApplication.class)
public class ServerServiceIntegrationTest {
    @MockBean
    private ServerRepository serverRepository;

    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private ServerService serverService;

    private Profile profile1;
    private Profile profile2;
    private Message message1;
    private Message message2;

    @BeforeEach
    void setUp() {
        profile1 = new Profile();
        profile1.setId(1L);
        profile1.setName("John");
        profile1.setPassword("password1");
        profile1.setBirthdate("01.01.2000");
        profile1.setPhoneNumber("0123456789");

        profile2 = new Profile();
        profile2.setId(2L);
        profile2.setName("Jane");
        profile2.setPassword("password2");
        profile2.setBirthdate("02.01.2000");
        profile2.setPhoneNumber("1234567890");

        message1 = new Message();
        message1.setId(1L);
        message1.setSender("Server");
        message1.setReceiver("John");
        message1.setMessage("Hello, John!");

        message2 = new Message();
        message2.setId(2L);
        message2.setSender("Jane");
        message2.setReceiver("Server");
        message2.setMessage("Hello, Server!");
    }

    @Test
    void readAllMessages_ReturnsFilteredMessages() {
        when(messageRepository.findAll()).thenReturn(Stream.of(message1, message2).collect(Collectors.toList()));

        List<Message> actualMessages = serverService.readToServerMessages();

        assertEquals(Collections.singletonList(message1), actualMessages);
        verify(messageRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }


    @Test
    void filterMessagesByName_ReturnsFilteredMessages() {
        when(messageRepository.findAll()).thenReturn(Stream.of(message1, message2).collect(Collectors.toList()));

        List<Message> actualMessages = serverService.filterMessagesByName("John", "Server");

        assertEquals(Collections.singletonList(message1), actualMessages);
        verify(messageRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }

    @Test
    void createProfile_SavesAndReturnsProfile() {
        Profile newProfile = new Profile();
        newProfile.setName("New Profile");
        newProfile.setPassword("new_password");
        newProfile.setBirthdate("01.01.2000");
        newProfile.setPhoneNumber("1122334455");

        when(serverRepository.save(any(Profile.class))).thenReturn(newProfile);

        Profile actualProfile = serverService.createProfile(newProfile);

        assertEquals(newProfile, actualProfile);
        verify(serverRepository).save(eq(newProfile));
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }

    @Test
    void deleteById_DeletesProfile() {
        serverService.deleteById(1L);

        verify(serverRepository).deleteById(eq(1L));
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }

    @Test
    void getAllProfiles_ReturnsAllProfiles() {
        when(serverRepository.findAll()).thenReturn(Arrays.asList(profile1, profile2));

        List<Profile> actualProfiles = serverService.getAllProfiles();

        assertEquals(Arrays.asList(profile1, profile2), actualProfiles);
        verify(serverRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }

    @Test
    void getAllMessages_ReturnsAllMessages() {
        when(messageRepository.findAll()).thenReturn(Stream.of(message1, message2).collect(Collectors.toList()));

        List<Message> actualMessages = serverService.getAllMessages();

        assertEquals(Arrays.asList(message1, message2), actualMessages);
        verify(messageRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }


    @Test
    void getByPhoneNumber_ReturnsFilteredProfiles() {
        List<Profile> profiles = Arrays.asList(profile1, profile2);
        when(serverRepository.findAll()).thenReturn(profiles);

        List<Profile> actualProfiles = serverService.getByPhoneNumber("0123456789");

        assertEquals(Collections.singletonList(profile1), actualProfiles);
        verify(serverRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }

    @Test
    void getByName_ReturnsFilteredProfiles() {
        List<Profile> profiles = Arrays.asList(profile1, profile2);
        when(serverRepository.findAll()).thenReturn(profiles);

        List<Profile> actualProfiles = serverService.getByName("John");

        assertEquals(Collections.singletonList(profile1), actualProfiles);
        verify(serverRepository).findAll();
        verifyNoMoreInteractions(messageRepository, serverRepository);
    }
}
