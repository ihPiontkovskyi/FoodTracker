package ua.foodtracker.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class User {
    private final Integer id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer weight;
    private final Integer height;
    private final Lifestyle lifestyle;
    private final Date birthday;
    private final Gender gender;
    private final UserGoal userGoal;
    private final Role role;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        if (builder.height == null) {
            builder.weight = 0;
        }
        this.weight = builder.weight;
        if (builder.height == null) {
            builder.height = 0;
        }
        this.height = builder.height;
        if (builder.lifestyle == null) {
            builder.lifestyle = Lifestyle.NOT_SELECTED;
        }
        this.lifestyle = builder.lifestyle;
        if (builder.birthday == null) {
            builder.birthday = Date.valueOf("2000-01-01");
        }
        this.birthday = builder.birthday;
        if (builder.gender == null) {
            builder.gender = Gender.NOT_SELECTED;
        }
        this.gender = builder.gender;
        if (builder.role == null) {
            builder.role = Role.USER;
        }
        this.role = builder.role;
        if (builder.userGoal == null) {
            builder.userGoal = build();
        }
        this.userGoal = builder.userGoal;
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

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public Date getBirthday() {
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

    private UserGoal build() {
        int dailyEnergy;
        int age = Period.between(LocalDate.parse(birthday.toString()), LocalDate.now()).getYears();
        switch (gender) {
            case MALE:
                dailyEnergy = (int) ((88.36 + 13.4 * weight + 4.8 * height - 5.7 * age) * lifestyle.getCoefficient());
                break;
            case FEMALE:
                dailyEnergy = (int) ((447.6 + 9.2 * weight + 3.1 * height - 4.3 * age) * lifestyle.getCoefficient());
            default:
                dailyEnergy = (int) ((100 + 13 * weight + 5 * height - 6 * age) * lifestyle.getCoefficient());
        }
        int sixthPart = dailyEnergy / 6;
        return UserGoal.builder()
                .withDailyEnergyGoal(dailyEnergy)
                .withDailyWaterGoal(2000)
                .withDailyFatGoal(sixthPart / 9)
                .withDailyProteinGoal(sixthPart / 4)
                .withDailyCarbohydrateGoal(sixthPart)
                .build();
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
        private Lifestyle lifestyle;
        private Date birthday;
        private Gender gender;
        private UserGoal userGoal;
        private Role role;

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

        public Builder withLifestyle(Lifestyle lifestyle) {
            this.lifestyle = lifestyle;
            return this;
        }

        public Builder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder withUserGoal(UserGoal userGoal) {
            this.userGoal = userGoal;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
