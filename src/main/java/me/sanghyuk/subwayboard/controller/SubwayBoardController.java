package me.sanghyuk.subwayboard.controller;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SubwayBoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String board(Model model) {
        return "index";
    }
}
