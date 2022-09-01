package com.tests.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tests.integration.domain.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

  List<MessageEntity> findByMessage(String message);

}
