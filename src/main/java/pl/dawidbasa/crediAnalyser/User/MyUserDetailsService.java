package pl.dawidbasa.crediAnalyser.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import pl.dawidbasa.crediAnalyser.Login.Role;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		pl.dawidbasa.crediAnalyser.User.User user = userRepository.findByEmail(email);
		
				List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

		
		return buildUserForAuthentication(user, authorities);

	}
	
	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private UserDetails buildUserForAuthentication(pl.dawidbasa.crediAnalyser.User.User user,
			List<GrantedAuthority> authorities) {
		
		return new User(user.getEmail(), user.getPassword(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Role> roles) {
		
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		
		for (Role userRole : roles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		
		return Result;
	}

	
}
