package com.quarantinefriends.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;


    //Should we mark this as unique?
    @Column(name="username")
    private String username;

    @Column(name="age")
    private Integer age;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="photo")
    private String photo;

    @ManyToOne
    private Role role;

    @OneToMany
    private List<Hobby> hobbies;

    @OneToMany
    private List<Preference> preferences;

    @JoinTable(name = "friendshipsss", joinColumns = {
            @JoinColumn(name = "friend_one", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "friend_two", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<User>friendOne;

    @ManyToMany(mappedBy = "friendOne")
    private List<User> friendTwo;

    @Column(name="account_terminated")
    private boolean accountTerminated;

}
