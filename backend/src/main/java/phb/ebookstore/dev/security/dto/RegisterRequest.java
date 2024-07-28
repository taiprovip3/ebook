package phb.ebookstore.dev.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import phb.ebookstore.dev.entity.Role;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String address;
  private String phoneNumber;
  private String email;
  private String password;
  private Role role;
}