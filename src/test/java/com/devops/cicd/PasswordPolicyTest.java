package com.devops.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordPolicyTest {

    @Test
    void isStrong_shouldReturnFalseForNull() {
        assertFalse(PasswordPolicy.isStrong(null));
    }

    @Test
    void isStrong_shouldReturnFalseForTooShort() {
        assertFalse(PasswordPolicy.isStrong("Ab1!"));
    }

    @Test
    void isStrong_shouldReturnFalseWithoutUpperCase() {
        assertFalse(PasswordPolicy.isStrong("abcdefgh1!"));
    }

    @Test
    void isStrong_shouldReturnFalseWithoutLowerCase() {
        assertFalse(PasswordPolicy.isStrong("ABCDEFGH1!"));
    }

    @Test
    void isStrong_shouldReturnFalseWithoutDigit() {
        assertFalse(PasswordPolicy.isStrong("Abcdefgh!"));
    }

    @Test
    void isStrong_shouldReturnFalseWithoutSpecialChar() {
        assertFalse(PasswordPolicy.isStrong("Abcdefgh1"));
    }

    @Test
    void isStrong_shouldReturnTrueForValidPassword() {
        assertTrue(PasswordPolicy.isStrong("Abcdefgh1!"));
    }

    @Test
    void isStrong_shouldReturnTrueForComplexPassword() {
        assertTrue(PasswordPolicy.isStrong("MyP@ssw0rd123"));
    }

    @Test
    void isStrong_shouldReturnTrueWithMultipleSpecialChars() {
        assertTrue(PasswordPolicy.isStrong("Str0ng!P@ss#"));
    }
}