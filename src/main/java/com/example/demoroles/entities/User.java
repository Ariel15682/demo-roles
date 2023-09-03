package com.example.demoroles.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "app_users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email") })
public class User {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    @JsonIgnore //no muestra el password en la respuesta
    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String businessTitle;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //cache
    @JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") },
               inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    //Constructores
    public User() { }

//    public User(Long id, String username, String email, String password, String name, String phone,
//                String businessTitle, Set<Role> roles) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.phone = phone;
//        this.businessTitle = businessTitle;
//        this.roles = roles;
//    }

    //Getter and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getBusinessTitle() { return businessTitle; }

    public void setBusinessTitle(String businessTitle) { this.businessTitle = businessTitle; }

    public Set<Role> getRoles() { return roles; }

    public void setRoles(Set<Role> roles) { this.roles = roles; }

    //toString()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", businessTitle='" + businessTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) &&
               Objects.equals(email, user.email) && Objects.equals(password, user.password) &&
               Objects.equals(name, user.name) && Objects.equals(phone, user.phone) &&
               Objects.equals(businessTitle, user.businessTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, name, phone, businessTitle);
    }
}
