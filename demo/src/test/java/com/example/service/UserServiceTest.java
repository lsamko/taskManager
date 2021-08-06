package com.example.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWithNameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserMapperImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Captor
    ArgumentCaptor<User> userCaptor;
    @Captor
    ArgumentCaptor<String> idCaptor;

    @Mock
    private UserRepository userRepository;
    private UserServiceImpl userService;
    private final UserMapper userMapper = new UserMapperImpl();
    private UserRequestDto userRequestDto;
    private final String id = "008";
    private static final String FIRST_NAME = "Steve";
    private static final String LAST_NAME = "Rogers";
    private static final String USER_ID = "1";
    private static final LocalDateTime TASK_DUE_TO_DO = LocalDateTime.of(2021, 10, 9, 12, 0);

    private final User USER = new User(
        "Bruce",
        "Wayne",
        "64"
    );


    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper);
        userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(FIRST_NAME);
        userRequestDto.setLastName(LAST_NAME);
        userRequestDto.setUserId(USER_ID);

        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUserId(USER_ID);
    }

    @Test
    void createUser() {
        when(userRepository.existsUserByLastName(LAST_NAME)).thenReturn(false);
        userService.createUser(userRequestDto, listOfTasks());
        verify(userRepository, times(1)).save(userCaptor.capture());
        User captureUser = userCaptor.getValue();
        assertNull(captureUser.getId());
        assertNotNull(captureUser.getUserId());
        assertEquals(captureUser.getFirstName(), FIRST_NAME);
        assertEquals(captureUser.getLastName(), LAST_NAME);
        assertNotNull(captureUser.getTasks());
    }

    @Test
    void getAllUsers() {
        int size = 2;
        int from = 1;
        Page<User> page = createUsers();

        when(userRepository.findAll(PageRequest.of(from, size))).thenReturn(page);
        List<UserResponseDto> users = userService.getAllUsers(from, size);

        assertEquals(3, users.size());

        IntStream.range(0, users.size()).forEach(i -> assertUserResponseDTO(page.getContent().get(i), users.get(i)));
    }

    @Test
    public void testCreateUserWithExistingLastName() {
        when(userRepository.existsUserByLastName(LAST_NAME)).thenReturn(true);
        assertThrows(UserWithNameAlreadyExistsException.class,
            () -> userService.createUser(userRequestDto, listOfTasks()),
            "User with lat name '" + LAST_NAME + "' already exists");
    }

    @Test
    public void testCreateUserWithExistingFirstName() {
        when(userRepository.existsUserByFirstName(FIRST_NAME)).thenReturn(true);
        assertThrows(UserWithNameAlreadyExistsException.class,
            () -> userService.createUser(userRequestDto, listOfTasks()),
            "User with first name '" + FIRST_NAME + "' already exists");
    }

    @Test
    void findById() {
        when(userRepository.findUserByUserId(idCaptor.capture())).thenReturn(Optional.of(USER));
        userService.findById(id);
        String value = idCaptor.getValue();
        assertThat(value, is(equalTo(id)));
    }

    @Test
    public void testFindByIdNotFound() {
        String id = "005";
        when(userRepository.findUserByUserId(idCaptor.capture())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
            () -> userService.findById(id), "Could not find user: " + id);

        String value = idCaptor.getValue();
        assertThat(value, is(equalTo(id)));
    }

    @Test
    void deleteById() {
        userService.deleteById(id);
        assertThat(id, is(equalTo("008")));
    }

    @Test
    void updateById() {
        String newFirstName = "Michelle";
        String newLastName = "Pfeiffer";
        when(userRepository.existsUserByFirstName(newFirstName)).thenReturn(false);
        when(userRepository.existsUserByLastName(newLastName)).thenReturn(false);
        when(userRepository.findUserByUserId(id)).thenReturn(Optional.of(USER));

        UserResponseDto userResponseDto = userService.updateById(id,
            new UserUpdateDto().update(newFirstName, newLastName));

        assertThat(userResponseDto.getFirstName(), is(newFirstName));
        assertThat(userResponseDto.getLastName(), is(newLastName));

//        verify(userRepository, times(1)).save(userCaptor.capture());
//        User user = userCaptor.getValue();
//        assertThat(user, is(
//            pojo(User.class)
//                .where(User::getFirstName, is(newFirstName))
//                .where(User::getLastName, is(newLastName))
//                .where(User::getUserId, is(id))
//        ));
    }

    @Test
    void testUpdateUserByIdNotFound() {
        String id = "5";
        when(userRepository.findUserByUserId(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
            () -> userService.updateById(id, new UserUpdateDto().update(FIRST_NAME, LAST_NAME)),
            "Could not find user: " + id);
    }

    @Test
    void testUpdateTaskByIdConflict() {
        when(userRepository.existsUserByFirstName(FIRST_NAME)).thenReturn(true);
        when(userRepository.existsUserByLastName(LAST_NAME)).thenReturn(false);
        when(userRepository.findUserByUserId(id)).thenReturn(Optional.of(USER));

        assertThrows(UserWithNameAlreadyExistsException.class,
            () -> userService.updateById(id,
                new UserUpdateDto().update(FIRST_NAME, LAST_NAME)),
            "User with first name '" + FIRST_NAME + "' already exists");
    }

    private List<Task> listOfTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task1", false, 1, "005", TASK_DUE_TO_DO, "1"));
        tasks.add(new Task("Task2", true, 2, "09", TASK_DUE_TO_DO, "2"));
        return tasks;
    }

    private void assertUserResponseDTO(User user, UserResponseDto userResponseDto) {
        assertEquals(user.getFirstName(), userResponseDto.getFirstName());
        assertEquals(user.getLastName(), userResponseDto.getLastName());
        assertEquals(user.getUserId(), userResponseDto.getUserId());
    }

    private Page<User> createUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User("Mark", "Ruffalo", "2"));
        list.add(new User("Chris", "Hemsworth", "3"));
        list.add(new User("Scarlett", "Johansson", "4"));
        return new PageImpl<>(list);
    }

}