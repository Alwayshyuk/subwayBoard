package me.sanghyuk.subwayboard.controller;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.BoardDTO;
import me.sanghyuk.subwayboard.dto.PageRequestDTO;
import me.sanghyuk.subwayboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class SubwayBoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }
    @GetMapping("/write")
    public void write(Principal principal, Model model){

    }
//    @PostMapping("/write")
//    public void write(@ModelAttribute BoardDTO boardDTO, Principal principal){
//        model
//    }
}
