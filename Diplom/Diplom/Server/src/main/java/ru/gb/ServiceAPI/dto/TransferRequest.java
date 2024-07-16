package ru.gb.ServiceAPI.dto;

import lombok.Data;

/**
 * senderAccountID - id отправителя
 * receiverAccountID - id получателя
 * message - сообщение
 */

@Data
public class TransferRequest {

    private Long senderAccountID;

    private Long receiverAccountID;

    private String message;
}
