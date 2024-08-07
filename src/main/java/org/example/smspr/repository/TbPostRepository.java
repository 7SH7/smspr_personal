package org.example.smspr.repository;

import org.example.smspr.entity.TbPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbPostRepository extends JpaRepository<TbPost, String> {

}