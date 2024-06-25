package me.sanghyuk.subwayboard.service;

import me.sanghyuk.subwayboard.dto.BoardDTO;
import me.sanghyuk.subwayboard.dto.PageRequestDTO;
import me.sanghyuk.subwayboard.dto.PageResultDTO;
import me.sanghyuk.subwayboard.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardServiceTest {
    @Autowired
    private BoardService service;
    @Autowired
    private SubwayService subwayService;

    @DisplayName("열차 없는 게시글 등록")
    @Test
    public void testSaveN(){
        IntStream.rangeClosed(1,300).forEach(i->{

            BoardDTO dto = BoardDTO.builder()
                    .title("test"+i)
                    .content("test"+i)
                    .writer("user"+i)
                    .build();

            System.out.println(service.save(dto, "user", 0));
        });

    }

    @DisplayName("열차 있는 게시글 등록")
    @Test
    public void testSaveY(){
        BoardDTO dto = BoardDTO.builder()
                .title("test")
                .content("test")
                .writer("user")
                .trainNo("C9012")
                .lineNo("09호선")
                .stdStation("언주")
                .trainTime("06:20:55")
                .build();

        System.out.println(service.save(dto, "user", 1));
    }
    @DisplayName("게시글 조회")
    @Test
    public void testRead(){
        System.out.println(service.read(2L));
    }

    @DisplayName("게시글 삭제")
    @Test
    public void testDelete(){
        service.delete(1L);
    }

    @DisplayName("열차 없는 게시글 수정")
    @Test
    public void updateN(){
        BoardDTO dto = BoardDTO.builder()
                .title("update")
                .content("update")
                .build();
        System.out.println(service.update(3L, dto, 0));
        System.out.println(service.read(3L));
    }

    @DisplayName("열차 있는 게시글 수정")
    @Test
    public void updateY(){
        BoardDTO dto = BoardDTO.builder()
                .title("test")
                .content("test")
                .writer("user")
                .trainNo("C9012")
                .lineNo("09호선")
                .stdStation("언주")
                .trainTime("06:20:55")
                .build();

        System.out.println(service.update(4L, dto, 1));
        System.out.println(service.read(4L));
    }


    @DisplayName("페이징 테스트")
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<BoardDTO, Board> resultDTO = service.getList(pageRequestDTO);
        System.out.println("PREV:"+resultDTO.isPrev());
        System.out.println("NEXT:"+resultDTO.isNext());
        System.out.println("TOTAL:"+resultDTO.getTotalPage());
        System.out.println("---------------------------");

        for(BoardDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("======================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));
    }

    @DisplayName("역 이름 테스트")
    @Test
    public void testStnName() throws Exception {
        String[] add = {" ", "신림", " "};
        List<String> result = subwayService.getStnName("SearchSTNBySubwayLineInfo", add);
        System.out.println(result.toString());
    }

}