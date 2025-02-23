package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserUpdate {

    @Size(min = 6, max = 30, message = "Tên đăng nhập phải có ít nhất 6 ký tự và nhiều nhất 30 ký tự !!")

    private String Username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W]).{8,20}$", message = "Mật Khẩu nên có ít nhất 1 ký tự đặc biệt và 1 chữ in hoa !! ")
    @Size(min = 8, max = 20, message = "Mật khẩu phải có ít nhất 8 ký tự và nhiều nhất 20 ký tự !!")
    private String Password;


    @Size(max = 100, message = "first_Name không được vượt quá 100 ký tự !!")
    private String first_Name;


    @Size(max = 100, message = "last_Name không được vượt quá 100 ký tự !!")
    private String last_Name;


    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Please enter right email")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự !!\n")
    private String Email;


    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
    private String Phone_number;


    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự !!")
    private String Address;


    @Size(max = 6, message = "Giới tính không được vượt quá 6 ký tự !!")
    private String Gender;

    private boolean Status;

    private String url_image;

}

