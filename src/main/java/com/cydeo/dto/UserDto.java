package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Getter
    @Setter
    private Long id;
    @NotBlank
    @Size(min = 2, max = 50)
    @Getter
    @Setter
    private String firstname;
    @Getter
    @Setter
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastname;
    @Getter
    @Setter
    @NotBlank
    @Email
    private String username;

    //    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    //    @NotBlank   // @Pattern is enough to check if it is not blank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;

    //    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    @NotNull
    private String confirmPassword;

    //    @NotBlank // @Pattern is enough to check if it is not blank
//    @Pattern(regexp = "^1-[0-9]{3}?-[0-9]{3}?-[0-9]{4}$")                         //  format "1-xxx-xxx-xxxx"
//    imported from https://www.baeldung.com/java-regex-validate-phone-numbers :
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")                     // +111 123 45 67 89
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    @NotNull
    private RoleDto role;
    @Getter
    @Setter
    @NotNull
    private CompanyDto company;

    @Getter
    @Setter
    private Boolean isOnlyAdmin;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (password != null && !password.equals(confirmPassword)) {
            this.confirmPassword = null;
        }
    }

}