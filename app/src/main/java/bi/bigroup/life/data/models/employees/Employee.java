package bi.bigroup.life.data.models.employees;

import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Employee {
    public String code;
    public String fullname;
    public String firstname;
    public String birthDate;
    public String jobPosition;
    public String companyName;
    public String departmentName;
    public Boolean isBirthdayToday;
    public Boolean hasAvatar;

    public String getCode() {
        return replaceNull(code);
    }

    public String getFullName() {
        return replaceNull(fullname);
    }

    public String getFirstName() {
        return replaceNull(firstname);
    }

    public String getJobPosition() {
        return replaceNull(jobPosition);
    }

    public boolean getBirthdayToday() {
        return isOkBoolean(isBirthdayToday);
    }

    public boolean getHasAvatar() {
        return isOkBoolean(hasAvatar);
    }
}
