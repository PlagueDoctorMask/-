package ru.gb.ServiceAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.ServiceAPI.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
