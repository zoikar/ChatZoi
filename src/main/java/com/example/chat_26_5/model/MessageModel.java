package com.example.chat_26_5.model;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Entity
@Table(name = "message_table")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String sender;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    @JsonBackReference
    private ThreadModel thread;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user;

    public MessageModel(Integer id, String content, String sender, LocalDateTime date, ThreadModel thread, UserModel user) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.date = date;
        this.thread = thread;
        this.user = user;
    }

    public MessageModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ThreadModel getThread() {
        return thread;
    }

    public void setThread(ThreadModel thread) {
        this.thread = thread;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MessageModel that = (MessageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(sender, that.sender) && Objects.equals(date, that.date) && Objects.equals(thread, that.thread) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, sender, date, thread, user);
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", date=" + date +
                ", thread=" + thread +
                ", user=" + user +
                '}';
    }
}
