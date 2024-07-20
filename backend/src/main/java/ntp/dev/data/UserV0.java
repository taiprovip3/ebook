package ntp.dev.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntp.dev.security.dto.AuthenticationResponse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserV0 {
	  @JsonProperty("email")
	  private String email;
	  @JsonProperty("role")
	  private String role;
}
