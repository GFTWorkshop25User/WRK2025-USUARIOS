package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    public void should_throwIllegalArgumentException_when_emailIsNotValid() {
        String email = "whiwhatanose.nose.com";
        var exception = assertThrows(IllegalArgumentException.class, () -> new Email(email));
        assertEquals("Invalid value", exception.getMessage());
    }

    @Test
    public void should_createEmail_when_emailIsValid() {
        String emailString = "joanbi@nose.com";
        Email email = new Email(emailString);
        assertEquals(emailString, email.value());
    }


}
