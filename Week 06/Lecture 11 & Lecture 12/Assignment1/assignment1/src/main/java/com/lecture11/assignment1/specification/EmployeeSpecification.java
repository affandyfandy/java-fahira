package com.lecture11.assignment1.specification;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.lecture11.assignment1.model.Employee;
import java.time.LocalDate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeSpecification implements Specification<Employee> {

    private List<SearchCriteria> list;

    public EmployeeSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        
        for (SearchCriteria criteria : list) {
            switch (criteria.getOperation()) {
                case EQUAL_FIRST_NAME:
                    predicates.add(builder.equal(root.get("firstName"), criteria.getValue()));
                    break;
                case EQUAL_LAST_NAME:
                    predicates.add(builder.equal(root.get("lastName"), criteria.getValue()));
                    break;
                case EQUAL_GENDER:
                    predicates.add(builder.equal(root.get("gender"), criteria.getValue()));
                    break;
                case GREATER_THAN_HIRE_DATE:
                    predicates.add(builder.greaterThan(root.get("hireDate"), (LocalDate) criteria.getValue()));
                    break;
                case LESS_THAN_HIRE_DATE:
                    predicates.add(builder.lessThan(root.get("hireDate"), (LocalDate) criteria.getValue()));
                    break;
                case EQUAL_HIRE_DATE:
                    predicates.add(builder.equal(root.get("hireDate"), criteria.getValue()));
                    break;
                case GREATER_THAN_BIRTH_DATE:
                    predicates.add(builder.greaterThan(root.get("birthDate"), (LocalDate) criteria.getValue()));
                    break;
                case LESS_THAN_BIRTH_DATE:
                    predicates.add(builder.lessThan(root.get("birthDate"), (LocalDate) criteria.getValue()));
                    break;
                case EQUAL_BIRTH_DATE:
                    predicates.add(builder.equal(root.get("birthDate"), criteria.getValue()));
                    break;
                case LIKE_FIRST_NAME:
                    predicates.add(builder.like(builder.lower(root.get("firstName")), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case LIKE_LAST_NAME:
                    predicates.add(builder.like(builder.lower(root.get("lastName")), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                default:
                    throw new UnsupportedOperationException("Search operation not supported: " + criteria.getOperation());
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

