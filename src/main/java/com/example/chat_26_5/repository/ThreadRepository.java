package com.example.chat_26_5.repository;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ThreadModel, Integer> {
    Optional<ThreadModel> findByIdAndUser_Id(Integer thread_id, Integer userId);

    List<ThreadModel> findAllByUser_Id(Integer userId);

    List<ThreadModel> findByUserId(Integer userId);

    Optional<ThreadModel> findByIdAndUserId(Integer id, Integer userId);

    @Query("SELECT COALESCE(MAX(t.thread_id), 0) FROM ThreadModel t WHERE t.user.id = :userId")
    Integer findMaxThIdByUserId(@Param("userId") Integer userId);
}
