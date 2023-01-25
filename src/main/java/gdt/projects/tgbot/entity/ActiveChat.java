package gdt.projects.tgbot.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ACTIVE_CHAT")
public class ActiveChat {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long chatId;
}
