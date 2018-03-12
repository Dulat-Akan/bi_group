package bi.bigroup.life.data.models.employees;

import static bi.bigroup.life.utils.DateUtils.getBirthDateStr;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class Employee {
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

    public String getBirthDate() {
        return getBirthDateStr(replaceNull(birthDate));
    }

    public boolean getHasAvatar() {
        return isOkBoolean(hasAvatar);
    }
}
