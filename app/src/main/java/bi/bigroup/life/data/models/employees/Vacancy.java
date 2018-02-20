package bi.bigroup.life.data.models.employees;

import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Vacancy {
    public String jobPosition;
    public String companyName;
    public String createDate;
    public String departmentName;
    public String salary;

    public String getJobPosition() {
        return replaceNull(jobPosition);
    }

    public String getCompanyName() {
        return replaceNull(companyName);
    }

    public String getCreateDate() {
        return replaceNull(createDate);
    }

    public String getDepartmentName() {
        return replaceNull(departmentName);
    }

    public String getSalary() {
        return replaceNull(salary);
    }
}
