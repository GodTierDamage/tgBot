package gdt.projects.tgbot.repository;

import gdt.projects.tgbot.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
