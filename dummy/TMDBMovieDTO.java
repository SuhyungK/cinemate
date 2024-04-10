import java.util.List;

// 영화 디테일
public class TMDBMovieDTO {

    private String id;
    private String title;
    private String original_title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private String runtime;
    private String release_date;
    private String vote_average;
    private Videos videos;
    private Credits credits;
    private List<Genre> genres;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public Videos getVideos() {
        return videos;
    }

    public Credits getCredits() {
        return credits;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "TMDBMovieDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", runtime='" + runtime + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", videos=" + videos +
                ", credits=" + credits +
                '}';
    }

    public class Genre {
        private String id;

        public String getId() {
            return id;
        }
    }
}

class Credits {
    private List<Credit> cast;
    private List<Credit> crew;

    public List<Credit> getCast() {
        return cast;
    }

    public List<Credit> getCrew() {
        return crew;
    }

    class Credit {

        private String id;
        private String name;
        private String character;
        private String profile_path;
        private String job;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCharacter() {
            return character;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public String getJob() {
            return job;
        }

        @Override
        public String toString() {
            return "Credit{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", character='" + character + '\'' +
                    ", profile_path='" + profile_path + '\'' +
                    ", job='" + job + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Credit cr : cast) {
            sb.append(cr.toString());
        }

        for (Credit cr : crew) {
            sb.append(cr.toString());
        }
        return sb.toString();
    }
}

class Videos {
    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    class Video {
        private String id;
        private String name;
        private String key;
        private String size;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }

        public String getSize() {
            return size;
        }

        public Video(String id, String name, String key, String size) {
            this.id = id;
            this.name = name;
            this.key = key;
            this.size = size;
        }

        @Override
        public String toString() {
            return "Video{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", key='" + key + '\'' +
                    ", size='" + size + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Video video : results) {
            sb.append(video.toString());
        }
        return sb.toString();
    }
}
