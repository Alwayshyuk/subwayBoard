package me.sanghyuk.subwayboard.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public String chatGET(@RequestParam Long lno, Model model){
        model.addAttribute("lno", lno);
        return "chat/chat";
    }
}