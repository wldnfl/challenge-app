package com.twelve.challengeapp.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.twelve.challengeapp.dto.oauth2.CustomOAuth2User;
import com.twelve.challengeapp.dto.oauth2.GoogleUserResponseDto;
import com.twelve.challengeapp.dto.oauth2.NaverUserResponseDto;
import com.twelve.challengeapp.dto.oauth2.OAuth2ResponseDto;
import com.twelve.challengeapp.dto.oauth2.OAuth2UserInfo;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.repository.UserRepository;

@Service
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	public CustomOAuth2UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// 소셜 로그인 저장
		OAuth2ResponseDto oAuth2ResponseDto;

		// 네이버 유저 확인
		if (registrationId.equals("naver")) {
			oAuth2ResponseDto = new NaverUserResponseDto(oAuth2User.getAttributes());
		}
		// 구글 유저 확인
		else if (registrationId.equals("google")) {
			oAuth2ResponseDto = new GoogleUserResponseDto(oAuth2User.getAttributes());
		} else {
			oAuth2ResponseDto = null;
			return null;
		}

		// 가입된 유저인지 확인
		String username = oAuth2ResponseDto.getProvider() + " " + oAuth2ResponseDto.getProviderId();

		Optional<User> optionalUser = userRepository.findByUsername(username);

		if (optionalUser.isPresent()) {
			// 기존 유저가 존재하는 경우
			User existData = optionalUser.get();
			User updatedUser = User.builder()
				.id(existData.getId()) // id를 포함한 기존 데이터 유지
				.username(existData.getUsername())
				.password(existData.getPassword())
				.nickname(existData.getNickname())
				.introduce(existData.getIntroduce())
				.email(oAuth2ResponseDto.getEmail())
				.role(UserRole.USER)
				.build();

			userRepository.save(updatedUser);

			OAuth2UserInfo userInfo = OAuth2UserInfo.builder()
				.username(updatedUser.getUsername())
				.name(updatedUser.getNickname())
				.email(oAuth2ResponseDto.getEmail())
				.role("ROLE_USER")
				.build();

			return new CustomOAuth2User(userInfo);
		} else {
			// 새로운 유저인 경우
			User newUser = User.builder()
				.username(username)
				.password("#@#$312314") // 임시비밀번호
				.nickname(oAuth2ResponseDto.getName())
				.introduce("처음 가입한 회원입니다, ")
				.email(oAuth2ResponseDto.getEmail())
				.role(UserRole.USER)
				.build();

			userRepository.save(newUser);

			OAuth2UserInfo userInfo = OAuth2UserInfo.builder()
				.username(newUser.getUsername())
				.name(newUser.getNickname())
				.email(oAuth2ResponseDto.getEmail())
				.role("ROLE_USER")
				.build();

			return new CustomOAuth2User(userInfo);
		}
	}
}
