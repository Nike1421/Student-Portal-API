package net.projects.student.portal.security_config.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.projects.student.portal.models.User;
import net.projects.student.portal.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String sapId) throws UsernameNotFoundException {
		User user = userRepository.findBySapID(sapId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with SAP ID " + sapId));

		return UserDetailsImpl.build(user);
	}

}
