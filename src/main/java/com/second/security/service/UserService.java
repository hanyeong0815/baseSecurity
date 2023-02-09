package com.second.security.service;

import com.second.security.domain.Users;
import com.second.security.domain.UsersRepo;
import com.second.security.dto.response.UsersResponse.UsersRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users users = usersRepo.findByUsername(username).orElseThrow();
            return users;
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("해당하는 유저를 찾지 못하였습니다.");
        }
    }

    public boolean signUp(Users users) {
        Users hasUser = usersRepo.findUserNameByUsername(users.getUsername());
        if (null != hasUser) {
            return false;
        }
        Users newUser = usersRepo.save(users);
        return newUser != null;
    }

    public UsersRes usersInfo(String username) {
        Users users = usersRepo.findByUsername(username).get();
        UsersRes res = UsersRes.toResFrom(users);
        return res;
    }

    public boolean deleteUser(Long userId) {
        usersRepo.deleteById(userId);
        return true;
    }

}
