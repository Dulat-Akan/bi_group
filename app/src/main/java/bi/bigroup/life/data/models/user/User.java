package bi.bigroup.life.data.models.user;

import java.util.List;

import static bi.bigroup.life.utils.DateUtils.getBirthDateStr;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class User {
    public String fullname;
    public String employeeCode;
    public String jobPosition;
    public String company;
    public String iin;
    public String birthDate;
    public String gender;
    public String familyStatus;
    public String childrenQuantity;
    public String clothingSize;
    public String shoeSize;
    public String email;
    public String workPhoneNumber;
    public String mobilePhoneNumber;
    public String address;
    public String totalExperience;
    public String corporateExperience;
    public String administrativeChiefName;
    public String functionalChiefName;
    public String adLogin;
    public List<Education> educations;
    public MedicalExamination medicalExamination;

    class MedicalExamination {
        public String last;
        public String next;
    }

    public String getCode() {
        return replaceNullTrim(employeeCode);
    }

    public String getFullname() {
        return replaceNull(fullname);
    }

    public String getEmployeeCode() {
        return replaceNull(employeeCode);
    }

    public String getJobPosition() {
        return replaceNull(jobPosition);
    }

    public String getCompany() {
        return replaceNull(company);
    }

    public String getIin() {
        return replaceNull(iin);
    }

    public String getBirthDate() {
        return getBirthDateStr(replaceNull(birthDate));
    }

    public String getGender() {
        return replaceNull(gender);
    }

    public String getFamilyStatus() {
        return replaceNull(familyStatus);
    }

    public String getChildrenQuantity() {
        return replaceNull(childrenQuantity);
    }

    public String getClothingSize() {
        return replaceNull(clothingSize);
    }

    public String getShoeSize() {
        return replaceNull(shoeSize);
    }

    public String getEmail() {
        return replaceNull(email);
    }

    public String getWorkPhoneNumber() {
        return replaceNull(workPhoneNumber);
    }

    public String getMobilePhoneNumber() {
        return replaceNull(mobilePhoneNumber);
    }

    public String getAddress() {
        return replaceNull(address);
    }

    public String getTotalExperience() {
        return replaceNull(totalExperience);
    }

    public String getCorporateExperience() {
        return replaceNull(corporateExperience);
    }

    public String getAdministrativeChiefName() {
        return replaceNull(administrativeChiefName);
    }

    public String getFunctionalChiefName() {
        return replaceNull(functionalChiefName);
    }

    public String getAdLogin() {
        return replaceNull(adLogin);
    }
}
