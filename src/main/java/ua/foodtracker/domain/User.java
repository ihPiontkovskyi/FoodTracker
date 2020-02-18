package ua.foodtracker.domain;

import java.time.LocalDate;

/**
 * Domain class of User
 */
public class User {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer weight;
    private Integer height;
    private Lifestyle lifestyle;
    private LocalDate birthday;
    private Gender gender;
    private UserGoal userGoal;
    private Role role;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public UserGoal getUserGoal() {
        return userGoal;
    }

    public Role getRole() {
        return role;
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.weight = builder.weight;
        this.height = builder.height;
        this.lifestyle = builder.lifestyleEntity;
        this.birthday = builder.birthday;
        this.gender = builder.genderEntity;
        this.role = builder.roleEntity;
        this.userGoal = builder.userGoalEntity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private Integer weight;
        private Integer height;
        private Lifestyle lifestyleEntity;
        private LocalDate birthday;
        private Gender genderEntity;
        private UserGoal userGoalEntity;
        private Role roleEntity;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Builder withLifestyle(Lifestyle lifestyleEntity) {
            this.lifestyleEntity = lifestyleEntity;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withGender(Gender genderEntity) {
            this.genderEntity = genderEntity;
            return this;
        }

        public Builder withUserGoal(UserGoal userGoalEntity) {
            this.userGoalEntity = userGoalEntity;
            return this;
        }

        public Builder withRole(Role roleEntity) {
            this.roleEntity = roleEntity;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
