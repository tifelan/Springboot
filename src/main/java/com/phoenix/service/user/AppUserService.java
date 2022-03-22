package com.phoenix.service.user;

import com.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.web.exceptions.BusinessLogicException;

public interface AppUserService {

    AppUserResponseDto createuser(AppUserRequestDto userRequestDto) throws BusinessLogicException;

}
