package me.sanghyuk.subwayboard.controller;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.PageRequestDTO;
import me.sanghyuk.subwayboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class SubwayBoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }
}
