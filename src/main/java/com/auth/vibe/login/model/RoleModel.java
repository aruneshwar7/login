package com.auth.vibe.login.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Column(length = 20, name = "role_name")
    private String roleName;

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }


    @ManyToMany(mappedBy = "roles")
    private List<UserModel> user = new ArrayList<>();

    @Override
    public String toString() {
        return "RoleModel{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
//                ", user=" + user +
                '}';
    }
}
