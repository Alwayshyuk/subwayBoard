package me.sanghyuk.subwayboard.controller;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.BoardDTO;
import me.sanghyuk.subwayboard.dto.PageRequestDTO;
import me.sanghyuk.subwayboard.service.BoardService;
import me.sanghyuk.subwayboard.service.SubwayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class SubwayBoardController {

    private final BoardService boardService;

    private final SubwayService subwayService;


    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }
    @GetMapping("/write")
    public void write(){}


    @PostMapping("/write")
    public String write(@ModelAttribute BoardDTO boardDTO,@CookieValue(value="userEmail", required = false) Cookie rCookie){
        System.out.println("@@@@@@@@@@@"+boardDTO.getStdStation());
        boardService.save(boardDTO, rCookie.getValue());
        return "redirect:/board/list";

    }

    @PostMapping("/stnm")
    public String stnNm(@RequestParam String stnNm, Model model) throws Exception{
        List<String> stnList = subwayService.getStnName(stnNm);
        model.addAttribute("stnList", stnList);
        return "board/write::#stnNmArea";
    }
    @GetMapping("/read")
    public void read(@RequestParam Long bno, Model model){
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }
}
