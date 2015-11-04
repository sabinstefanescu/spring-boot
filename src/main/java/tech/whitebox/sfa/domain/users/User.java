package tech.whitebox.sfa.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.whitebox.sfa.utilities.Profile;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements Serializable{

    @Id
    @Column(name = "USER_ID")
    private Integer id;
    
    @Column(name = "USER_ACCOUNT")
    private String account;
    
    @Column(name = "USER_FIRST_NAME")
    private String firstName;
    
    @Column(name = "USER_LAST_NAME")
    private String lastName;
    
    @Column(name = "USER_EMAIL")
    private String email;
    
    @Column(name = "USER_PHONE")
    private String phone;
    
    @Column(name = "USER_LAST_ACCESS_DATE")
    private Date lastAccessDate;

    @Column(name = "USER_EMPLOYEE_ID")
    private Integer employeeId;

    @Column(name = "USER_POS_CODE")
    private String posCode;
    
    @Transient
    private List<Profile> profiles = new ArrayList<>();

    @Transient
    private List<Role> roles = new ArrayList<>();

    public User(){
    }

    public User(Integer id, String account, String firstName, String lastName, String email, Integer employeeId, String posCode) {
        this.id = id;
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeId = employeeId;
        this.posCode = posCode;
    }


    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastAccessDate() {
        if (lastAccessDate != null)
            return new Date(lastAccessDate.getTime());
        else
            return null;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = new Date(lastAccessDate.getTime());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return this.profiles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean hasProfile(Profile profile) {
        return this.profiles.contains(profile);
    }

    public static class Builder {
        private Integer id;
        private String account;
        private String firstName;
        private String lastName;
        private String email;
        private Integer employeeId;
        private String posCode;


        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setAccount(String account) {
            this.account = account;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User buildUser(){
            return new User(id, account, firstName, lastName, email, employeeId, posCode);
        }

        public Builder setEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder setPosCode(String posCode) {
            this.posCode = posCode;
            return this;
        }
    }

    @JsonIgnore
    public boolean isDbsr(){
        if (posCode != null) {
            return posCode.startsWith("AD");
        } else {
            return false;
        }
    }

}
