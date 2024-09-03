package org.example.smspr.repository;

import org.example.smspr.entity.TbPost;
import org.example.smspr.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserRepository extends JpaRepository<TbUser, String> {

	TbUser findByUsernameAndPassword(String username, String password);
}
