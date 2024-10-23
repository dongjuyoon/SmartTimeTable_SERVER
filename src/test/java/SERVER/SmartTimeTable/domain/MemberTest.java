package SERVER.SmartTimeTable.domain;

import SERVER.SmartTimeTable.Domain.Member;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testMemberCreation() {
        // Given
        Member member = new Member();
        member.setId("testUser");
        member.setName("Test User");
        member.setPassword("password123");
        member.setStudentId(123456);
        member.setEmail("test@example.com");
        member.setEmailVerified(true);
        member.setMajors(Arrays.asList("Computer Science", "Mathematics"));
        member.setCoreElectives(Arrays.asList("Data Structures", "Algorithms"));
        member.setCommonElectives(Arrays.asList("English", "History"));

        // When
        String id = member.getId();
        String name = member.getName();
        String password = member.getPassword();
        int studentId = member.getStudentId();
        String email = member.getEmail();
        boolean emailVerified = member.isEmailVerified();
        List<String> majors = member.getMajors();
        List<String> coreElectives = member.getCoreElectives();
        List<String> commonElectives = member.getCommonElectives();

        // Then
        assertEquals("testUser", id);
        assertEquals("Test User", name);
        assertEquals("password123", password);
        assertEquals(123456, studentId);
        assertEquals("test@example.com", email);
        assertTrue(emailVerified);
        assertEquals(Arrays.asList("Computer Science", "Mathematics"), majors);
        assertEquals(Arrays.asList("Data Structures", "Algorithms"), coreElectives);
        assertEquals(Arrays.asList("English", "History"), commonElectives);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Member member = new Member();
        member.setId("testUser");
        member.setName("Test User");
        member.setPassword("password123");
        member.setStudentId(123456);
        member.setEmail("test@example.com");
        member.setEmailVerified(true);

        // When & Then
        assertEquals("testUser", member.getId());
        assertEquals("Test User", member.getName());
        assertEquals("password123", member.getPassword());
        assertEquals(123456, member.getStudentId());
        assertEquals("test@example.com", member.getEmail());
        assertTrue(member.isEmailVerified());
    }
}
