package phb.ebookstore.dev.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import phb.ebookstore.dev.entity.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	  @JsonProperty("access_token")
	  private String accessToken;
	  @JsonProperty("refresh_token")
	  private String refreshToken;
	  @JsonProperty("role")
	  private Role role;
}
