package br.com.consolidaiag.user.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;

public class UserAlreadyExistsException extends ConflictException {

  public UserAlreadyExistsException(String email) {
    super("Já existe um usuário cadastrado com o e-mail: " + email);
  }
}
