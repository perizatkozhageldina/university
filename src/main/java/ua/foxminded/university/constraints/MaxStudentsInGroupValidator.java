package ua.foxminded.university.constraints;

import ua.foxminded.university.repository.GroupJdbcRepository;
import ua.foxminded.university.repository.StudentJdbcRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxStudentsInGroupValidator implements ConstraintValidator<MaxStudentsInGroup, Long> {
    private GroupJdbcRepository groupJdbcRepository;
    private StudentJdbcRepository studentJdbcRepository;

    public MaxStudentsInGroupValidator(GroupJdbcRepository groupJdbcRepository, StudentJdbcRepository studentJdbcRepository) {
        this.groupJdbcRepository = groupJdbcRepository;
        this.studentJdbcRepository = studentJdbcRepository;
    }

    @Override
    public void initialize(MaxStudentsInGroup maxStudentsInGroup) {
    }

    @Override
    public boolean isValid(Long groupId, ConstraintValidatorContext context) {
        if (groupId == null) {
            return true;
        }

        int maxSize = groupJdbcRepository.findById(groupId).get().getMaxStudents();
        int currentSize = studentJdbcRepository.countByGroupId(groupId);
        return currentSize < maxSize;
    }
}