package ru.gb.ServiceAPI.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * id - id сообщения
 * sender - имя отправителя
 * receiver - имя получателя
 * message - текст сообщения
 */

@Entity
@Data
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String message;


}
