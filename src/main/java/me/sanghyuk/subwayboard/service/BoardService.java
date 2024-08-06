package me.sanghyuk.subwayboard.service;

import lombok.RequiredArgsConstructor;
import me.sanghyuk.subwayboard.dto.BoardDTO;
import me.sanghyuk.subwayboard.dto.PageRequestDTO;
import me.sanghyuk.subwayboard.dto.PageResultDTO;
import me.sanghyuk.subwayboard.entity.Board;
import me.sanghyuk.subwayboard.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(BoardDTO dto, String userName) {
        if(dto.getStdStation() == null || dto.getStdStation() == ""){
            return boardRepository.save(dto.toEntity(userName));
        } else {
            String[] values = dto.getStdStation().split("\\.");
            dto.setLineNo(values[0]);
            dto.setStdStation(values[1]);
            return boardRepository.save(dto.toTrainEntity(userName));
        }
    }

    public BoardDTO read(Long bno) {
        Board board = boardRepository.findById(bno).orElseThrow(()->new IllegalArgumentException("not found: "+bno));

        return entityToDto(board);
    }

    public void delete(long bno){
        Board board = boardRepository.findById(bno).orElseThrow(()->new IllegalArgumentException("not found: "+bno));
//        authorizeArticleAuthor(board);
        boardRepository.delete(board);
    }

    @Transactional
    public Board update(long bno, BoardDTO dto, int trainYn) {
        Board board = boardRepository.findById(bno).orElseThrow(()->new IllegalArgumentException("not found: "+bno));
//        authorizeArticleAuthor(board);
//        if(trainYn == 0){
            board.update(dto.getTitle(), dto.getContent());
//        } else{
//            board.trainUpdate(dto.getTitle(), dto.getContent(), dto.getLineNo(), dto.getTrainNo(), dto.getStdStation(), dto.getTrainTime());
//        }
        return board;
    }



    public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        Function<Board, BoardDTO> fn = (entity -> entityToDtoList(entity));
        return new PageResultDTO<>(result, fn);
    }
    public PageResultDTO<BoardDTO, Board> getListByLno(PageRequestDTO requestDTO, Long lno) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
        Page<Board> result = boardRepository.findByLineNoContaining(lno.toString(),pageable);
        Function<Board, BoardDTO> fn = (entity -> entityToDtoList(entity));
        return new PageResultDTO<>(result,fn);
    }

    public BoardDTO entityToDtoList(Board entity) {
        String writerList = entity.getWriter().substring(0,3)+"***";
        BoardDTO dto = BoardDTO.builder()
                .bno(entity.getBno())
                .lineNo(entity.getLineNo())
                .stdStation(entity.getStdStation())
                .title(entity.getTitle())
                .writer(writerList)
                .regDate(entity.getRegDate())
                .build();
        return dto;
    }

    public BoardDTO entityToDto(Board entity) {
        BoardDTO dto = BoardDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .lineNo(entity.getLineNo())
                .trainNo(entity.getTrainNo())
                .stdStation(entity.getStdStation())
                .trainTime(entity.getTrainTime())
                .build();
        return dto;
    }

//    private static void authorizeArticleAuthor(Board board){
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        if(!board.getWriter().equals(userName)){
//            throw new IllegalArgumentException("not authorized");
//        }
//    }
}
