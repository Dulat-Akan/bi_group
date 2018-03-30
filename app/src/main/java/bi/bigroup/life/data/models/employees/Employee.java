package bi.bigroup.life.data.models.employees;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import static bi.bigroup.life.utils.DateUtils.getEmployeeBirthday;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public String code;
    public String fullname;
    public String firstname;
    public String login;
    public String birthDate;
    public String jobPosition;
    public String company;
    public String companyName;
    public String departmentName;
    public String email;
    public String workPhoneNumber;
    public String mobilePhoneNumber;
    public String address;
    public String administrativeChiefName;
    public String functionalChiefName;
    public Boolean isBirthdayToday;
    public Boolean hasAvatar;

    public String getCode() {
        return replaceNullTrim(code);
    }

    public String getFullName() {
        return replaceNull(fullname);
    }

    public String getFunctionalChiefName() {
        return replaceNull(functionalChiefName);
    }

    public String getAdministrativeChiefName() {
        return replaceNull(administrativeChiefName);
    }

    public String getFirstName() {
        return replaceNull(firstname);
    }

    public String getJobPosition() {
        return replaceNull(jobPosition);
    }

    public String getLogin() {
        return replaceNull(login);
    }

    public String getMobilePhoneNumber() {
        return replaceNull(mobilePhoneNumber);
    }

    public String getWorkPhoneNumber() {
        return replaceNull(workPhoneNumber);
    }

    public String getEmail() {
        return replaceNull(email);
    }

    public boolean getBirthdayToday() {
        return isOkBoolean(isBirthdayToday);
    }

    public String getBirthDate(Context context) {
        return isStringOk(birthDate) ? getEmployeeBirthday(context, replaceNull(birthDate)) : EMPTY_STR;
    }

    public boolean getHasAvatar() {
        return isOkBoolean(hasAvatar);
    }
}
