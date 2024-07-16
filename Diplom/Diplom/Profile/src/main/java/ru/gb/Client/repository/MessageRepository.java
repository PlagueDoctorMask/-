package ru.gb.Client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.Client.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
