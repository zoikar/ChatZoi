package com.example.chat_26_5.model;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Entity
@Table(name = "thread_table")
public class ThreadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String th_created;

    private Integer thread_id;

    private String th_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MessageModel> messages;

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTh_created() {
        return th_created;
    }

    public void setTh_created(String th_created) {
        this.th_created = th_created;
    }

    public Integer getThread_id() {
        return thread_id;
    }

    public void setThread_id(Integer thread_id) {
        this.thread_id = thread_id;
    }

    public String getTh_name() {
        return th_name;
    }

    public void setTh_name(String th_name) {
        this.th_name = th_name;
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
        ThreadModel that = (ThreadModel) o;
        return Objects.equals(id, that.id) && Objects.equals(th_created, that.th_created) && Objects.equals(thread_id, that.thread_id) && Objects.equals(th_name, that.th_name) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, th_created, thread_id, th_name, user);
    }

    @Override
    public String toString() {
        return "ThreadModel{" +
                "id=" + id +
                ", th_created='" + th_created + '\'' +
                ", thread_id=" + thread_id +
                ", th_name='" + th_name + '\'' +
                ", user=" + user +
                '}';
    }
}