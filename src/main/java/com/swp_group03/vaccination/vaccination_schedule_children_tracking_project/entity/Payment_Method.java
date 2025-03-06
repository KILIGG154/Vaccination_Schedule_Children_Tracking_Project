package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Payment_Method")
public class Payment_Method {

    @Id
    @Column(name = "MethodId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Method_Name")
    private String methodName;

    public Payment_Method() {
    }

    public Payment_Method(String methodName) {
        this.methodName = methodName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
