package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TeacherMapperTest {

    // Use the real instance of the mapper generated by MapStruct
    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    void shouldMapTeacherDtoToTeacherCorrectly() {
        // Prepare a TeacherDto object with test data
        TeacherDto dto = new TeacherDto();
        dto.setId(42L);
        dto.setLastName("Taylor");
        dto.setFirstName("Alex");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        // Convert TeacherDto to Teacher
        Teacher teacher = teacherMapper.toEntity(dto);

        // Assertions to verify mapping
        assertNotNull(teacher, "Teacher object should not be null");
        assertEquals(dto.getId(), teacher.getId(), "ID should match");
        assertEquals(dto.getLastName(), teacher.getLastName(), "Last name should match");
        assertEquals(dto.getFirstName(), teacher.getFirstName(), "First name should match");
        assertEquals(dto.getCreatedAt(), teacher.getCreatedAt(), "Creation date should match");
        assertEquals(dto.getUpdatedAt(), teacher.getUpdatedAt(), "Update date should match");
    }

    @Test
    void shouldMapTeacherDtoListToTeacherList() {
        // Create a list of TeacherDto objects
        TeacherDto dto1 = new TeacherDto();
        dto1.setId(10L);
        dto1.setLastName("Parker");
        dto1.setFirstName("Jordan");

        TeacherDto dto2 = new TeacherDto();
        dto2.setId(11L);
        dto2.setLastName("Adams");
        dto2.setFirstName("Morgan");

        List<TeacherDto> dtoList = List.of(dto1, dto2);

        // Convert list of TeacherDto to Teacher
        List<Teacher> teachers = teacherMapper.toEntity(dtoList);

        // Assertions to verify the conversion
        assertNotNull(teachers, "The Teacher list should not be null");
        assertEquals(2, teachers.size(), "The size of the Teacher list should be 2");
        assertEquals(dto1.getId(), teachers.get(0).getId(), "First teacher ID should match");
        assertEquals(dto2.getId(), teachers.get(1).getId(), "Second teacher ID should match");
    }

    @Test
    void shouldMapTeacherToTeacherDtoCorrectly() {
        // Prepare a Teacher object with test data
        Teacher teacher = Teacher.builder()
                .id(25L)
                .lastName("Brown")
                .firstName("Jamie")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Convert Teacher to TeacherDto
        TeacherDto dto = teacherMapper.toDto(teacher);

        // Assertions to verify mapping
        assertNotNull(dto, "TeacherDto object should not be null");
        assertEquals(teacher.getId(), dto.getId(), "ID should match");
        assertEquals(teacher.getLastName(), dto.getLastName(), "Last name should match");
        assertEquals(teacher.getFirstName(), dto.getFirstName(), "First name should match");
        assertEquals(teacher.getCreatedAt(), dto.getCreatedAt(), "Creation date should match");
        assertEquals(teacher.getUpdatedAt(), dto.getUpdatedAt(), "Update date should match");
    }

    @Test
    void shouldMapTeacherListToTeacherDtoList() {
        // Create a list of Teacher objects
        Teacher teacher1 = Teacher.builder().id(7L).lastName("Harris").firstName("Taylor").build();
        Teacher teacher2 = Teacher.builder().id(8L).lastName("Clark").firstName("Riley").build();

        List<Teacher> teacherList = List.of(teacher1, teacher2);

        // Convert list of Teacher to TeacherDto
        List<TeacherDto> dtoList = teacherMapper.toDto(teacherList);

        // Assertions to verify the conversion
        assertNotNull(dtoList, "The TeacherDto list should not be null");
        assertEquals(2, dtoList.size(), "The size of the TeacherDto list should be 2");
        assertEquals(teacher1.getId(), dtoList.get(0).getId(), "First TeacherDto ID should match");
        assertEquals(teacher2.getId(), dtoList.get(1).getId(), "Second TeacherDto ID should match");
    }
}
