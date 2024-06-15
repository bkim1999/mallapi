package org.zerock.mallapi.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDto;
import org.zerock.mallapi.dto.PageResponseDto;
import org.zerock.mallapi.dto.TodoDto;
import org.zerock.mallapi.repository.TodoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDto todoDto) {
        log.info("........");

        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.getTno();
    }

    @Override
    public TodoDto get(Long tno) {
        Todo todo = todoRepository.findById(tno).orElseThrow();
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public void modify(TodoDto todoDto) {
        Todo todo = todoRepository.findById(todoDto.getTno()).orElseThrow();
        todo.changeTitle(todoDto.getTitle());
        todo.changeComplete(todoDto.isComplete());
        todo.changeDueDate(todoDto.getDueDate());
        
        todoRepository.save(todo);
    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {
        
        Pageable pageable = 
            PageRequest.of(
                pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("tno").descending());
        
        Page<Todo> result = todoRepository.findAll(pageable);

        List<TodoDto> dtoList = result.getContent().stream()
            .map(todo -> modelMapper.map(todo, TodoDto.class))
            .toList();

        long totalCount = result.getTotalElements();

        PageResponseDto<TodoDto> responseDto = 
            PageResponseDto.<TodoDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .totalCount(totalCount)
                .build();
            
        return responseDto;

    }
}

