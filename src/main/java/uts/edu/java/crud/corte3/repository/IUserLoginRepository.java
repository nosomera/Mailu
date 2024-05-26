package uts.edu.java.crud.corte3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uts.edu.java.crud.corte3.model.UserLogin;

public interface IUserLoginRepository extends JpaRepository<UserLogin, Integer>{

}
