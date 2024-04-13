import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class DummyData {
    static int count = 1;
    static Gson gson = new Gson();

    // 도메인 테이블
    static Set<CustomMovie> movieList = new HashSet<>();
    static List<String[]> videoList = new ArrayList<>();
    static Set<CustomCredit> actorList = new HashSet<>();
    static Set<CustomCredit> directorList = new HashSet<>();

    // 다대다 테이블
    static List<String[]> movieGenreList = new ArrayList<>();
    static List<String[]> castList = new ArrayList<>();
    static List<String[]> crewList = new ArrayList<>();

    /**
     * 크레딧 정보 커스텀
     * 크레딧ID랑 이름이 같으면 같은 크레딧
     */
    private static class CustomCredit {
        private String id;
        String[] creditInfo = new String[3];

        public CustomCredit(String id, String name, String profile_path) {
            this.id = id;
            this.creditInfo = new String[]{id, name, profile_path};
        }

        public String[] getCreditInfo() {
            return creditInfo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomCredit that = (CustomCredit) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    /**
     * 영화 정보 커스텀
     * 영화ID가 같으면 같은 영화
     */
    private static class CustomMovie {
        private String id;
        private String[] movieInfo;

        public CustomMovie(String id, String title, String original_title, String overview, String poster_path, String backdrop_path, String runtime, String release_date, String vote_average, String popularity) {
            this.id = id;
            this.movieInfo = new String[]{id, title, original_title, overview, poster_path, backdrop_path, runtime, release_date, vote_average, popularity};
        }

        public String[] getMovieInfo() {
            return movieInfo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomMovie that = (CustomMovie) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    public static void main(String[] args) {
        List<String> movieIds = getMovies();
        for (String movieId : movieIds) {
            getMovieDetail(movieId);
            count++;
        }

        /**
         * csv 로 넘겨야 되는 파일 목록
         * 1. movie.csv(영화정보)
         * 2. movie_genre.csv(영화장르 다대다)
         * 3. credit.csv(감독/배우)
         * 4. movie_credit(영화-감독/배우 다대다)
         * 5. video.csv(비디오정보)
         */
        writeCSV();
    }

    // 트렌드에 오른 영화들의 id만 받아오기
    static List<String> getMovies() {
        List<String> ids = new ArrayList<>();

        for (int page = 1; page <= 100; page++) {
            System.out.println("page = " + page);
            TMDBMovieID tmdbMovieID = gson.fromJson(
                    fetch("https://api.themoviedb.org/3/trending/movie/week?language=ko-KR&page=" + page),
                    TMDBMovieID.class);

            for (TMDBMovieID.MovieId movieId : tmdbMovieID.getResults()) {
                ids.add(movieId.getId());
            }
        }
        return ids;
    }

    // movieId로 각 영화의 디테일 정보 수집
    static void getMovieDetail(String movieId) {
        TMDBMovieDTO tmdbMovieDTO = gson.fromJson(
                fetch("https://api.themoviedb.org/3/movie/" + movieId + "?append_to_response=videos%2Ccredits&language=ko-KR"),
                TMDBMovieDTO.class);
//        System.out.println(tmdbMovieDTO.getCredits());
        System.out.println("#" + count + " " + movieId + " " + tmdbMovieDTO.getTitle());

        // 영화 정보 추가
        movieList.add(new CustomMovie(
                movieId,
                tmdbMovieDTO.getTitle(),
                tmdbMovieDTO.getOriginal_title(),
                tmdbMovieDTO.getOverview(),
                tmdbMovieDTO.getPoster_path(),
                tmdbMovieDTO.getBackdrop_path(),
                tmdbMovieDTO.getRuntime(),
                tmdbMovieDTO.getRelease_date(),
                tmdbMovieDTO.getVote_average(),
                tmdbMovieDTO.getPopularity()
        ));

        // 영화-장르 정보 추가
        for (TMDBMovieDTO.Genre genre : tmdbMovieDTO.getGenres()) {
            movieGenreList.add(new String[]{movieId, genre.getId()});
        }

        /**
         * 배우 정보
         */
        for (Credits.Credit credit : tmdbMovieDTO.getCredits()
                                                 .getCast()) {
            // 배역 정보가 있으면 배우
            if (credit.getCharacter() != null) {
                actorList.add(new CustomCredit(
                        credit.getId(),
                        credit.getName(),
                        credit.getProfile_path()));

                castList.add(new String[]{
                        movieId,
                        credit.getId(),
                        credit.getCharacter() // 배역 정보
                });
            }
        }

        /**
         * 감독
         * 영화 : 감독 = M : N
         */
        for (Credits.Credit crew : tmdbMovieDTO.getCredits()
                                               .getCrew()) {
            if (crew.getJob()
                    .equals("Director")) {

                directorList.add(new CustomCredit(
                        crew.getId(),
                        crew.getName(),
                        crew.getProfile_path()));

                crewList.add(new String[]{
                        movieId,
                        crew.getId()
                });
            }
        }

        // 영화-비디오
        for (Videos.Video video : tmdbMovieDTO.getVideos()
                                              .getResults()) {
            videoList.add(new String[]{
                    movieId,
                    video.getId(),
                    video.getKey(),
                    video.getName(),
                    video.getSize()
            });
        }
    }

    static void writeCSV() {

        String[] header;
        FileWriter output;
        CSVWriter writer;
        try {
            /**
             * 영화 csv
             */
            File file1 = new File("../csv/movie.csv");
            output = new FileWriter(file1);
            writer = new CSVWriter(output);
            header = new String[]{"movie_id", "title", "original_title", "overview", "poster_path", "backdrop_path", "runtime", "release_date", "vote_average", "popularity"};
            writer.writeNext(header);
            for (CustomMovie movie : movieList) {
                writer.writeNext(movie.getMovieInfo());
            }
            writer.close();

            /**
             * 영화 - 장르 다대다 (movie_genre.csv)
             */
            File file2 = new File("../csv/movie_genre.csv");
            output = new FileWriter(file2);
            writer = new CSVWriter(output);
            header = new String[]{"movie_id", "genre_id"};
            writer.writeNext(header);
            writer.writeAll(movieGenreList);
            writer.close();

            /**
             * 배우 (actor.csv)
             */
            File file3 = new File("../csv/actor.csv");
            output = new FileWriter(file3);
            writer = new CSVWriter(output);
            header = new String[]{"id", "name", "profile_path"};
            writer.writeNext(header);
            for (CustomCredit credit : actorList) {
                writer.writeNext(credit.getCreditInfo());
            }
            writer.close();

            /**
             * 감독 (director.csv)
             */
            File file4 = new File("../csv/director.csv");
            output = new FileWriter(file4);
            writer = new CSVWriter(output);
            header = new String[]{"id", "name", "profile_path"};
            writer.writeNext(header);
            for (CustomCredit credit : directorList) {
                writer.writeNext(credit.getCreditInfo());
            }
            writer.close();

            /**
             * 영화 - 배우 다대다 (cast.csv)
             */
            File file5 = new File("../csv/cast.csv");
            output = new FileWriter(file5);
            writer = new CSVWriter(output);
            header = new String[]{"movie_id", "actor_id", "character"};
            writer.writeNext(header);
            writer.writeAll(castList);
            writer.close();

            /**
             * 영화 - 감독 다대다 (crew.csv)
             */
            File file6 = new File("../csv/crew.csv");
            output = new FileWriter(file6);
            writer = new CSVWriter(output);
            header = new String[]{"movie_id", "director_id"};
            writer.writeNext(header);
            writer.writeAll(crewList);
            writer.close();

            // 비디오 csv
            File file7 = new File("../csv/video.csv");
            output = new FileWriter(file7);
            writer = new CSVWriter(output);
            header = new String[]{"movie_id", "video_id", "path", "name", "size"};
            writer.writeNext(header);
            writer.writeAll(videoList);
            writer.close();

        } catch (IOException e) {
            System.out.println("IO 에러 발생");
            System.out.println(e.getMessage());
        }
    }

    private static String fetch(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .header("accept", "application/json")
                                             .header("Authorization", "Bearer ")
                                             .method("GET", HttpRequest.BodyPublishers.noBody())
                                             .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                                                      .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
