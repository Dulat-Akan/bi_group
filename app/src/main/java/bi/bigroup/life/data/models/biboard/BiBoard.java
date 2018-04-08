package bi.bigroup.life.data.models.biboard;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;

public class BiBoard {
    public int title;
    public int first;
    public int second;
    public int third;
    public List<Suggestion> popularSuggestions;
    public List<Suggestion> allSuggestions;
    public List<Questionnaire> popularQuestionnaires;
    public List<Questionnaire> allQuestionnaires;

    public List<Employee> employees;
    public List<Vacancy> vacancies;
    public int allEmployeesCount;

    public BiBoard(int title) {
        this.title = title;
    }

    public BiBoard(int title, int first, int second, int third, List<Suggestion> popularSuggestions, List<Suggestion> allSuggestions, List<Questionnaire> popularQuestionnaires,
                   List<Questionnaire> allQuestionnaires,
                   List<Employee> employees, List<Vacancy> vacancies, Integer allEmployeesCount) {
        this.title = title;
        this.first = first;
        this.second = second;
        this.third = third;
        this.popularSuggestions = popularSuggestions;
        this.allSuggestions = allSuggestions;
        this.popularQuestionnaires = popularQuestionnaires;
        this.allQuestionnaires = allQuestionnaires;
        this.employees = employees;
        this.vacancies = vacancies;
        this.allEmployeesCount = allEmployeesCount;
    }
}