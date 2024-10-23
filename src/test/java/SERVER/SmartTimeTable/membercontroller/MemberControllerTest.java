package SERVER.SmartTimeTable.membercontroller;

import SERVER.SmartTimeTable.Controller.MemberController;
import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemoryMemberRepository memberRepository; // MockBean으로 변경

    @Test
    public void signUpWithDepartment_Success() throws Exception {
        // Given
        String id = "testUser";
        String major = "Computer Science";
        int studentId = 123456;

        when(memberRepository.findById(id)).thenReturn(null); // 새로운 사용자
        doNothing().when(memberRepository).save(any(Member.class));

        // When & Then
        mockMvc.perform(post("/api/users/sign")
                        .param("id", id)
                        .param("major", major)
                        .param("studentId", String.valueOf(studentId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(content().string("학과 및 학번 정보 저장 완료"));
    }


    @Test
    void signUpWithDepartment_UserExists() throws Exception {
        // Given
        String id = "testUser";
        String major = "Computer Science";
        int studentId = 123456;

        Member existingMember = new Member();
        existingMember.setId(id);
        existingMember.setMajors(List.of("Mathematics"));

        when(memberRepository.findById(id)).thenReturn(existingMember); // 기존 사용자

        // When & Then
        mockMvc.perform(post("/api/users/sign")
                        .param("id", id)
                        .param("major", major)
                        .param("studentId", String.valueOf(studentId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(content().string("학과 및 학번 정보 저장 완료"));

        // Verify that the existing member's majors are updated
        verify(memberRepository).save(existingMember); // 기존 사용자의 정보가 업데이트되었는지 확인
    }

    @Test
    void login_Success() throws Exception {
        // Given
        String id = "testUser";
        String password = "password123";
        Member member = new Member();
        member.setId(id);
        member.setPassword(password);

        when(memberRepository.findById(id)).thenReturn(member); // 사용자 존재

        // When & Then
        mockMvc.perform(post("/api/users/login")
                        .param("id", id)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 성공"));
    }

    @Test
    void login_Fail_UserNotFound() throws Exception {
        // Given
        String id = "nonExistentUser";
        String password = "password123";

        when(memberRepository.findById(id)).thenReturn(null); // 사용자 없음

        // When & Then
        mockMvc.perform(post("/api/users/login")
                        .param("id", id)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("아이디가 맞지 않습니다."));
    }

    @Test
    void login_Fail_WrongPassword() throws Exception {
        // Given
        String id = "testUser";
        String wrongPassword = "wrongPassword";
        Member member = new Member();
        member.setId(id);
        member.setPassword("password123");

        when(memberRepository.findById(id)).thenReturn(member); // 사용자 존재

        // When & Then
        mockMvc.perform(post("/api/users/login")
                        .param("id", id)
                        .param("password", wrongPassword)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("비밀번호가 맞지 않습니다."));
    }

    @Test
    void verifyEmail_UserNotFound() throws Exception {
        // Given
        String id = "nonExistentUser";

        when(memberRepository.findById(id)).thenReturn(null); // 사용자 없음

        // When & Then
        mockMvc.perform(post("/api/users/verify_email")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound())
                .andExpect(content().string("사용자가 존재하지 않음"));
    }

    @Test
    void completeSignUp_UserNotFound() throws Exception {
        // Given
        String id = "nonExistentUser";

        when(memberRepository.findById(id)).thenReturn(null); // 사용자 없음

        // When & Then
        mockMvc.perform(get("/api/users/complete")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound())
                .andExpect(content().string("null")); // null 대신 "null" 문자열을 기대
    }

    // 추가 테스트 메서드 작성...
}
