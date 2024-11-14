package com.example.DoAnTotNghiep_MiniatureCrafts.security.services;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Account;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  AccountRepository accountRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = accountRepository.findByUsername(username);
    return UserDetailsImpl.build(user);
  }

}
