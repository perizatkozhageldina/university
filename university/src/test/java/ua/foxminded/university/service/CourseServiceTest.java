package ua.foxminded.university.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import ua.foxminded.university.dao.CourseJdbcDAO;
import ua.foxminded.university.model.Course;

class CourseServiceTest {
    @Mock Course course = Mockito.mock(Course.class);
    @Mock CourseJdbcDAO dao = Mockito.mock(CourseJdbcDAO.class);
    CourseService service = new CourseService();
    ReflectionTestUtils.setField(service, "dao", dao);
    

    @Test
    void shouldCallDaoAdd_whenServiceAddMethodCalled() {        
        service.add(course);
        verify(dao, times(1)).add(course);
    }

}
