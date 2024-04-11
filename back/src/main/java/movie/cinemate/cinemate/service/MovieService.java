package movie.cinemate.cinemate.service;

import org.springframework.stereotype.Service;

@Service
public interface MovieService {
    /**
     * 전체 영화 조회
     */
    public void findAll();

    /**
     * 영화ID로 개별 영화 조회
     */
    public void findById();

    /**
     * 키워드로 영화 찾기
     */
    public void findByKeyword();
}
