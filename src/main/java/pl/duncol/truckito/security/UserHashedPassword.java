package pl.duncol.truckito.security;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class UserHashedPassword extends BaseEntity<Long> {
	
	@Column(name = "user_login")
	private String userLogin;
	@Column(name = "hashed_password")
	private String hashedPass;
	private String salt;
}
