package org.zerock.mallapi.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mallapi.dto.PageRequestDto;
import org.zerock.mallapi.dto.PageResponseDto;
import org.zerock.mallapi.dto.TodoDto;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoServiceTests {
    
    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister() {
        TodoDto todoDto = TodoDto.builder()
            .title("테스트")
            .writer("ServiceTester")
            .dueDate(LocalDate.of(2024, 6, 14))
            .build();

        Long tno = todoService.register(todoDto);
        
        log.info("TNO: {}", tno);
    }

    @Test
    public void testGet() {
        Long tno = 10L;
        TodoDto todoDto = todoService.get(tno);
        log.info(todoDto);
    }

    @Test
    public void testModify() {
        Long tno = 15L;
        TodoDto todoDto = TodoDto.builder()
            .tno(tno)
            .title("테스트")
            .writer("ServiceModifyTester")
            .dueDate(LocalDate.of(2024, 6, 14))
            .build();
        todoService.modify(todoDto);
    }
    
    @Test
    public void testRemove() {
        Long tno = 20L;
        todoService.remove(tno);
    }

    @Test
    public void testList() {

        PageRequestDto pageRequestDto = 
            PageRequestDto.builder()
                .page(2)
                .size(10)
                .build();
        
        PageResponseDto<TodoDto> response = todoService.list(pageRequestDto);

        log.info(response);

    }

}
