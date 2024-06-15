package org.zerock.mallapi.service;

import org.zerock.mallapi.dto.PageRequestDto;
import org.zerock.mallapi.dto.PageResponseDto;
import org.zerock.mallapi.dto.TodoDto;

public interface TodoService {
    Long register(TodoDto todoDto);
    TodoDto get(Long tno);
    void modify(TodoDto todoDto);
    void remove(Long tno);
    PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto);
}
