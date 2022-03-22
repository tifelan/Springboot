package com.phoenix.service.user;

import com.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.repository.AppUserRepository;
import com.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class AppUserServiceImpl implements  AppUserService{

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private AppUserResponseDto buildResponse(AppUser appUser){
        return AppUserResponseDto.builder()
                .firstname(appUser.getFirstname())
                .lastname(appUser.getLastname())
                .email(appUser.getEmail())
                .address(appUser.getAddress()).build();
    }

    @Override
    public AppUserResponseDto createuser(AppUserRequestDto userRequestDto) throws BusinessLogicException {


//        check that user doesnt exist

            Optional<AppUser> savedUser = appUserRepository.findByEmail(userRequestDto.getEmail());
            if(savedUser.isPresent()){
                throw new BusinessLogicException("Oga, we get this person for hia");
            }

//        create an app user object
            AppUser newUser = new AppUser();
            newUser.setEmail(userRequestDto.getEmail());
            newUser.setFirstname(userRequestDto.getFirstname());
            newUser.setLastname(userRequestDto.getLastname());
            newUser.setAddress(userRequestDto.getAddress());
            newUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

//        save object
            appUserRepository.save(newUser);
//        return object
            return buildResponse(newUser);
        }
}
