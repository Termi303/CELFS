package uk.ac.bris.celfs.website;

import lombok.Data;

@Data
public class LoginCommand {
  String email;
  String password;
}
