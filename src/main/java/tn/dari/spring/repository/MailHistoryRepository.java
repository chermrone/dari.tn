package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dari.spring.entity.MailHistory;

public interface MailHistoryRepository extends  JpaRepository<MailHistory, Long> {

}
