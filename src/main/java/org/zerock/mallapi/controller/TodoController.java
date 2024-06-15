package org.zerock.mallapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.dto.PageRequestDto;
import org.zerock.mallapi.dto.PageResponseDto;
import org.zerock.mallapi.dto.TodoDto;
import org.zerock.mallapi.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {
    
    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDto get(@PathVariable(name = "tno") Long tno) {
        log.info("Tno: {}", tno);
        return todoService.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDto<TodoDto> list(@ModelAttribute PageRequestDto pageRequestDto) {
        log.info(pageRequestDto);
        return todoService.list(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDto todoDto) {
        log.info("TodoDto: {}", todoDto);
        Long tno = todoService.register(todoDto);
        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
        @PathVariable(name = "tno") Long tno,
        @RequestBody TodoDto todoDto) {
        todoDto.setTno(tno);
        log.info("Modify: {}", todoDto);
        todoService.modify(todoDto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> modify(@PathVariable(name = "tno") Long tno) {
        log.info("Remove: {}", tno);
        todoService.remove(tno);
        return Map.of("RESULT", "SUCCESS");
    }

    

}

