package ru.gb.MicroService1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.Client.domain.Message;
import ru.gb.Client.domain.Profile;
import ru.gb.Client.repository.MessageRepository;
import ru.gb.Client.repository.ProfileRepository;
import ru.gb.Client.service.ProfileService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceModelTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ProfileService profileService;

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
        message1.setSender("John");
        message1.setReceiver("Jane");
        message1.setMessage("Hello, Jane!");

        message2 = new Message();
        message2.setId(2L);
        message2.setSender("Jane");
        message2.setReceiver("John");
        message2.setMessage("Hello, John!");
    }


    @Test
    void readAllMessages_WrongPassword_ThrowsRuntimeException() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThrows(RuntimeException.class, () -> profileService.readAllReceivedMessages(1L, "wrongPassword"));

        verify(profileRepository).findById(eq(1L));
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }


    @Test
    void sendMessageToServer_WrongPassword_ThrowsRuntimeException() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThrows(RuntimeException.class, () -> profileService.sendMessageToServer(1L, "Hello, Server!", "wrongPassword"));

        verify(profileRepository).findById(eq(1L));
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }



    @Test
    void sendMessageToProfile_WrongPassword_ThrowsRuntimeException() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThrows(RuntimeException.class, () -> profileService.sendMessageToProfile(1L, 2L, "Hello, Jane!", "wrongPassword"));

        verify(profileRepository).findById(eq(1L));
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }



    @Test
    void filterMessagesByName_WrongPassword_ThrowsRuntimeException() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThrows(RuntimeException.class, () -> profileService.filterMessagesByName(1L, "wrongPassword", "John"));

        verify(profileRepository).findById(eq(1L));
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }

    @Test
    void filterMessagesFromServer_CorrectPassword_ReturnsFilteredMessages() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));
        when(messageRepository.findAll()).thenReturn(Stream.of(message1, message2).collect(Collectors.toList()));

        List<Message> actualMessages = profileService.readAllMessagesFromServer(1L, "password1");

        assertEquals(Collections.emptyList(), actualMessages);
        verify(profileRepository).findById(eq(1L));
        verify(messageRepository).findAll();
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }

    @Test
    void filterMessagesFromServer_WrongPassword_ThrowsRuntimeException() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThrows(RuntimeException.class, () -> profileService.filterMessagesFromServer(1L, "wrongPassword"));

        verify(profileRepository).findById(eq(1L));
        verifyNoMoreInteractions(profileRepository, messageRepository);
    }
}
