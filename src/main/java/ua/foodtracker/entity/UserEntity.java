package ua.foodtracker.entity;

import java.sql.Date;

public class UserEntity {
    private final Integer id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer weight;
    private final Integer height;
    private final LifestyleEntity lifestyleEntity;
    private final Date birthday;
    private final GenderEntity genderEntity;
    private final UserGoalEntity userGoalEntity;
    private final RoleEntity roleEntity;

    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.weight = builder.weight;
        this.height = builder.height;
        this.lifestyleEntity = builder.lifestyleEntity;
        this.birthday = builder.birthday;
        this.genderEntity = builder.genderEntity;
        this.roleEntity = builder.roleEntity;
        this.userGoalEntity = builder.userGoalEntity;
    }

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

    public LifestyleEntity getLifestyleEntity() {
        return lifestyleEntity;
    }

    public Date getBirthday() {
        return birthday;
    }

    public GenderEntity getGenderEntity() {
        return genderEntity;
    }

    public UserGoalEntity getUserGoalEntity() {
        return userGoalEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
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
        private LifestyleEntity lifestyleEntity;
        private Date birthday;
        private GenderEntity genderEntity;
        private UserGoalEntity userGoalEntity;
        private RoleEntity roleEntity;

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

        public Builder withLifestyle(LifestyleEntity lifestyleEntity) {
            this.lifestyleEntity = lifestyleEntity;
            return this;
        }

        public Builder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withGender(GenderEntity genderEntity) {
            this.genderEntity = genderEntity;
            return this;
        }

        public Builder withUserGoal(UserGoalEntity userGoalEntity) {
            this.userGoalEntity = userGoalEntity;
            return this;
        }

        public Builder withRole(RoleEntity roleEntity) {
            this.roleEntity = roleEntity;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
