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
public class SubwayBoardController {

    private final BoardService boardService;

    private final SubwayService subwayService;


    @GetMapping("/board")
    public String list(PageRequestDTO pageRequestDTO, Model model, @RequestParam(required = false) Long lno){
        if(lno == null){
            model.addAttribute("result", boardService.getList(pageRequestDTO));
        }else{
            model.addAttribute("result", boardService.getListByLno(pageRequestDTO, lno));
        }
        return "board/list";
    }
    @GetMapping("/board/write")
    public void write(){}


    @PostMapping("/board/write")
    public String write(@ModelAttribute BoardDTO boardDTO,@CookieValue(value="userEmail", required = false) Cookie rCookie){
        boardService.save(boardDTO, rCookie.getValue());
        return "redirect:/board";
    }

    @PostMapping("/board/stnm")
    public String stnNm(@RequestParam String stnNm, Model model) throws Exception{
        List<String> stnList = subwayService.getStnName(stnNm);
        model.addAttribute("stnList", stnList);
        return "board/write::#stnNmArea";
    }
    @GetMapping("/board/read")
    public void read(@RequestParam Long bno, Model model){
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }
    @GetMapping("/board/modify")
    public void modify(@RequestParam Long bno, Model model){
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }
    @PostMapping("/board/modify")
    public String modify(@ModelAttribute BoardDTO boardDTO){
        boardService.update(boardDTO.getBno(), boardDTO, 0);
        return "redirect:/board";
    }
    @GetMapping("/board/delete")
    public String delete(@RequestParam Long bno){
        boardService.delete(bno);
        return"redirect:/board";
    }
}
