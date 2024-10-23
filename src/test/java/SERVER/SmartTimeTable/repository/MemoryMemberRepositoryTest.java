package SERVER.SmartTimeTable.repository;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class MemoryMemberRepositoryTest {

     MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
    }

    @Test
    public void login_Success() {
        // Given
        Member member = new Member();
        member.setId("testUser");
        member.setPassword("password123");
        memberRepository.save(member);

        // When
        ResponseEntity<String> response = login("testUser", "password123");

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("로그인 성공", response.getBody());
    }

    @Test
    public void login_Fail_InvalidId() {
        // When
        ResponseEntity<String> response = login("invalidUser", "password123");

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("아이디가 맞지 않습니다.", response.getBody());
    }

    @Test
    public void login_Fail_InvalidPassword() {
        // Given
        Member member = new Member();
        member.setId("testUser");
        member.setPassword("password123");
        memberRepository.save(member);

        // When
        ResponseEntity<String> response = login("testUser", "wrongPassword");

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("비밀번호가 맞지 않습니다.", response.getBody());
    }

    public ResponseEntity<String> login(String id, String password) {
        Member loginMember = memberRepository.findById(id);
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 맞지 않습니다.");
        } else if (!loginMember.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 맞지 않습니다.");
        }
        return ResponseEntity.ok("로그인 성공");
    }
    @Test
    public void simpleTest() {
        assertEquals(1, 1); // 간단한 테스트
    }

}
