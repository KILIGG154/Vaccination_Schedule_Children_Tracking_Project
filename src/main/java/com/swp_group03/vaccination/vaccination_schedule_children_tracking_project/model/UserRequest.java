package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Size(min = 6, max = 30, message = "Tên đăng nhập phải có ít nhất 6 ký tự và nhiều nhất 30 ký tự !!")
    @NotBlank(message = "Vui lòng không để trống Username !! ")
    private String Username;

    @NotBlank(message = "Vui lòng không để trống Password !! ")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W]).{8,20}$", message = "Mật Khẩu nên có ít nhất 1 ký tự đặc biệt và 1 chữ in hoa !! ")
    @Size(min = 8, max = 20, message = "Mật khẩu phải có ít nhất 8 ký tự và nhiều nhất 20 ký tự !!")
    private String Password;

    @NotBlank(message = "Vui lòng không để trống first_Name !!")
    @Size(max = 100, message = "first_Name không được vượt quá 100 ký tự !!")
    @Nationalized
    private String first_Name;

    @NotBlank(message = "Vui lòng không để trống last_Name !!")
    @Size(max = 100, message = "last_Name không được vượt quá 100 ký tự !!")
    @Nationalized
    private String last_Name;

    @NotBlank(message = "Vui lòng không để trống Email !!")
    @Pattern(regexp = "^(?=.{6,100}$)[a-zA-Z0-9]+(?:[._%+-][a-zA-Z0-9]+)*@gmail\\.com$", message = "Please enter right email")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự !!\n")
    private String Email;

    @NotBlank(message = "Vui lòng không để trống số điện thoại !!")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
    private String Phone_number;

    @NotBlank(message = "Vui lòng không để trống Địa chỉ !!")
    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự !!")
    private String Address;

    @NotBlank(message = "Vui lòng không để trống Giới tính !!")
    @Size(max = 6, message = "Giới tính không được vượt quá 6 ký tự !!")
    private String Gender;

    private boolean Status;

    private String url_image;

}
